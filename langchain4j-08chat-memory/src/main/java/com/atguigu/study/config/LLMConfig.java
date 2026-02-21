package com.atguigu.study.config;

import com.atguigu.study.service.ChatAssistant;
import com.atguigu.study.service.ChatMemoryAssistant;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.TokenCountEstimator;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiTokenCountEstimator;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.MemoryId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther zzyybs@126.com
 * @Date 2025-05-30 18:58
 * @Description: 知识出处，https://docs.langchain4j.dev/tutorials/chat-memory/#eviction-policy
 */
@Configuration
public class LLMConfig
{
    @Bean
    public ChatModel chatModel()
    {
        return OpenAiChatModel.builder()
                    .apiKey(System.getenv("aliQwen-api"))
                    .modelName("qwen-long")
                    .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();
    }

    @Bean(name = "chat")
    public ChatAssistant chatAssistant(ChatModel chatModel)
    {
        return AiServices.create(ChatAssistant.class, chatModel);
    }


    /**
    * @Description: 按照MessageWindowChatMemory,当堂编码给学生演示
     *知识出处，https://docs.langchain4j.dev/tutorials/chat-memory/#eviction-policy
    * @Auther: zzyybs@126.com
    */
    @Bean(name = "chatMessageWindowChatMemory")
    public ChatMemoryAssistant chatMessageWindowChatMemory(ChatModel chatModel)
    {

        return AiServices.builder(ChatMemoryAssistant.class)
                .chatModel(chatModel)
                //按照memoryId对应创建了一个chatMemory
                /*
                    1. 配置聊天记忆提供器，使用MessageWindowChatMemory策略
                    2. 设置最大消息数量为100条，当超过这个数量时会自动移除最早的消息
                    3. memoryId参数用于区分不同用户的聊天会话，确保每个用户的记忆独立管理
                    4. MessageWindowChatMemory是最简单的内存管理策略，按消息数量进行淘汰
                 */
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(100))
                .build();
    }

    /**
    * @Description: 按照TokenWindowChatMemory,当堂编码给学生演示
     * 知识出处，https://docs.langchain4j.dev/tutorials/chat-memory/#eviction-policy
    * @Auther: zzyybs@126.com
    */
    @Bean(name = "chatTokenWindowChatMemory")
    public ChatMemoryAssistant chatTokenWindowChatMemory(ChatModel chatModel)
    {
        /*
         * 创建TokenCountEstimator实例用于估算消息的token数量
         * 使用OpenAiTokenCountEstimator并指定模型为"gpt-4"
         * 这个估算器将帮助TokenWindowChatMemory准确计算消息的token消耗
         */
        TokenCountEstimator tokenCountEstimator = new OpenAiTokenCountEstimator("gpt-4");


        return AiServices.builder(ChatMemoryAssistant.class)
                .chatModel(chatModel)
                /*
                    1. 配置聊天记忆提供器，使用TokenWindowChatMemory策略
                    2. 设置最大token数量为1000个，当超过这个数量时会自动移除最早的消息
                    3. tokenCountEstimator用于估算消息的token数量，这里使用OpenAiTokenCountEstimator
                    4. TokenWindowChatMemory根据token数量进行淘汰，相比MessageWindow更精确地控制上下文长度
                 */
                .chatMemoryProvider(memoryId -> TokenWindowChatMemory.withMaxTokens(1000,tokenCountEstimator))
                .build();
    }
}
