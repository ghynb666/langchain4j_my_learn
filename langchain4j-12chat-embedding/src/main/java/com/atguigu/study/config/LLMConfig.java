package com.atguigu.study.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther zzyybs@126.com
 * @Date 2025-06-02 20:44
 * @Description: TODO
 */
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
     * 创建Qdrant客户端
     * @return
     */
    @Bean
    public QdrantClient qdrantClient() {
        QdrantGrpcClient.Builder grpcClientBuilder =
                QdrantGrpcClient.newBuilder("127.0.0.1", 6374, false);
        return new QdrantClient(grpcClientBuilder.build());
    }

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        return QdrantEmbeddingStore.builder()
                .host("127.0.0.1") // Qdrant服务主机地址
                .port(6333)        // Qdrant服务端口
                .collectionName("test-qdrant") // 向量存储集合名称
                .build();
    }
}
