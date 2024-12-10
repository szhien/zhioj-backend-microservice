package com.zhien.zhiojbackendmodel.model.codesandbox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Zhien
 * @version 1.0
 * @name ExecuteCodeResponse
 * @description 代码执行返回判题信息等
 * @createDate 2024/11/08 16:38
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeResponse {
    /**
     * 输出信息
     */
    private List<String> outputList;
    /**
     * 执行结果信息
     */
    private String message;
    /**
     * 执行状态
     */
    private Integer status;
    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;
}
