package com.codinglounge.jobsearch.repo;

import com.codinglounge.jobsearch.model.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

public interface JobPostRepository extends JpaRepository<JobPost, Long>, JpaSpecificationExecutor<JobPost> {
    Optional<JobPost> findByPostUrl(String postUrl);
}
