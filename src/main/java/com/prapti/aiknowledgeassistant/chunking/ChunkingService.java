package com.prapti.aiknowledgeassistant.chunking;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Service responsible for splitting extracted document text
 * into smaller chunks for downstream embedding and vector search.
 */

@Service
public class ChunkingService {
    private static final int CHUNK_SIZE = 500;   // characters
    private static final int OVERLAP = 50;       // characters

    public List<String> chunkText(String text) {

        List<String> chunks = new ArrayList<>();

        if (text == null || text.isEmpty()) {
            return chunks;
        }

        int start = 0;
        int textLength = text.length();

        while (start < textLength) {

            int end = Math.min(start + CHUNK_SIZE, textLength);

            String chunk = text.substring(start, end).trim();

            if (!chunk.isEmpty()) {
                chunks.add(chunk);
            }

            start += CHUNK_SIZE - OVERLAP;
        }

        return chunks;
    }
}