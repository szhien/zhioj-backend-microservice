package com.zhien.zhiojbackendjudgeservice.judge;

import com.zhien.zhiojbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.zhien.zhiojbackendjudgeservice.judge.strategy.JavaLanguageJudgeStrategy;
import com.zhien.zhiojbackendjudgeservice.judge.strategy.JudgeContext;
import com.zhien.zhiojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.zhien.zhiojbackendmodel.model.codesandbox.JudgeInfo;
import com.zhien.zhiojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * @author Zhien
 * @version 1.0
 * @name JudgeManger
 * @description 判题管理（选择对应的判题策略，对最终的输入输出进行对比判断等）
 * @createDate 2024/11/11 14:17
 */
@Service
public class JudgeManger {

    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
