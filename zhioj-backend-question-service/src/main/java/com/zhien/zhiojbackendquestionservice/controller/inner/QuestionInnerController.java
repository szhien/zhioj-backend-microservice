package com.zhien.zhiojbackendquestionservice.controller.inner;

import com.zhien.zhiojbackendmodel.model.entity.Question;
import com.zhien.zhiojbackendmodel.model.entity.QuestionSubmit;
import com.zhien.zhiojbackendquestionservice.service.QuestionService;
import com.zhien.zhiojbackendquestionservice.service.QuestionSubmitService;
import com.zhien.zhiojbackendserviceclient.feign.QuestionFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Zhien
 * @version 1.0
 * @name QuestionInnerController
 * @description 题目服务内部提供服务接口
 * @createDate 2024/12/05 17:13
 */
@RestController
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {
    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Override
    @GetMapping("/get/id")
    public Question getById(@RequestParam("questionId")long questionId) {
        return questionService.getById(questionId);
    }

    @Override
    @PostMapping("/update/submit")
    public boolean updateById(@RequestBody QuestionSubmit questionSubmitUpdate) {
        return questionSubmitService.updateById(questionSubmitUpdate);
    }

    @Override
    @GetMapping("/get/submit/id")
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId) {
        return questionSubmitService.getById(questionSubmitId);
    }
}
