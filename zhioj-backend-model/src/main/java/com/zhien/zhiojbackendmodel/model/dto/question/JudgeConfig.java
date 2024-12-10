package com.zhien.zhiojbackendmodel.model.dto.question;

import lombok.Data;

/**
 * @author Zhien
 * @version 1.0
 * @name JudgeCase
 * @description 判题配置
 * @createDate 2024/11/02 17:14
 */
@Data
public class JudgeConfig {

    //时间限制（ms）
    private Long timeLimit;

    //内存限制（KB）
    private Long memoryLimit;

    //堆栈限制（KB）
    private Long stackLimit;

}
