package com.atguigu.study.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LLMConfig
{
    @Bean
    public EmbeddingModel embeddingModel()
    {
        return OpenAiEmbeddingModel.builder()
                .apiKey(System.getenv("AI_DASHSCOPE_API_KEY"))
                .modelName("qwen3-vl-rerank")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();
    }

    /**
     * 创建qdrant客户端
     *
     * @return
     */
    @Bean
    public QdrantClient qdrantClient() {
        QdrantGrpcClient.Builder newBuilder = QdrantGrpcClient.newBuilder("localhost", 6374, false);
        return new QdrantClient(newBuilder.build());
    }

    /**
     * 创建Qdrant向量存储实例
     * 用于存储和检索文本片段的向量嵌入
     * 
     * @return QdrantEmbeddingStore实例，配置连接到本地Qdrant服务
     */
    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        return QdrantEmbeddingStore.builder()
                .host("localhost")
                .port(6333)
                .collectionName("test-qdrant")
                .build();
    }
}
