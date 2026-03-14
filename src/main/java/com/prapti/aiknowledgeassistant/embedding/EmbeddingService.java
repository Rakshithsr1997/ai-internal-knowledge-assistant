package com.prapti.aiknowledgeassistant.embedding;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service responsible for generating vector embeddings
 * from text using the configured embedding model.
 */

@Service
public class EmbeddingService {

    private static final Logger log = LoggerFactory.getLogger(EmbeddingService.class);
    private final EmbeddingModel embeddingModel;

    public EmbeddingService(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    /**
     * Generate embedding for a single text input
     */
    public List<Double> generateEmbedding(String text) {

        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Text cannot be null or empty");
        }
        log.debug("Generating embedding for text of length {}", text.length());

        float[] floats = embeddingModel.embed(text);
        List<Double> result = new ArrayList<>();
        for (float f : floats) {
            result.add((double) f);
        }
        return result;
    }

    /**
     * Generate embeddings for multiple texts (used for document chunks)
     */
    public List<List<Double>> generateEmbeddings(List<String> texts) {

        List<List<Double>> embeddings = new ArrayList<>();

        if (texts == null || texts.isEmpty()) {
            return embeddings;
        }
        log.info("Generating embeddings for {} chunks", texts.size());

        for (String text : texts) {
            embeddings.add(generateEmbedding(text));
        }

        return embeddings;
    }
}
