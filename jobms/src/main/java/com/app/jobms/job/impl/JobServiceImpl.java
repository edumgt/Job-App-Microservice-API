package com.app.jobms.job.impl;


import com.app.jobms.job.entity.Job;
import com.app.jobms.job.repository.JobRepository;
import com.app.jobms.job.service.JobService;

import com.app.jobms.job.clients.CompanyClient;
import com.app.jobms.job.clients.ReviewClient;
import com.app.jobms.job.dto.JobWithCompanyDTO;
import com.app.jobms.job.external.Company;
import com.app.jobms.job.external.Review;
import com.app.jobms.job.mapper.JobMapper;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private CompanyClient companyClient;
    private ReviewClient reviewClient;

//    private List<Job> jobs = new ArrayList<>();

    private final JobRepository jobRepository;


    public JobServiceImpl(JobRepository repo,CompanyClient companyClient,ReviewClient reviewClient  ) {
        this.jobRepository = repo;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
//    @CircuitBreaker(name="companyBreaker",fallbackMethod = "companyBreakerFallback")
//    @Retry(name="companyBreaker",fallbackMethod = "companyBreakerFallback")
    @RateLimiter(name="companyBreaker",fallbackMethod = "companyBreakerFallback")
    public List<JobWithCompanyDTO> findAll() {

        List<Job> jobs = jobRepository.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDTOS = new ArrayList<>();



        return jobs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<String> companyBreakerFallback(Exception e){
        List<String> list = new ArrayList<>();
        list.add("You got into this page,\nbecause of high traffic on server or service might not be availble");
        return list;
    }


    private JobWithCompanyDTO convertToDTO(Job job){
        Company company = companyClient.getCompany(job.getCompanyId());
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
        JobWithCompanyDTO jobWithCompanyDTO = JobMapper.mapToJobCompanyDTO(job,company,reviews);
        return jobWithCompanyDTO;
    }

    @Override
    public String createJob(Job job) {
//        jobs.add(job);
        try{
            jobRepository.save(job);
            return "Job Created Successfully";

        }catch (Exception e){
            return e.getMessage();
        }
    }

    @Override
    public JobWithCompanyDTO findJob(Long id) {
//        for(Job job :jobs){
//            if(job.getId().equals(id)){
//                return job;
//            }
//
//        }
        Job job = jobRepository.findById(id).orElse(null);
        return convertToDTO(job);


    }

    @Override
    public Boolean removeJob(Long id) {
//        for(Job job: jobs){
//            if(job.getId().equals(id)){
////                Job x = job ;
//                jobs.remove(job);
//                return Boolean.TRUE;
//            }
//
//        }
        Optional<Job> job = jobRepository.findById(id);
            if(job.isPresent()) {
                jobRepository.deleteById(id);
                return true;
            }

            return false;

        }

//        return Boolean.FALSE;


    @Override
    public Boolean updateJob(Long id, Job jobUp) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()){
            jobUp.setTitle(jobUp.getTitle());
            jobUp.setDescription(jobUp.getDescription());
            jobUp.setLocation(jobUp.getLocation());
            jobUp.setMinSalary(jobUp.getMinSalary());
            jobUp.setMaxSalary(jobUp.getMaxSalary());
            jobRepository.save(jobUp);
            return true;
        }

        return false;

    }


}
