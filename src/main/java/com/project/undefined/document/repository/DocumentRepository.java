package com.project.undefined.document.repository;

import com.project.undefined.document.entity.Document;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    Optional<Document> findByJobId(Long jobId);
}
