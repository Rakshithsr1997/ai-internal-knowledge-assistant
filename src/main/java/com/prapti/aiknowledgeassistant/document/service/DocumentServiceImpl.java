package com.prapti.aiknowledgeassistant.document.service;

import com.prapti.aiknowledgeassistant.document.model.Document;
import com.prapti.aiknowledgeassistant.document.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Override
    public Document saveDocument(Document document) {
        return documentRepository.save(document);
    }
}
