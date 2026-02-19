package com.atguigu.study.controller;

import com.atguigu.study.service.ChatAssistant;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:HighApiController
 * Package:com.atguigu.study.controller
 * Description:
 *
 * @Author 郭浩宇
 * @Create 2026/2/19 16:30
 * @Version 1.0
 */
@RestController
@Slf4j
@RequestMapping("/highApi")

public class HighApiController {

    @Resource
    private ChatAssistant chatAssistant;

    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    public String chat(@RequestParam(value = "prompt", defaultValue = "你是谁") String prompt) {
        return chatAssistant.chat(prompt);
    }
}
