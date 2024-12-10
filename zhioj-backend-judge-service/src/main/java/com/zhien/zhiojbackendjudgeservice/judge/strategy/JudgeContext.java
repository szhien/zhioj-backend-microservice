package com.zhien.zhiojbackendjudgeservice.judge.strategy;

import com.zhien.zhiojbackendmodel.model.codesandbox.JudgeInfo;
import com.zhien.zhiojbackendmodel.model.dto.question.JudgeCase;
import com.zhien.zhiojbackendmodel.model.entity.Question;
import com.zhien.zhiojbackendmodel.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * @author Zhien
 * @version 1.0
 * @name JudgeContext
 * @description 判题模块服务执行判题上下文（用于定义在策略中传递的参数）
 * @createDate 2024/11/11 10:50
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;

}

