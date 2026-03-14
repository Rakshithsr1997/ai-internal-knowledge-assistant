package com.prapti.aiknowledgeassistant.vectorstore.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "document_chunks")
@Data
public class DocumentChunk {
    @Id
    private UUID id;

    @Column(name = "document_id", nullable = false)
    private UUID documentId;

    @Column(name = "chunk_index", nullable = false)
    private int chunkIndex;

    @Column(name = "chunk_text", columnDefinition = "TEXT", nullable = false)
    private String chunkText;

    @Column(name = "embedding", columnDefinition = "vector(1536)")
    private List<Double> embedding;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}