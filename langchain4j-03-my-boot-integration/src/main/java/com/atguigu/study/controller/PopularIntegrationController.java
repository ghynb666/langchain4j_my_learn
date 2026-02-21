package com.atguigu.study.controller;

import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class PopularIntegrationController {

    @Resource
    private ChatModel chatModel;

    @RequestMapping(value = "/lc4j/boot/chat", method = RequestMethod.GET)
    public String model(@RequestParam(value = "prompt", defaultValue = "Hello") String prompt) {
        return chatModel.chat(prompt);
    }
}