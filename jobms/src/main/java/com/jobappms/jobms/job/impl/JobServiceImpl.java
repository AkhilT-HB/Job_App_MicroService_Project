package com.jobappms.jobms.job.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.jobappms.jobms.job.clients.CompanyClient;
import com.jobappms.jobms.job.clients.ReviewClient;
import com.jobappms.jobms.job.dto.JobDTO;
import com.jobappms.jobms.job.external.Company;
import com.jobappms.jobms.job.external.Review;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jobappms.jobms.job.JobClass;
import com.jobappms.jobms.job.JobRepository;
import com.jobappms.jobms.job.JobService;
import org.springframework.web.client.RestTemplate;

import static com.jobappms.jobms.job.mapper.JobMapper.mapToJobWithCompanyDto;


@Service
public class JobServiceImpl implements JobService {

	// private List<JobClass> jobs = new ArrayList<>();

	JobRepository jobRepository;

	@Autowired
	RestTemplate restTemplate;

	private CompanyClient companyClient;
	private ReviewClient reviewClient;

	int attempt=0;

	public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
		this.jobRepository = jobRepository;
		this.companyClient = companyClient;
		this.reviewClient = reviewClient;
	}

	@Override
	//@CircuitBreaker(name="companyBreaker",fallbackMethod = "companyBreakerFallBack")
	//@Retry( name="companyBreaker",fallbackMethod = "companyBreakerFallBack" )
	@RateLimiter(name="companyBreaker",fallbackMethod = "companyBreakerFallBack")
	public List<JobDTO> findAllJobs() {
		System.out.println("Attempt is: "+ ++attempt);
		List<JobClass> jobs = jobRepository.findAll();
		List<JobDTO> jobDTOS = new ArrayList<>();

		return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	public List<String> companyBreakerFallBack(Exception e){
		List<String> list = new ArrayList<>();
		list.add("Dummy");
		return list;
	}

	private JobDTO convertToDto(JobClass job){
		//RestTemplate restTemplate =  new RestTemplate();
		Company company = companyClient.getCompany(job.getCompanyId());
		List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

		JobDTO jobDTO = mapToJobWithCompanyDto(job, company, reviews);

		return jobDTO;
	}

	@Override
	public void createJob(JobClass job) {
		jobRepository.save(job);
	}

	@Override
	public JobDTO getJobById(Long id) {
		JobClass job = jobRepository.findById(id).orElse(null);

		return convertToDto(job);
	}

	@Override
	public boolean deleteJobById(Long id) {
		try {
			jobRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean updateJobById(Long id, JobClass updatedJob) {

		Optional<JobClass> jobOptional = jobRepository.findById(id);

		if (jobOptional.isPresent()) {
			JobClass job = jobOptional.get();
			job.setTitle(updatedJob.getTitle());
			job.setDescription(updatedJob.getDescription());
			job.setMinSalary(updatedJob.getMinSalary());
			job.setMaxSalary(updatedJob.getMaxSalary());
			job.setLocation(updatedJob.getLocation());
			jobRepository.save(job);
			return true;
		}
		return false;
	}

}
