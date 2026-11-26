package com.codinglounge.jobsearch.web;

import com.codinglounge.jobsearch.model.JobPost;
import com.codinglounge.jobsearch.repo.JobPostRepository;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/api/export")
@CrossOrigin(origins = "http://localhost:5173")
public class ExcelController {
    private final JobPostRepository repository;
    public ExcelController(JobPostRepository repository){this.repository=repository;}
    @GetMapping("/excel")
    public ResponseEntity<byte[]> excel() throws IOException {
        try(XSSFWorkbook wb=new XSSFWorkbook(); ByteArrayOutputStream out=new ByteArrayOutputStream()) {
            Sheet sheet=wb.createSheet("Job Posts");
            String[] headers={"ID","Job Title","Company","Experience","Location","Skills","Recruiter","Email","Phone","Posted At","Post URL","Post Content"};
            Row h=sheet.createRow(0);
            for(int i=0;i<headers.length;i++){Cell c=h.createCell(i);c.setCellValue(headers[i]);}
            List<JobPost> posts=repository.findAll(Sort.by(Sort.Direction.DESC,"createdAt"));
            int rowNum=1;
            for(JobPost p:posts){
                Row r=sheet.createRow(rowNum++);
                Object[] vals={p.getId(),p.getJobTitle(),p.getCompanyName(),p.getExperience(),p.getLocation(),p.getSkills(),p.getAuthorName(),p.getContactEmail(),p.getContactPhone(),p.getPostedAt()==null?"":p.getPostedAt().toString(),p.getPostUrl(),p.getPostContent()};
                for(int i=0;i<vals.length;i++)r.createCell(i).setCellValue(vals[i]==null?"":vals[i].toString());
            }
            for(int i=0;i<headers.length;i++)sheet.autoSizeColumn(i);
            wb.write(out);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=linkedin-job-posts.xlsx").contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")).body(out.toByteArray());
        }
    }
}
