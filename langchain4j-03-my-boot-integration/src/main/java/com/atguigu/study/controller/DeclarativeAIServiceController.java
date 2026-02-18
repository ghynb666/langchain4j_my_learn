package com.atguigu.study.controller;

import com.atguigu.study.service.ChatAssistant;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:DeclarativeAIServiceController
 * Package:com.atguigu.study.controller
 * Description:
 *
 * @Author 郭浩宇
 * @Create 2026/2/18 22:01
 * @Version 1.0
 */
@RestController
public class DeclarativeAIServiceController {

    @Resource
    private ChatAssistant chatAssistant;

    @RequestMapping(value = "/lc4j/boot/declarative/chat", method = RequestMethod.GET)
    public String chat(@RequestParam(value = "prompt", defaultValue = "hello") String prompt) {
        return chatAssistant.chat(prompt);
    }
}
