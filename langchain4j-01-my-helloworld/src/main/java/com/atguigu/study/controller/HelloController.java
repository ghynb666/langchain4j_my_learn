package com.atguigu.study.controller;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.output.TokenUsage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:HelloController
 * Package:com.atguigu.study.controller
 * Description:
 *
 * @Author 郭浩宇
 * @Create 2026/2/18 18:11
 * @Version 1.0
 */
@RestController
@Slf4j
public class HelloController {

    @Resource(name = "QWen")
    private ChatModel chatModelQWen;

    @Resource(name = "DeepSeek")
    private ChatModel chatModelDeepSeek;

    @RequestMapping(value = "langchain4j/MultiModel/qwen", method = RequestMethod.GET)
    public String chatWithQWen(@RequestParam(value = "prompt",defaultValue = "你是谁") String prompt) {
        String result = chatModelQWen.chat(prompt);
        log.info("question:{}，result:{}", prompt, result);
        return result;
    }

    @RequestMapping(value = "langchain4j/MultiModel/deepseek", method = RequestMethod.GET)
    public String chatWithDeepSeek(@RequestParam(value = "prompt",defaultValue = "你是谁") String prompt) {
        String result = chatModelDeepSeek.chat(prompt);
        log.info("question:{}，result:{}", prompt, result);
        return result;
    }


    /**
     * 测试记录对话消耗了多少token
     */
    @RequestMapping(value = "langchain4j/MultiModel/chatWithQWenAndCountTokens", method = RequestMethod.GET)
    public String chatWithQWenAndCountTokens(@RequestParam(value = "prompt",defaultValue = "你是谁") String prompt) {
        ChatResponse chatResponse = chatModelQWen.chat(UserMessage.from(prompt));

        //获取LLM响应结果
        String text = chatResponse.aiMessage().text();

        System.out.println("LLM: " + text);
        log.info("question:{}，result:{}", prompt, text);


        //获取对话消耗的token数量
        TokenUsage tokenUsage = chatResponse.tokenUsage();

        System.out.println("LLM消耗的Token为：" + tokenUsage );
        return text + "\n" + tokenUsage.toString();
    }
}
