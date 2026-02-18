package com.atguigu.study.service;

import dev.langchain4j.service.spring.AiService;

/**
 * ClassName:ChatAssistant
 * Package:com.atguigu.study.service
 * Description:
 *
 * @Author 郭浩宇
 * @Create 2026/2/18 21:54
 * @Version 1.0
 */
@AiService
public interface ChatAssistant {

    String chat(String prompt);
}
