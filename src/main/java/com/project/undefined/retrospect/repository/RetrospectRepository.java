package com.project.undefined.retrospect.repository;

import com.project.undefined.retrospect.entity.Retrospect;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetrospectRepository extends JpaRepository<Retrospect, Long> {

    Optional<Retrospect> findByStageId(Long stageId);
}
