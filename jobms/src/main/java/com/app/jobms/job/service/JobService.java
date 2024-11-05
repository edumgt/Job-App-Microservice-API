package com.app.jobms.job.service;

import com.app.jobms.job.dto.JobWithCompanyDTO;
import com.app.jobms.job.entity.Job;

import java.util.List;

public interface JobService {

     List<JobWithCompanyDTO> findAll();

     String createJob(Job job);


    JobWithCompanyDTO findJob(Long id);

    Boolean removeJob(Long id);
    Boolean updateJob(Long id, Job job);
}
