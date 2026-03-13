package com.prapti.aiknowledgeassistant.document.dto;

import lombok.Data;

@Data
public class DocumentDTO {
    private String fileName;

    private String fileType;

    private Long fileSize;

    private String uploadedBy;
}
