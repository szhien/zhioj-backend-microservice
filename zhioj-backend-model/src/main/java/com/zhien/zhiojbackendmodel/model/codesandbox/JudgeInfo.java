package com.zhien.zhiojbackendmodel.model.codesandbox;

import lombok.Data;

/**
 * @author Zhien
 * @version 1.0
 * @name JudgeInfo
 * @description 判题信息
 * @createDate 2024/11/08 16:59
 */
@Data
public class JudgeInfo {

    /**
     * 程序执行信息
     */
    private String message;

    /**
     * 消耗内存
     */
    private Long memory;

    /**
     * 消耗时间（KB）
     */
    private Long time;
}