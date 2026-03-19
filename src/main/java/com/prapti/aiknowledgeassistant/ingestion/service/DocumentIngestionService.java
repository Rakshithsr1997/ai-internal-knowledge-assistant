package com.prapti.aiknowledgeassistant.ingestion.service;

import com.prapti.aiknowledgeassistant.chunking.ChunkingService;
import com.prapti.aiknowledgeassistant.embedding.EmbeddingService;
import com.prapti.aiknowledgeassistant.extraction.FileTextExtractor;
import com.prapti.aiknowledgeassistant.vectorstore.service.VectorStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


/**
 * Orchestrates the document ingestion pipeline.
 * Executes extraction → chunking → embedding → vector storage
 */
@Slf4j
@Service
public class DocumentIngestionService {


    private final FileTextExtractor extractor;
    private final ChunkingService chunkingService;
    private final EmbeddingService embeddingService;
    private final VectorStoreService vectorStoreService;

    public DocumentIngestionService(FileTextExtractor extractor,
                                    ChunkingService chunkingService,
                                    EmbeddingService embeddingService,
                                    VectorStoreService vectorStoreService) {
        this.extractor = extractor;
        this.chunkingService = chunkingService;
        this.embeddingService = embeddingService;
        this.vectorStoreService = vectorStoreService;
    }

    public void ingestDocument(UUID documentId, MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        log.info("Starting ingestion for documentId: {}", documentId);

        // Extract text
        String text = extractor.extractText(file);

        // Chunk text
        List<String> chunks = chunkingService.chunkText(text);

        if (chunks.isEmpty()) {
            throw new IllegalStateException("No chunks generated from document");
        }

        log.info("Generated {} chunks", chunks.size());

        // Generate embeddings
        List<List<Double>> embeddings = embeddingService.generateEmbeddings(chunks);


        if (chunks.size() != embeddings.size()) {
            throw new IllegalStateException("Chunks and embeddings size mismatch");
        }

        log.info("Generated {} embeddings", embeddings.size());

        // Store vectors
        vectorStoreService.storeChunks(documentId, chunks, embeddings);

        log.info("Completed ingestion for documentId: {}", documentId);
    }
}
