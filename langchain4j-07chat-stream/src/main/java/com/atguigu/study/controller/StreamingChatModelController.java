package com.atguigu.study.controller;

import com.atguigu.study.service.ChatAssistant;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @auther zzyybs@126.com
 * @Date 2025-05-30 16:25
 * @Description: 知识出处，https://docs.langchain4j.dev/tutorials/response-streaming
 */
@RestController
@Slf4j
public class StreamingChatModelController
{
    @Resource //直接使用 low-level LLM API
    private StreamingChatModel streamingChatLanguageModel;
    @Resource //自己封装接口使用 high-level LLM API
    private ChatAssistant chatAssistant;


    // http://localhost:9007/chatstream/chat?prompt=天津有什么好吃的
    @GetMapping(value = "/chatstream/chat")
    public Flux<String> chat(@RequestParam("prompt") String prompt)
    {
        System.out.println("---come in chat");

        return Flux.create(emitter -> {
            streamingChatLanguageModel.chat(prompt, new StreamingChatResponseHandler()
            {
                @Override
                public void onPartialResponse(String s) {
                    emitter.next(s);
                }

                @Override
                public void onCompleteResponse(ChatResponse chatResponse) {
                    emitter.complete();
                }

                @Override
                public void onError(Throwable throwable) {
                    emitter.error(throwable);
                }
            });
        });
    }

    @GetMapping(value = "/chatstream/chat2")
    public void chat2(@RequestParam(value = "prompt", defaultValue = "北京有什么好吃") String prompt)
    {
        System.out.println("---come in chat2");
        streamingChatLanguageModel.chat(prompt, new StreamingChatResponseHandler() {
            @Override
            public void onPartialResponse(String s) {
                System.out.println(s);
            }

            @Override
            public void onCompleteResponse(ChatResponse chatResponse) {
                System.out.println("流式返回已战场结束" + chatResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("流式返回期间存在异常:" + throwable);
            }
        });
    }



    @GetMapping(value = "/chatstream/chat3")
    public Flux<String> chat3(@RequestParam(value = "prompt", defaultValue = "南京有什么好吃") String prompt)
    {
        System.out.println("---come in chat3");

        return chatAssistant.chatFlux(prompt);
    }
}
