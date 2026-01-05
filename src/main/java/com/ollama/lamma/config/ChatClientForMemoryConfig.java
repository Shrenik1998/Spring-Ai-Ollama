package com.ollama.lamma.config;

import com.ollama.lamma.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatClientForMemoryConfig {

    @Bean("chatMemoryChatClient")
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory,
                                 RetrievalAugmentationAdvisor retrievalAugmentationAdvisor) {
        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        return chatClientBuilder
                .defaultAdvisors(List.of(loggerAdvisor, memoryAdvisor, retrievalAugmentationAdvisor))
                .build();
    }

    @Bean
    public RetrievalAugmentationAdvisor retrievalAugmentationAdvisor(VectorStore vectorStore) {
        return RetrievalAugmentationAdvisor.builder()
                .documentRetriever(
                        VectorStoreDocumentRetriever.builder()
                                .vectorStore(vectorStore)   // 🔥 REQUIRED
                                /*.topK(3)*/
                                .similarityThreshold(0.5)
                                .build()
                )
                .build();
    }

}
