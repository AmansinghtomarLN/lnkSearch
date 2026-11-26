# LinkedIn Job Search App

A full-stack job-post intake and management application.

## Stack
- Java 21, Spring Boot 3.5.6, Spring Data JPA, MySQL
- React 18, TypeScript, Vite
- Apache POI for Excel export
- CSV/XLSX import and paste/import of user-authorized post content

## Important data-source boundary
This application does not automate LinkedIn login, scroll LinkedIn, scrape its DOM, use private endpoints, or collect posts automatically. Use only content obtained through an authorized API/export or content you are authorized to provide. The LinkedIn search launcher opens the normal LinkedIn search page for a human user; importing is a separate user-controlled action.

## Run locally

### 1. Database
```sql
CREATE DATABASE linkedin_job_search CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. Backend
Set DB_URL, DB_USERNAME and DB_PASSWORD if your local MySQL credentials differ from the defaults, then:
```bash
cd backend
mvn spring-boot:run
```
Backend: http://localhost:8080  
Swagger: http://localhost:8080/swagger-ui.html

### 3. Frontend
```bash
cd frontend
npm install
npm run dev
```
Frontend: http://localhost:5173

## Features
- Search configuration and LinkedIn search URL launcher
- Import authorized post content from CSV/XLSX
- Rule-based extraction of role, experience, location, company, skills and recruiter details
- Job post search/filter/pagination
- Recruiter details panel
- Excel export
- Duplicate protection using source URL where present

## CSV/XLSX import format
Headers accepted: `postUrl,postContent,authorName,authorProfileUrl,companyName,postedAt,location,jobTitle,experience,contactEmail,contactPhone`.
