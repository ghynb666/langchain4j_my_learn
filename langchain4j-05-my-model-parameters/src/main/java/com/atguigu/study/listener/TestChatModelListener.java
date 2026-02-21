package com.atguigu.study.listener;

import cn.hutool.core.util.IdUtil;
import dev.langchain4j.model.chat.listener.ChatModelErrorContext;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassName:TestChatModelListener
 * Package:com.atguigu.study.listener
 * Description:
 *
 * @Author 郭浩宇
 * @Create 2026/2/19 17:40
 * @Version 1.0
 */
@Slf4j
public class TestChatModelListener implements ChatModelListener {
    @Override
    public void onError(ChatModelErrorContext errorContext) {
        log.error("请求出错errorContext:{}", errorContext);
    }

    @Override
    public void onRequest(ChatModelRequestContext requestContext) {
        //在请求时通过requestContext传入参数
        String traceId = IdUtil.simpleUUID();
        requestContext.attributes().put("TraceID", traceId);
        log.info("请求参数requestContext:{},{}", requestContext, traceId); // 打印请求参数，traceId用于关联请求和响应
    }

    @Override
    public void onResponse(ChatModelResponseContext responseContext) {
        String traceId = (String) responseContext.attributes().get("TraceID");
        log.info("返回结果responseContext:{},{}", responseContext, traceId); // 打印返回结果，traceId用于关联请求和响应
    }
}
