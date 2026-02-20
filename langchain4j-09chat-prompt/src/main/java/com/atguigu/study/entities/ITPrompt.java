package com.atguigu.study.entities;

import dev.langchain4j.model.input.structured.StructuredPrompt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName:ITPrompt
 * Package:com.atguigu.study.entities
 * Description:
 *
 * @Author 郭浩宇
 * @Create 2026/2/20 16:35
 * @Version 1.0
 */
@Data
@StructuredPrompt("根据{{techStack}}知识库,解答一下问题{{question}}，字数控制在{{length}}以内")
@AllArgsConstructor
@NoArgsConstructor
public class ITPrompt {
    /**
     * 技术栈
     */
    private String techStack;

    /**
     * 问题
     */
    private String question;
    /**
     * 输出返回长度
     */
    private int length;
}
