package com.atguigu.study.config;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:RedisChatMemoryStore
 * Package:com.atguigu.study.config
 * Description:
 *
 * @Author 郭浩宇
 * @Create 2026/2/20 21:26
 * @Version 1.0
 */
@Component
public class RedisChatMemoryStore implements ChatMemoryStore {

    public static final String CHAT_MEMORY_PREFIX = "chat:memory:";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        //从redis中取出
        String retValue = stringRedisTemplate.opsForValue().get(CHAT_MEMORY_PREFIX + memoryId);

        if(retValue == null || retValue.isEmpty())
        {
            return new ArrayList<>();
        }

        //序列化为对话内容
        return ChatMessageDeserializer.messagesFromJson(retValue);
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> chatMessageList) {
        //跟新缓存
        stringRedisTemplate.opsForValue().set(CHAT_MEMORY_PREFIX + memoryId, ChatMessageSerializer.messagesToJson(chatMessageList));
    }

    @Override
    public void deleteMessages(Object memoryId) {
        stringRedisTemplate.delete(CHAT_MEMORY_PREFIX + memoryId);

    }
}
