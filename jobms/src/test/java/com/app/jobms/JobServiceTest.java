package com.app.jobms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.app.jobms.job.entity.Job;
import com.app.jobms.job.repository.JobRepository;
import com.app.jobms.job.service.JobService;
import com.app.jobms.job.dto.JobWithCompanyDTO;
import com.app.jobms.job.external.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobService jobService;

    private Job job;
    private Company company;
    private JobWithCompanyDTO jobWithCompanyDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        job = new Job();
        job.setId(1L);
        job.setTitle("Example Job");

        jobWithCompanyDTO = new JobWithCompanyDTO();
        jobWithCompanyDTO.setId(1L);
        jobWithCompanyDTO.setTitle("Example Job");
        jobWithCompanyDTO.setCompany(company);
    }

    @Test
    public void testGetJobById() {
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));
        JobWithCompanyDTO found = jobService.findJob(1L);
        assertEquals("Example Job", found.getTitle());
    }

    @Test
    public void testAddJob() {
        jobService.createJob(job);
        verify(jobRepository, times(1)).save(job);
    }
}