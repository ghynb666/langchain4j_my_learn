package com.atguigu.study.config;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther zzyybs@126.com
 * @Date 2025-06-02 16:08
 * @Description: TODO
 */

@Configuration
public class LLMConfig
{


    @Bean
    public ChatModel chatModel()
    {
        return OpenAiChatModel.builder()
                    .apiKey(System.getenv("AI_DASHSCOPE_API_KEY"))
                    .modelName("qwen3.5-plus")
                    .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();
    }



}