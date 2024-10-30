package com.app.jobms.job.mapper;


import com.app.jobms.job.Job;
import com.app.jobms.job.dto.JobWithCompanyDTO;
import com.app.jobms.job.external.Company;
import com.app.jobms.job.external.Review;

import java.util.List;

public class JobMapper {
    public static JobWithCompanyDTO mapToJobCompanyDTO(
            Job job,
            Company company,
            List<Review> reviews){
        JobWithCompanyDTO jobDTO = new JobWithCompanyDTO() ;
        jobDTO.setId(job.getId());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setCompany(company);
        jobDTO.setReviews(reviews);

        return jobDTO;
    }
}
