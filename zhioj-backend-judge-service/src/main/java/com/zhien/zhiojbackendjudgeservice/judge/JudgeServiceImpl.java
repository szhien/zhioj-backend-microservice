package com.zhien.zhiojbackendjudgeservice.judge;

import cn.hutool.json.JSONUtil;
import com.zhien.zhiojbackendcommon.common.ErrorCode;
import com.zhien.zhiojbackendcommon.exception.BusinessException;
import com.zhien.zhiojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.zhien.zhiojbackendjudgeservice.judge.codesandbox.CodeSandboxFactory;
import com.zhien.zhiojbackendjudgeservice.judge.codesandbox.CodeSandboxProxy;
import com.zhien.zhiojbackendjudgeservice.judge.strategy.JudgeContext;
import com.zhien.zhiojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.zhien.zhiojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.zhien.zhiojbackendmodel.model.codesandbox.JudgeInfo;
import com.zhien.zhiojbackendmodel.model.dto.question.JudgeCase;
import com.zhien.zhiojbackendmodel.model.entity.Question;
import com.zhien.zhiojbackendmodel.model.entity.QuestionSubmit;
import com.zhien.zhiojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.zhien.zhiojbackendserviceclient.feign.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Zhien
 * @version 1.0
 * @name JudgeServiceImpl
 * @description 判题模块
 * @createDate 2024/11/11 10:52
 */
@Service
public class JudgeServiceImpl implements JudgeService {
    @Resource
    private QuestionFeignClient questionFeignClient;

    @Resource
    private JudgeManger judgeManger;

    @Value("${codesandbox.type}")
    private String codeSandboxType;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        // 1）传入题目的提交 id，获取到对应的题目、提交信息（包含代码、编程语言等）
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目提交不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionFeignClient.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        // 2）如果题目提交状态不为等待中，就不用重复执行了
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目提交正在判题中");
        }
        // 3）更改判题（题目提交）的状态为 “判题中”，防止重复执行，也能让用户即时看到状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionFeignClient.updateById(questionSubmit);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目提交状态更新失败");
        }
        // 4）调用沙箱，获取到执行结果
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(codeSandboxType);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        //沙箱执行请求参数
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        String judgeCase = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCase, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest request = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        // 调用沙箱执行代码
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(request);
        // 5）根据沙箱的执行结果，设置题目的判题状态和信息
        List<String> outputList = executeCodeResponse.getOutputList();
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        // 6）判题选择对应的判题策略进行判题，返回判题信息（一般就是对比输入输出用例的数量和值）
        JudgeInfo judgeInfo = judgeManger.doJudge(judgeContext);
        // 修改数据库中的判题结果
        questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        questionSubmit.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        update = questionFeignClient.updateById(questionSubmit);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目提交状态更新失败");
        }
        return questionFeignClient.getQuestionSubmitById(questionId);
    }
}
