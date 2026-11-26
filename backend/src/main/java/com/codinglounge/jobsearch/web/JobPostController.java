package com.codinglounge.jobsearch.web;

import com.codinglounge.jobsearch.model.JobPost;
import com.codinglounge.jobsearch.service.JobPostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class JobPostController {
    private final JobPostService service;
    public JobPostController(JobPostService service){this.service=service;}

    @GetMapping("/posts")
    public Page<JobPost> posts(@RequestParam(required=false) String q,
        @RequestParam(required=false) String location,
        @RequestParam(required=false) String experience,
        @RequestParam(defaultValue="0") int page,
        @RequestParam(defaultValue="20") int size) {
        return service.search(q,location,experience,PageRequest.of(page,Math.min(size,100),Sort.by(Sort.Direction.DESC,"createdAt")));
    }
    @PostMapping("/posts")
    public ResponseEntity<JobPost> create(@Valid @RequestBody JobPost post){return ResponseEntity.status(HttpStatus.CREATED).body(service.importPost(post));}
    @PostMapping("/posts/import")
    public Map<String,Object> importPosts(@RequestBody List<JobPost> posts) {
        List<JobPost> saved=posts.stream().map(service::importPost).toList();
        return Map.of("received",posts.size(),"savedOrMatched",saved.size(),"posts",saved);
    }
    @GetMapping("/stats")
    public Map<String,Object> stats(){return Map.of("totalPosts",service.count());}
    @GetMapping("/posts/{id}")
    public ResponseEntity<JobPost> get(@PathVariable Long id){return service.find(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}
}
