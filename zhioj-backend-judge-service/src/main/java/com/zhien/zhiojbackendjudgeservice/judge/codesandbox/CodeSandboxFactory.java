package com.zhien.zhiojbackendjudgeservice.judge.codesandbox;

import com.zhien.zhiojbackendjudgeservice.judge.codesandbox.impl.ExampleCodeSandbox;
import com.zhien.zhiojbackendjudgeservice.judge.codesandbox.impl.RemoteCodeSandbox;
import com.zhien.zhiojbackendjudgeservice.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * @author Zhien
 * @version 1.0
 * @name CodeSandboxFactory
 * @description 代码沙箱工厂模式（根据字符串参数创建指定的代码沙箱实例）
 * @createDate 2024/11/11 10:08
 */
public class CodeSandboxFactory {
    /**
     * 根据字符串参数创建指定的代码沙箱实例
     *
     * @param type 沙箱的类型
     * @return 代码沙箱实例
     */
    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}
