package com.prapti.aiknowledgeassistant.vectorstore.repository;

import com.prapti.aiknowledgeassistant.vectorstore.model.DocumentChunk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentChunkRepository extends JpaRepository<DocumentChunk, UUID> {
}
