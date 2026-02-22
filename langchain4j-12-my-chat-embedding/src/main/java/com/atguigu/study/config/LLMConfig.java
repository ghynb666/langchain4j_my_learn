package com.atguigu.study.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import io.qdrant.client.QdrantClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LLMConfig
{
    @Bean
    public EmbeddingModel embeddingModel()
    {
        return null;
    }

    @Bean
    public QdrantClient qdrantClient() {
        return null;
    }

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        return null;
    }
}
