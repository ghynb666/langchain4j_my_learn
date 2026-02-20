package com.atguigu.study.service;

import com.atguigu.study.entities.ITPrompt;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * ClassName:ITAssistant
 * Package:com.atguigu.study.service
 * Description:
 *
 * @Author 郭浩宇
 * @Create 2026/2/20 16:04
 * @Version 1.0
 */
public interface ITAssistant {

    @SystemMessage("你是一位IT行业专业，只回答IT相关的知识或者问题" +
            "输出限制: 若用于询问其他方面的问题，直接回复：抱歉，我只能回复IT相关的知识")
    @UserMessage("用户的提问:{{question}},字数控制在{{length}}之内")
    String chat(@V("question") String question,@V("length") int length);

    @SystemMessage("你是一位IT行业专业，只回答IT相关的知识或者问题\" +\n" +
            "\"输出限制: 若用于询问其他方面的问题，直接回复：抱歉，我只能回复IT相关的知识")
    String chat(ITPrompt itPrompt);
}
