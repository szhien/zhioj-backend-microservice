package com.zhien.zhiojbackendmodel.model.dto.question;

import lombok.Data;

/**
 * @author Zhien
 * @version 1.0
 * @name JudgeCase
 * @description 题目用例
 * @createDate 2024/11/02 17:14
 */

@Data
public class JudgeCase {
    //输入用例
    private String input;

    //输出用例
    private String output;

}
