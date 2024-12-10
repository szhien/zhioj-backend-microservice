package com.zhien.zhiojbackendserviceclient.feign;

import com.zhien.zhiojbackendmodel.model.entity.Question;
import com.zhien.zhiojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Zhien
 * @version 1.0
 * @name QuestionFeignClient
 * @description 题目服务远程调用所提供的服务
 * @createDate 2024/12/05 09:28
 */
@FeignClient(name = "zhioj-backend-question-service", path = "/api/question/inner")
public interface QuestionFeignClient {
    /**
     * questionService.getById(questionId)
     * <p>
     * questionSubmitService.getById(questionSubmitId)
     * <p>
     * questionSubmitService.updateById(questionSubmitUpdate)
     */

    @GetMapping("/get/id")
    Question getById(@RequestParam("questionId") long questionId);

    @PostMapping("/update/submit")
    boolean updateById(@RequestBody QuestionSubmit questionSubmitUpdate);

    @GetMapping("/get/submit/id")
    QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId);
}
