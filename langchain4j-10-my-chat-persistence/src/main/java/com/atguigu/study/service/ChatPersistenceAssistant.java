package com.atguigu.study.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;

/**
 * ClassName:ChatPersistenceAssitant
 * Package:com.atguigu.study.service
 * Description:
 *
 * @Author 郭浩宇
 * @Create 2026/2/20 21:48
 * @Version 1.0
 */
public interface ChatPersistenceAssistant {

    /**
     * 与LLM聊天
     * @param userId
     * @param message
     * @return
     */
    String chat(@MemoryId Long userId, @UserMessage String message);
}
