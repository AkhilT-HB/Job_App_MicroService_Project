package com.jobappms.jobms.job.mapper;

import com.jobappms.jobms.job.JobClass;
import com.jobappms.jobms.job.dto.JobDTO;
import com.jobappms.jobms.job.external.Company;
import com.jobappms.jobms.job.external.Review;

import java.util.List;

public class JobMapper {

    public static JobDTO mapToJobWithCompanyDto(JobClass job, Company company, List<Review> reviews){
        JobDTO jobDTO = new JobDTO();

        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setCompany(company);
        jobDTO.setReviews(reviews);

        return jobDTO;
    }
}
