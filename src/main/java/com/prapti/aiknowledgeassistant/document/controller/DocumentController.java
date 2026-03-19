package com.prapti.aiknowledgeassistant.document.controller;

import com.prapti.aiknowledgeassistant.document.dto.DocumentDTO;
import com.prapti.aiknowledgeassistant.document.model.Document;
import com.prapti.aiknowledgeassistant.document.service.DocumentService;
import com.prapti.aiknowledgeassistant.ingestion.service.DocumentIngestionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * REST controller for handling document upload and ingestion requests
 */
@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor   
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentIngestionService ingestionService;

    private static final Logger log = LoggerFactory.getLogger(DocumentController.class);

    @PostMapping
    public Document saveDocumentMetadata(@RequestBody DocumentDTO documentDTO) {

        Document document = Document.builder()
                .fileName(documentDTO.getFileName())
                .fileType(documentDTO.getFileType())
                .fileSize(documentDTO.getFileSize())
                .uploadedBy(documentDTO.getUploadedBy())
                .build();

        return documentService.saveDocument(document);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadAndIngestDocument(
            @RequestParam("file") MultipartFile file) {

        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File cannot be empty");
            }

            Document document = Document.builder()
                    .fileName(file.getOriginalFilename())
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .uploadedBy("system")
                    .build();

            Document savedDocument = documentService.saveDocument(document);

            UUID documentId = savedDocument.getId();

            ingestionService.ingestDocument(documentId, file);

            return ResponseEntity.ok(savedDocument);

        } catch (Exception e) {

            log.error("Error processing document upload for file: {}", file.getOriginalFilename(), e);

            return ResponseEntity
                    .status(500)
                    .body("Failed to process document: " + e.getMessage());
        }
    }

}
