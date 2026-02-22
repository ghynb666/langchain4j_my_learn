package com.atguigu.study.controller;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.filter.MetadataFilterBuilder;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Collections;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static dev.langchain4j.store.embedding.filter.MetadataFilterBuilder.metadataKey;

import static dev.langchain4j.store.embedding.filter.MetadataFilterBuilder.metadataKey;

@RestController
@Slf4j
public class EmbeddinglController
{
    @Resource
    private EmbeddingModel embeddingModel;
    @Resource
    private QdrantClient qdrantClient;
    @Resource
    private EmbeddingStore<TextSegment> embeddingStore;

    @GetMapping(value = "/embedding/embed")
    public String embed()
    {
        String prompt = """
                   咏鸡
                鸡鸣破晓光，
                红冠映朝阳。
                金羽披霞彩，
                昂首步高岗。
                """;
        Response<Embedding> embed = embeddingModel.embed(prompt);

        return embed.content().toString();
    }

    @GetMapping(value = "/embedding/createCollection")
    public void createCollection()
    {
        // 1. 创建向量参数配置构建器
        Collections.VectorParams vectorParams = Collections.VectorParams.newBuilder()
                // 2. 设置距离度量方式为余弦相似度 (Cosine)，这是文本向量最常用的度量方式
                .setDistance(Collections.Distance.Cosine)
                // 3. 设置向量维度为1024，必须与使用的Embedding模型输出维度一致
                .setSize(1024)
                .build();
        // 4. 异步创建一个名为"test-qdrant"的集合（类似于关系型数据库中的表）
        qdrantClient.createCollectionAsync("test-qdrant",vectorParams);
    }

    @GetMapping(value = "/embedding/add")
    public String add()
    {
        String prompt = """
                咏鸡
                鸡鸣破晓光，
                红冠映朝阳。
                金羽披霞彩，
                昂首步高岗。
                """;

        // 1. 将原始文本封装为 TextSegment 对象，这是 LangChain4j 中处理文本的基本单元
        TextSegment segment = TextSegment.from(prompt);
        // 2. 为文本段添加元数据（Metadata），例如作者、来源、日期等
        // 元数据非常重要，后续检索时可以基于元数据进行过滤（如：只查"haoyu"写的诗）
        segment.metadata().put("author","haoyu");
        
        // 3. 调用 EmbeddingModel 将文本段转换为向量（Embedding）
        // 这里会将 TextSegment 发送给 AI 模型（如 OpenAI 或 DashScope），返回一个高维向量（如 1024 维的 float 数组）
        Embedding content = embeddingModel.embed(segment).content();
        
        // 4. 将向量（Embedding）和对应的原始文本（TextSegment）一起存入向量数据库（EmbeddingStore）
        // 存储后，既可以通过向量相似度搜索到这段文本，也可以获取其元数据
        // 返回值通常是存储记录的 ID
        return embeddingStore.add(content,segment);
    }

    @GetMapping(value = "/embedding/query1")
    public void query1(){
        //1. 先向量化待查询的文本内容
        // 这里需要将用户的自然语言问题（如"咏鸡讲的是什么？"）转换成向量
        // 关键点：查询用的 Embedding 模型必须和入库时用的模型完全一致，否则向量空间不匹配，查出的结果无意义
        Embedding content = embeddingModel.embed("咏鸡讲的是什么？").content();
        System.out.println("Query Vector Dimension: " + content.vector().length);
        if (content.vector().length == 0) {
            System.err.println("CRITICAL ERROR: Embedding vector is empty! This usually means the API call failed to parse the vector.");
        }

        //2. 构建向量数据库的搜索请求对象
        EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(content) // 设置查询向量
                .maxResults(1) // 设置最大返回结果数（Top-K），这里只取相似度最高的 1 条
                .build();
        
        //3. 执行查询
        // 在向量数据库中进行相似度搜索（通常是计算余弦相似度），找出最匹配的 TextSegment
        EmbeddingSearchResult<TextSegment> searchResult = embeddingStore.search(embeddingSearchRequest);
        
        // 打印结果
        if (!searchResult.matches().isEmpty()) {
            System.out.println("查询结果：" + searchResult.matches().get(0).embedded().text());
        }
    }

    @GetMapping(value = "/embedding/query2")
    public void query2(){
        //1. 向量化查询文本
        Embedding content = embeddingModel.embed("咏鸡").content();

        //2. 构建带过滤条件的搜索请求
        EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(content)
                // 添加元数据过滤条件：只查询 author = "haoyu" 的记录
                // 这被称为 "混合检索"（Hybrid Search）或 "带过滤的向量检索"
                // metadataKey 需要静态导入: import static dev.langchain4j.store.embedding.filter.MetadataFilterBuilder.metadataKey;
                .filter(metadataKey("author").isEqualTo("haoyu"))
                .maxResults(1)
                .build();

        //3. 执行查询
        EmbeddingSearchResult<TextSegment> searchResult = embeddingStore.search(embeddingSearchRequest);

        // 4. 获取并打印匹配结果
        if (!searchResult.matches().isEmpty()) {
            // matches() 返回的是 List<EmbeddingMatch<TextSegment>>
            // get(0) 获取最相似的那条记录
            // embedded() 获取其中封装的 TextSegment 对象
            // text() 获取原始文本内容
            System.out.println("带过滤条件的查询结果：" + searchResult.matches().get(0).embedded().text());
        }
    }
}
