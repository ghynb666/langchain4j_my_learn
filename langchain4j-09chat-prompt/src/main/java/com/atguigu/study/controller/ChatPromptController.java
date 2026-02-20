package com.atguigu.study.controller;

import cn.hutool.core.date.DateUtil;
import com.atguigu.study.entities.ITPrompt;
import com.atguigu.study.entities.LawPrompt;
import com.atguigu.study.service.ITAssistant;
import com.atguigu.study.service.LawAssistant;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @auther zzyybs@126.com
 * @Date 2025-05-30 21:26
 * @Description: TODO
 */
@RestController
@Slf4j
public class ChatPromptController
{
    @Resource
    private LawAssistant lawAssistant;

    @Resource
    private ITAssistant itAssistant;

    @Resource
    private ChatModel chatModel;

    // http://localhost:9009/chatprompt/test1
    @GetMapping(value = "/chatprompt/test1")
    public String test1()
    {
        String chat = lawAssistant.chat("什么是知识产权？",2000);
        System.out.println(chat);

        String chat2 = lawAssistant.chat("什么是java？",2000);
        System.out.println(chat2);

        String chat3 = lawAssistant.chat("介绍下西瓜和芒果",2000);
        System.out.println(chat3);

        String chat4 = lawAssistant.chat("飞机发动机原理",2000);
        System.out.println(chat4);

        return "success : "+ DateUtil.now()+"<br> \n\n chat: "+chat+"<br> \n\n chat2: "+chat2;
    }

    @RequestMapping(value = "/chatPrompt/test01",method = RequestMethod.GET)
    public String test01(){
        String result1 = itAssistant.chat("redis是啥", 200);
        System.out.println(result1);

        String result2 = itAssistant.chat("婚姻法是啥", 200);
        System.out.println(result1);

        return "success : "+ DateUtil.now()+"<br> \n\n chat: "+result1+"<br> \n\n chat2: "+result2;
    }

    /**
     * TRIPS协议（与贸易有关的知识产权协议）：
     * 这是世界贸易组织（WTO）成员间的一个重要协议，
     * 它规定了最低标准的知识产权保护要求，并适用于所有WTO成员。
     * @return
     */
    @GetMapping(value = "/chatprompt/test2")
    public String test2()
    {
        LawPrompt prompt = new LawPrompt();

        prompt.setLegal("知识产权");
        prompt.setQuestion("TRIPS协议?");

        String chat = lawAssistant.chat(prompt);

        System.out.println(chat);

        return "success : "+ DateUtil.now()+"<br> \n\n chat: "+chat;
    }


    /**
     * 测试IT相关问题的智能回答功能
     * 通过ITPrompt对象封装问题参数，调用IT助手进行专业回答
     * @return 包含回答结果的HTML字符串，显示当前时间和回答内容
     */
    @GetMapping(value = "/chatPrompt/test02")
    public String test02(){

        ITPrompt itPrompt = new ITPrompt("java","JDK8的新特新是啥",200);

        String chat = itAssistant.chat(itPrompt);

        System.out.println(chat);

        return "success : "+ DateUtil.now()+"<br> \n\n chat: "+chat;
    }


    /**
    * @Description: 单个参数可以使用{{it}》”占位符或者”{{参数名}”，如果为其他字符，系统不能自动识别会报错。
    * @Auther: zzyybs@126.com
     *      http://localhost:9009/chatprompt/test3
    */
    @GetMapping(value = "/chatprompt/test3")
    public String test3()
    {
        // 看看源码，默认 PromptTemplate 构造使用 it 属性作为默认占位符

        /*String role = "外科医生";
        String question = "牙疼";*/

        String role = "财务会计";
        String question = "人民币大写";

        //1 构造PromptTemplate模板
        PromptTemplate template = PromptTemplate.from("你是一个{{it}}助手,{{question}}怎么办");
        //2 由PromptTemplate生成Prompt
        Prompt prompt = template.apply(Map.of("it",role,"question",question));
        //3 Prompt提示词变成UserMessage
        UserMessage userMessage = prompt.toUserMessage();
        //4 调用大模型
        ChatResponse chatResponse = chatModel.chat(userMessage);

        //4.1 后台打印
        System.out.println(chatResponse.aiMessage().text());
        //4.2 前台返回
        return "success : "+ DateUtil.now()+"<br> \n\n chat: "+chatResponse.aiMessage().text();
    }

    @GetMapping("/chatPrompt/test03")
    public String test03(){

        String role = "IT";
        String question = "Mysql事务失效";
        int length = 200;

        //1. 构建提示词模板
        PromptTemplate promptTemplate = PromptTemplate.from("你是一个{{role}}专家,{{question}}该怎么办,字数限制{{length}}");

        //2. 生成Prompt
        Prompt prompt = promptTemplate.apply(Map.of("role", role, "question", question, "length", length));

        //3. 转为用户消息，以便大模型识别
        UserMessage userMessage = prompt.toUserMessage();

        //4. 调用大模型
        ChatResponse chatResponse = chatModel.chat(userMessage);

        //5. 打印LLM输出
        System.out.println("LLM输出:" + chatResponse);

        return "success : "+ DateUtil.now()+"<br> \n\n chat: "+chatResponse.aiMessage().text();
    }
}
