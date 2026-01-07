package com.ollama.lamma.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserChatClientConfig {

    @Value("classpath:/promptTemplates/UserServiceSystemPromptTemplate.st")
    Resource UserServiceSystemPromptTemplate;


    @Bean("userChatClient")
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory,
                                 RetrievalAugmentationAdvisor retrievalAugmentationAdvisor) {
        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        return chatClientBuilder
                .defaultAdvisors(List.of(loggerAdvisor, memoryAdvisor))
                .defaultSystem(UserServiceSystemPromptTemplate)
                .build();
    }

/*    @Bean
    public RetrievalAugmentationAdvisor retrievalAugmentationAdvisor(VectorStore vectorStore,
                                                                     ChatClient.Builder chatClientBuilder) {
        return RetrievalAugmentationAdvisor.builder()
                //queryTransformers-> rewrite the query for send to llm
                .queryTransformers(RewriteQueryTransformer.builder().
                        chatClientBuilder(chatClientBuilder.clone()).build())
                .documentRetriever(
                        VectorStoreDocumentRetriever.builder()
                                .vectorStore(vectorStore)   // 🔥 REQUIRED
                                *//*.topK(3)*//*
                                .similarityThreshold(0.5)
                                .build()
                )
                .build();
    }*/

}
