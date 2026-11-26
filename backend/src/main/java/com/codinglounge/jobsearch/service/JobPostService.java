package com.codinglounge.jobsearch.service;

import com.codinglounge.jobsearch.model.JobPost;
import com.codinglounge.jobsearch.repo.JobPostRepository;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class JobPostService {
    private final JobPostRepository repository;
    private final PostParser parser;
    public JobPostService(JobPostRepository repository, PostParser parser){this.repository=repository;this.parser=parser;}

    public Page<JobPost> search(String q, String location, String experience, Pageable pageable) {
        Specification<JobPost> spec = (root, query, cb) -> cb.conjunction();
        if (q != null && !q.isBlank()) {
            String like="%"+q.toLowerCase()+"%";
            spec=spec.and((r,cq,cb)->cb.or(cb.like(cb.lower(r.get("jobTitle")),like),cb.like(cb.lower(r.get("companyName")),like),cb.like(cb.lower(r.get("postContent")),like),cb.like(cb.lower(r.get("skills")),like)));
        }
        if(location!=null&&!location.isBlank()) spec=spec.and((r,cq,cb)->cb.like(cb.lower(r.get("location")),"%"+location.toLowerCase()+"%"));
        if(experience!=null&&!experience.isBlank()) spec=spec.and((r,cq,cb)->cb.like(cb.lower(r.get("experience")),"%"+experience.toLowerCase()+"%"));
        return repository.findAll(spec,pageable);
    }

    @Transactional
    public JobPost importPost(JobPost post) {
        if(post.getPostUrl()!=null&&!post.getPostUrl().isBlank()) {
            Optional<JobPost> existing=repository.findByPostUrl(post.getPostUrl());
            if(existing.isPresent()) return existing.get();
        }
        parser.enrich(post);
        return repository.save(post);
    }
    public long count(){return repository.count();}
    public Optional<JobPost> find(Long id){return repository.findById(id);}
}
