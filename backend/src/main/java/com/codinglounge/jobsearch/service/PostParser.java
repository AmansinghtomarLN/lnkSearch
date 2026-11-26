package com.codinglounge.jobsearch.service;

import com.codinglounge.jobsearch.model.JobPost;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.regex.*;

@Component
public class PostParser {
    private static final List<String> SKILLS = List.of("Java","Spring Boot","Microservices","Kafka","AWS","Azure","Python","React","SQL","Docker","Kubernetes","MySQL","JPA","Hibernate","REST API","Solace");
    private static final Pattern EXPERIENCE = Pattern.compile("(?i)(\\d{1,2}\\s*\\+?\\s*(?:-|to)\\s*\\d{1,2}\\s*years?|\\d{1,2}\\s*\\+\\s*years?|\\d{1,2}\\s+years?)");
    private static final Pattern EMAIL = Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}", Pattern.CASE_INSENSITIVE);
    private static final Pattern PHONE = Pattern.compile("(?:\\+?\\d[\\d ()-]{8,}\\d)");
    public void enrich(JobPost p) {
        String body = Optional.ofNullable(p.getPostContent()).orElse("");
        if (blank(p.getExperience())) p.setExperience(find(EXPERIENCE, body));
        if (blank(p.getContactEmail())) p.setContactEmail(find(EMAIL, body));
        if (blank(p.getContactPhone())) p.setContactPhone(find(PHONE, body));
        if (blank(p.getJobTitle())) {
            String lower = body.toLowerCase();
            for (String role : List.of("Senior Java Developer","Java Backend Developer","Java Developer","Backend Engineer","Software Engineer","Spring Boot Developer","Technical Lead")) {
                if (lower.contains(role.toLowerCase())) { p.setJobTitle(role); break; }
            }
        }
        if (blank(p.getLocation())) {
            for (String city : List.of("Bangalore","Bengaluru","Hyderabad","Pune","Mumbai","Delhi","Gurgaon","Noida","Jaipur","Remote")) {
                if (body.toLowerCase().contains(city.toLowerCase())) { p.setLocation(city); break; }
            }
        }
        if (blank(p.getCompanyName())) {
            Matcher m = Pattern.compile("(?i)(?:at|company\\s*[:=-])\\s*([A-Z][A-Za-z0-9& .]{1,50})").matcher(body);
            if (m.find()) p.setCompanyName(m.group(1).trim());
        }
        if (blank(p.getSkills())) {
            List<String> found = SKILLS.stream().filter(s -> body.toLowerCase().contains(s.toLowerCase())).toList();
            p.setSkills(String.join(", ", found));
        }
        if (blank(p.getAuthorName())) p.setAuthorName("Not provided");
    }
    private String find(Pattern p, String s) { Matcher m=p.matcher(s); return m.find()?m.group().trim():null; }
    private boolean blank(String s){return s==null||s.isBlank();}
}
