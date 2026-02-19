package com.atguigu.study.config;

import com.atguigu.study.service.ChatAssistant;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName:LLMConfig
 * Package:com.atguigu.study.config
 * Description:
 *
 * @Author 郭浩宇
 * @Create 2026/2/18 18:02
 * @Version 1.0
 */
@Configuration
public class LLMConfig {

    @Bean(name = "QWen")
    public ChatModel chatModelQWen() {
        return OpenAiChatModel.builder()
                .apiKey(System.getenv("AI_DASHSCOPE_API_KEY"))
                .modelName("qwen-max-0428")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();
    }

    @Bean(name = "DeepSeek")
    public ChatModel chatModelDeepSeek() {
        return OpenAiChatModel.builder()
                .apiKey(System.getenv("AI_DEEPSEEK_API_KEY"))
                .modelName("deepseek-chat")
                .baseUrl("https://api.deepseek.com/v1")
                .build();
    }

    @Bean
    public ChatAssistant chatAssistant(@Qualifier("QWen") ChatModel chatModelQWen) {
        return AiServices.create(ChatAssistant.class, chatModelQWen);
    }


}
