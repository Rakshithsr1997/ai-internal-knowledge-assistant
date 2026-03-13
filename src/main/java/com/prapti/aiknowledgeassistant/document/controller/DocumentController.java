package com.prapti.aiknowledgeassistant.document.controller;

import com.prapti.aiknowledgeassistant.document.dto.DocumentDTO;
import com.prapti.aiknowledgeassistant.document.model.Document;
import com.prapti.aiknowledgeassistant.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor   
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    public Document uploadDocument(@RequestBody DocumentDTO documentDTO) {

        Document document = Document.builder()
                .fileName(documentDTO.getFileName())
                .fileType(documentDTO.getFileType())
                .fileSize(documentDTO.getFileSize())
                .uploadedBy(documentDTO.getUploadedBy())
                .build();

        return documentService.saveDocument(document);
    }

}
