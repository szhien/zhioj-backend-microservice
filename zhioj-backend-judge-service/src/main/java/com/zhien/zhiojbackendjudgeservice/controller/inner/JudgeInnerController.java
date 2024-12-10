package com.zhien.zhiojbackendjudgeservice.controller.inner;

import com.zhien.zhiojbackendjudgeservice.judge.JudgeService;
import com.zhien.zhiojbackendmodel.model.entity.QuestionSubmit;
import com.zhien.zhiojbackendserviceclient.feign.JudgeFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Zhien
 * @version 1.0
 * @name JudgeInnerController
 * @description 题目服务内部提供服务接口
 * @createDate 2024/12/05 17:13
 */
@RestController
@RequestMapping("/inner")
public class JudgeInnerController implements JudgeFeignClient {

    @Resource
    private JudgeService judgeService;

    @Override
    @GetMapping("/doJudge")
    public QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId) {
        return judgeService.doJudge(questionSubmitId);
    }
}
