package com.project.undefined.job.repository;

import com.project.undefined.job.entity.Job;
import com.project.undefined.job.entity.Stage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {

    List<Stage> findByJob(Job job);
}
