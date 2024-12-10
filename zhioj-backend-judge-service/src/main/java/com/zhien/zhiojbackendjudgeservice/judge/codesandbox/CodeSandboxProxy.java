package com.zhien.zhiojbackendjudgeservice.judge.codesandbox;

import com.zhien.zhiojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.zhien.zhiojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Zhien
 * @version 1.0
 * @name codeSandboxProxy
 * @description 代码沙箱功能增强代理类
 * @createDate 2024/11/11 10:41
 */
@Slf4j
public class CodeSandboxProxy implements CodeSandbox {
    private final CodeSandbox codeSandbox;

    public CodeSandboxProxy(CodeSandbox codeSandbox) {
        this.codeSandbox = codeSandbox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息：{}", executeCodeRequest.toString());
        // 在执行代码之前，可以进行一些预处理操作
        // 例如：检查代码是否包含恶意代码，过滤敏感词等
        // 这里只是简单示例，实际应用中需要根据具体需求进行实现
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        // 在执行代码之后，可以进行一些后处理操作
        // 例如：记录日志，统计代码执行时间等
        // 这里只是简单示例，实际应用中需要根据具体需求进行实现
        log.info("代码沙箱响应信息：{}", executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
