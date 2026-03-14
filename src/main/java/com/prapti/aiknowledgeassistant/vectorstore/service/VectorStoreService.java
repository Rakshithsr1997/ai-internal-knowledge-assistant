package com.prapti.aiknowledgeassistant.vectorstore.service;

import com.prapti.aiknowledgeassistant.vectorstore.model.DocumentChunk;
import com.prapti.aiknowledgeassistant.vectorstore.repository.DocumentChunkRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Stores document chunks and their embedding vectors
 * into the vector database.
 */

@Service
public class VectorStoreService {

    private final DocumentChunkRepository repository;

    public VectorStoreService(DocumentChunkRepository repository) {
        this.repository = repository;
    }

    public void storeChunks(UUID documentId,
                            List<String> chunks,
                            List<List<Double>> embeddings) {

        if (documentId == null) {
            throw new IllegalArgumentException("documentId cannot be null");
        }

        if (chunks == null || chunks.isEmpty()) {
            throw new IllegalArgumentException("chunks cannot be null or empty");
        }

        if (embeddings == null || embeddings.isEmpty()) {
            throw new IllegalArgumentException("embeddings cannot be null or empty");
        }

        if (chunks.size() != embeddings.size()) {
            throw new IllegalArgumentException(
                    "Chunks count (" + chunks.size() + ") does not match embeddings count (" + embeddings.size() + ")"
            );
        }

        List<DocumentChunk> entities = new ArrayList<>();

        for (int i = 0; i < chunks.size(); i++) {

            DocumentChunk chunk = new DocumentChunk();

            chunk.setId(UUID.randomUUID());
            chunk.setDocumentId(documentId);
            chunk.setChunkIndex(i);
            chunk.setChunkText(chunks.get(i));
            chunk.setEmbedding(embeddings.get(i));

            entities.add(chunk);
        }

        repository.saveAll(entities);
    }
}

