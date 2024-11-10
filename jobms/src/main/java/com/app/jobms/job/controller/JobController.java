package com.app.jobms.job.controller;



import com.app.jobms.job.service.JobService;
import com.app.jobms.job.dto.JobWithCompanyDTO;
import com.app.jobms.job.entity.Job;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/jobs")
public class JobController {


    private final JobService jobService;

    public JobController(JobService jobservice){
        this.jobService = jobservice;
    }

    @GetMapping
    public ResponseEntity<List<JobWithCompanyDTO>> findAll(){

        return ResponseEntity.ok(jobService.findAll());
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody Job job){
        jobService.createJob(job);
        return new ResponseEntity<String>("Job created",HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJob(@PathVariable Long id){

        JobWithCompanyDTO jobWithCompanyDTO= jobService.findJob(id);
        if(jobWithCompanyDTO!=null){
            return new ResponseEntity<>(jobWithCompanyDTO, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id){
        Boolean x = jobService.removeJob(id);
        if(x){
            System.out.println(x);
            return new ResponseEntity<>("Deleted Successfully",HttpStatus.GONE);

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id,@RequestBody Job udpatedJob){
        Boolean updated = jobService.updateJob(id,udpatedJob);
        if(updated){
            return new ResponseEntity<>("Updated Successfully",HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}
