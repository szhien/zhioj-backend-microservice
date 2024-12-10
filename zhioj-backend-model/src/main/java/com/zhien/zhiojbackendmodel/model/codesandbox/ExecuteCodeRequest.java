package com.zhien.zhiojbackendmodel.model.codesandbox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Zhien
 * @version 1.0
 * @name ExecuteCodeRequest
 * @description 代码执行请求参数
 * @createDate 2024/11/08 16:49
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeRequest {
    private List<String> inputList;
    private String language;
    private String code;
}
