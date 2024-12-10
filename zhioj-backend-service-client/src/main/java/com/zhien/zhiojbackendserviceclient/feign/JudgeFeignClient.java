package com.zhien.zhiojbackendserviceclient.feign;

import com.zhien.zhiojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Zhien
 * @version 1.0
 * @name JudgeFeignClient
 * @description 判读服务提供的Feign调用
 * @createDate 2024/12/05 17:04
 */
@FeignClient(name = "zhioj-backend-judge-service", path = "/api/judge/inner")
public interface JudgeFeignClient {
    @GetMapping("/doJudge")
    QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId);
}
