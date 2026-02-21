package com.atguigu.study.controller;

import cn.hutool.core.date.DateUtil;
import com.atguigu.study.service.ChatPersistenceAssistant;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:ChatPersistenceController
 * Package:com.atguigu.study.controller
 * Description:
 *
 * @Author 郭浩宇
 * @Create 2026/2/20 22:06
 * @Version 1.0
 */
@RestController
@Slf4j
public class ChatPersistenceController {

    @Resource
    private ChatPersistenceAssistant chatPersistenceAssistant;

    @GetMapping("/chatPersistence/redis")
    public String test01(){
        chatPersistenceAssistant.chat(1L,"你好我的名字叫redis");
        chatPersistenceAssistant.chat(3L,"你好我的名字叫做nacos");

        String chat = chatPersistenceAssistant.chat(1L, "我的名字叫啥？");
        System.out.println(chat);

        return "testChatPersistence success : "+ DateUtil.now();
    }
}
