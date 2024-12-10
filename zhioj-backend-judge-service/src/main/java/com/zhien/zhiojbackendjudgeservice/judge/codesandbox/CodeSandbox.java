package com.zhien.zhiojbackendjudgeservice.judge.codesandbox;

import com.zhien.zhiojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.zhien.zhiojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * @author Zhien
 * @version 1.0
 * @name CodeSandbox
 * @description 代码沙箱接口
 * @createDate 2024/11/08 16:25
 */
public interface CodeSandbox {
    ExecuteCodeResponse executeCode(ExecuteCodeRequest request);
}
