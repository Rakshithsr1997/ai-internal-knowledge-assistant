-- pgvector index for fast similarity search

CREATE INDEX idx_document_chunks_embedding
    ON document_chunks
    USING ivfflat (embedding vector_cosine_ops)
    WITH (lists = 100);

-- Update query planner statistics

ANALYZE document_chunks;