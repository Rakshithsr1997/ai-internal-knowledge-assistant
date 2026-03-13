package com.prapti.aiknowledgeassistant.extraction;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Extracts raw text content from uploaded files during the ingestion pipeline.
 * Currently supports plain text files (.txt)
 * Can be extended later for other formats such as PDF or DOCX.
 */

@Component
public class FileTextExtractor {

    public String extractText(MultipartFile file) throws IOException {

        String fileType = file.getContentType();

        if (fileType == null) {
            throw new IllegalArgumentException("File type not supported");
        }

        if (fileType.equals("text/plain")) {
            return new String(file.getBytes());
        }

        throw new IllegalArgumentException("Unsupported file type: " + fileType);
    }
}
