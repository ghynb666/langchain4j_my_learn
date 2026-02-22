package com.atguigu.study.controller;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import io.qdrant.client.QdrantClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return null;
    }

    @GetMapping(value = "/embedding/createCollection")
    public void createCollection()
    {

    }

    @GetMapping(value = "/embedding/add")
    public String add()
    {
        return null;
    }

    @GetMapping(value = "/embedding/query1")
    public void query1(){

    }

    @GetMapping(value = "/embedding/query2")
    public void query2(){

    }
}
