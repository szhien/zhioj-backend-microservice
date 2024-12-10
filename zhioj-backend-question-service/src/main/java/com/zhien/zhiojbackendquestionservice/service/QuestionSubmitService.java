package com.zhien.zhiojbackendquestionservice.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhien.zhiojbackendmodel.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.zhien.zhiojbackendmodel.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.zhien.zhiojbackendmodel.model.entity.QuestionSubmit;
import com.zhien.zhiojbackendmodel.model.entity.User;
import com.zhien.zhiojbackendmodel.model.vo.QuestionSubmitVO;

/**
 * @author Zhien
 * @description 针对表【question_submit(题目提交)】的数据库操作Service
 * @createDate 2024-11-02 16:23:46
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return 题目提交的id
     */
    Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);

}
