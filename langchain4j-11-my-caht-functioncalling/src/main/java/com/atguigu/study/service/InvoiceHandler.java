package com.atguigu.study.service;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;

/**
 * @auther zzyy
 * @create 2025-03-12 23:28
 * 知识出处，https://docs.langchain4j.dev/tutorials/tools/#tool
 */
@Slf4j
public class InvoiceHandler
{

    @Tool("根据用户提交的开票信息进行开票")
    public  String handle(@P("公司名称")String companyName,
                          @P("税号")String dutyNumber,
                          @P("金额保留两位有效数字") String amount) throws Exception {
        log.info("companyName =>>>> {} dutyNumber =>>>> {} amount =>>>> {}", companyName, dutyNumber, amount);
        //这里一般就要写一些业务逻辑
        System.out.println(new WeatherService().getWeatherV2("101010100"));

        //返回给LLM作为响应结果，LLM根据这个返回的消息内容做出LLM本身的响应
        return "开票成功";
    }

}
