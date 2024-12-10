package com.zhien.zhiojbackendquestionservice.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhien.zhiojbackendcommon.common.ErrorCode;
import com.zhien.zhiojbackendcommon.constant.CommonConstant;
import com.zhien.zhiojbackendcommon.exception.BusinessException;
import com.zhien.zhiojbackendcommon.utils.SqlUtils;
import com.zhien.zhiojbackendmodel.model.codesandbox.JudgeInfo;
import com.zhien.zhiojbackendmodel.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.zhien.zhiojbackendmodel.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.zhien.zhiojbackendmodel.model.entity.Question;
import com.zhien.zhiojbackendmodel.model.entity.QuestionSubmit;
import com.zhien.zhiojbackendmodel.model.entity.User;
import com.zhien.zhiojbackendmodel.model.enums.QuestionSubmitLanguageEnum;
import com.zhien.zhiojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.zhien.zhiojbackendmodel.model.enums.UserRoleEnum;
import com.zhien.zhiojbackendmodel.model.vo.QuestionSubmitVO;
import com.zhien.zhiojbackendmodel.model.vo.QuestionVO;
import com.zhien.zhiojbackendmodel.model.vo.UserVO;
import com.zhien.zhiojbackendquestionservice.mapper.QuestionSubmitMapper;
import com.zhien.zhiojbackendquestionservice.producer.MyMessageProducer;
import com.zhien.zhiojbackendquestionservice.service.QuestionService;
import com.zhien.zhiojbackendquestionservice.service.QuestionSubmitService;
import com.zhien.zhiojbackendserviceclient.feign.JudgeFeignClient;
import com.zhien.zhiojbackendserviceclient.feign.UserFeignClient;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Zhien
 * @description 针对表【question_submit(题目提交)】的数据库操作Service实现
 * @createDate 2024-11-02 16:23:46
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit> implements QuestionSubmitService {
    @Resource
    private QuestionService questionService;
    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    @Lazy
    private JudgeFeignClient judgeFeignClient;

    @Resource
    private MyMessageProducer myMessageProducer;

    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest 请求
     * @param loginUser                登录用户
     * @return 用户提交问题的id
     */
    @Override
    public Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        //校验编程语言是否合法
        String language = questionSubmitAddRequest.getLanguage();
        //根据请求中的language字段来到枚举类中找对应的枚举类型
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        //找不到对应的枚举类型
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "编程语言不合法!");
        }
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionSubmitAddRequest.getQuestionId());
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        long userId = loginUser.getId();
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setQuestionId(questionSubmitAddRequest.getQuestionId());
        questionSubmit.setCode(questionSubmitAddRequest.getCode());

        //初始化提交状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setLanguage(language);
        questionSubmit.setUserId(userId);
        questionSubmit.setJudgeInfo("{}");
        boolean save = this.save(questionSubmit);
        if (!save) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目提交失败!");
        }
        //TODO 提交代码至判题服务
        Long questionSubmitId = questionSubmit.getId();
        myMessageProducer.sendMessage("do_judge_exchange", "my_routing_key", String.valueOf(questionSubmitId));
        //异步调用判题服务
//        CompletableFuture.runAsync(() -> {
//            judgeFeignClient.doJudge(questionSubmitId);
//        });
        return questionSubmitId;
    }


    /**
     * 获取查询包装类
     *
     * @param questionSubmitQueryRequest 题目提交查询请求
     * @return 查询包装类
     */
    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryRequest == null) {
            return queryWrapper;
        }
        Long id = questionSubmitQueryRequest.getId();
        String language = questionSubmitQueryRequest.getLanguage();
        String code = questionSubmitQueryRequest.getCode();
        JudgeInfo judgeInfo = questionSubmitQueryRequest.getJudgeInfo();
        Integer status = questionSubmitQueryRequest.getStatus();
        Long questionId = questionSubmitQueryRequest.getQuestionId();
        Long userId = questionSubmitQueryRequest.getUserId();
        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();

        // 拼接查询条件
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(StringUtils.isNotEmpty(language), "language", language);
        queryWrapper.eq(StringUtils.isNotEmpty(code), "code", code);
        queryWrapper.eq(ObjectUtils.isNotEmpty(judgeInfo), "judgeInfo", judgeInfo);
        queryWrapper.eq(ObjectUtils.isNotEmpty(status), "status", status);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);

        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }

    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser) {
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        //脱敏: 仅本人或管理员才能查看题目提交后，所提交题目的提交代码
        if (!loginUser.getId().equals(questionSubmit.getUserId()) &&
                !Objects.equals(loginUser.getUserRole(), UserRoleEnum.ADMIN.getValue())) {
            questionSubmitVO.setCode(null);
        }
        // 1. 关联查询用户信息
        Long userId = questionSubmit.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userFeignClient.getById(userId);
        }
        UserVO userVO = userFeignClient.getUserVO(user);
        questionSubmitVO.setUserVO(userVO);

        Long questionId = questionSubmit.getQuestionId();
        Question question = null;
        if (questionId != null && questionId > 0) {
            question = questionService.getById(questionId);
        }
        //获取用户
        QuestionVO questionVO = questionService.getQuestionVO(question);
        questionSubmitVO.setQuestionVO(questionVO);
        return questionSubmitVO;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser) {
        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollUtil.isEmpty(questionSubmitList)) {
            return questionSubmitVOPage;
        }
        // 1. 关联查询用户信息
        Set<Long> userIdSet = questionSubmitList.stream().map(QuestionSubmit::getUserId).collect(Collectors.toSet());
        //查询数据库中所有ids的用户信息，保存至map中，key为用户id，value为用户信息
        Map<Long, List<User>> userIdUserListMap = userFeignClient.listByIds(userIdSet).stream().collect(Collectors.groupingBy(User::getId));
        // 填充信息
        List<QuestionSubmitVO> questionSubmitVOList = questionSubmitList.stream().map(questionSubmit -> {
            //将对象转成VO包装类
//            QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
//            Long userId = questionSubmit.getUserId();
//            User user = null;
//            //根据用户id查询是否存在当前用户，存在则赋值给user
//            if (userIdUserListMap.containsKey(userId)) {
//                user = userIdUserListMap.get(userId).get(0);
//            }
//            //补充VO包装类中用户vo信息
//            questionSubmitVO.setUserVO(userService.getUserVO(user));
//            return questionSubmitVO;
            return this.getQuestionSubmitVO(questionSubmit, loginUser);
        }).collect(Collectors.toList());
        questionSubmitVOPage.setRecords(questionSubmitVOList);
        return questionSubmitVOPage;
    }
}





