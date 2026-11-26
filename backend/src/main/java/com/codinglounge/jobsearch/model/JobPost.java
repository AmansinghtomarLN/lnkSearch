package com.codinglounge.jobsearch.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_post", indexes = {
    @Index(name = "idx_job_post_created", columnList = "createdAt"),
    @Index(name = "idx_job_post_company", columnList = "companyName")
})
public class JobPost {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1200, unique = true)
    private String postUrl;
    @Column(length = 2000) private String authorName;
    @Column(length = 1200) private String authorProfileUrl;
    @Lob private String postContent;
    private LocalDateTime postedAt;
    @Column(length = 300) private String jobTitle;
    @Column(length = 100) private String experience;
    @Column(length = 200) private String location;
    @Column(length = 300) private String companyName;
    @Column(length = 2000) private String skills;
    @Column(length = 320) private String contactEmail;
    @Column(length = 80) private String contactPhone;
    @Column(length = 40) private String source = "USER_IMPORT";
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getPostUrl(){return postUrl;} public void setPostUrl(String v){postUrl=v;}
    public String getAuthorName(){return authorName;} public void setAuthorName(String v){authorName=v;}
    public String getAuthorProfileUrl(){return authorProfileUrl;} public void setAuthorProfileUrl(String v){authorProfileUrl=v;}
    public String getPostContent(){return postContent;} public void setPostContent(String v){postContent=v;}
    public LocalDateTime getPostedAt(){return postedAt;} public void setPostedAt(LocalDateTime v){postedAt=v;}
    public String getJobTitle(){return jobTitle;} public void setJobTitle(String v){jobTitle=v;}
    public String getExperience(){return experience;} public void setExperience(String v){experience=v;}
    public String getLocation(){return location;} public void setLocation(String v){location=v;}
    public String getCompanyName(){return companyName;} public void setCompanyName(String v){companyName=v;}
    public String getSkills(){return skills;} public void setSkills(String v){skills=v;}
    public String getContactEmail(){return contactEmail;} public void setContactEmail(String v){contactEmail=v;}
    public String getContactPhone(){return contactPhone;} public void setContactPhone(String v){contactPhone=v;}
    public String getSource(){return source;} public void setSource(String v){source=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}
