package com.zhien.zhiojbackendjudgeservice.judge.codesandbox.impl;

import com.zhien.zhiojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.zhien.zhiojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.zhien.zhiojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.zhien.zhiojbackendmodel.model.codesandbox.JudgeInfo;
import com.zhien.zhiojbackendmodel.model.enums.JudgeInfoMessageEnum;
import com.zhien.zhiojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Zhien
 * @version 1.0
 * @name CodeSandboxImpl
 * @description 示例代码沙箱（仅为了跑通业务流程）
 * @createDate 2024/11/08 16:42
 */
@Slf4j
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("示例代码沙箱");
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
