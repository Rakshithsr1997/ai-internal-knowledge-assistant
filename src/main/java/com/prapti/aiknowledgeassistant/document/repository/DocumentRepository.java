package com.prapti.aiknowledgeassistant.document.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prapti.aiknowledgeassistant.document.model.Document;

import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {

}
