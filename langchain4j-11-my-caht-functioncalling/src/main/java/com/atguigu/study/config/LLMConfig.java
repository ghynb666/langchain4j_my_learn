package com.atguigu.study.config;

import com.atguigu.study.service.FunctionAssistant;
import com.atguigu.study.service.InvoiceHandler;
import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @auther zzyybs@126.com
 * @Date 2025-06-02 16:59
 * @Description: TODO
 */
@Configuration
public class LLMConfig
{
    @Bean
    public ChatModel chatModel()
    {
        return OpenAiChatModel.builder()
                .apiKey(System.getenv("AI_DASHSCOPE_API_KEY"))
                .modelName("qwen3.5-plus")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();
    }

    /**
    * @Description: 第一组 Low Level Tool API
     * https://docs.langchain4j.dev/tutorials/tools#low-level-tool-api
    * @Auther: zzyybs@126.com
    */
    /*@Bean
    public FunctionAssistant functionAssistant(ChatModel chatModel)
    {
        // 工具说明 ToolSpecification
        ToolSpecification toolSpecification = ToolSpecification.builder()
                .name("发票工具小助手")
                .description("根据用户提交的开票信息，开具发票")
                .parameters(JsonObjectSchema.builder()
                        .addStringProperty("companyName", "公司名称")
                        .addStringProperty("dutyNumber", "税号序列")
                        .addStringProperty("amount", "开票金额，保留两位有效数字")
                        .build())
                .build();


        // 业务逻辑 ToolExecutor
        ToolExecutor toolExecutor = (toolExecutionRequest,memoryId) -> {
            System.out.println("每次调用的唯一ID:" + toolExecutionRequest.id());
            System.out.println("调用工具名称:" + toolExecutionRequest.name());
            String arguments = toolExecutionRequest.arguments();//LLM解析自然语言得到的JSON参数字符串

            return "开具成功!";//这个作为传给LLM，告诉LLM工具执行完毕，LLM根据这个自然语言回答。
        };


        return AiServices.builder(FunctionAssistant.class)
                .chatModel(chatModel)
                .tools(Map.of(toolSpecification,toolExecutor)) // Tools (Function Calling)
                .build();
    }*/



    /**
    * @Description: 第二组 High Level Tool API
     * https://docs.langchain4j.dev/tutorials/tools#high-level-tool-api
    * @Auther: zzyybs@126.com
    */
    @Bean
    public FunctionAssistant functionAssistant(ChatModel chatModel)
    {
        return AiServices.builder(FunctionAssistant.class)
                    .chatModel(chatModel)
                    .tools(new InvoiceHandler())
                .build();
    }
}
