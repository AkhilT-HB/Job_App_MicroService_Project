package com.jobappms.jobms.job;

import com.jobappms.jobms.job.dto.JobDTO;

import java.util.List;

public interface JobService {
	
	List<JobDTO> findAllJobs();
	
	void createJob(JobClass job);

	JobDTO getJobById(Long id);

	boolean deleteJobById(Long id);

	boolean updateJobById(Long id, JobClass updatedJob);

}
