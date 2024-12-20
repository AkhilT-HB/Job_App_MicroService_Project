package com.companyappms.companyms.company.impl;

import java.util.List;
import java.util.Optional;

import com.companyappms.companyms.company.clients.ReviewClient;
import com.companyappms.companyms.company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import com.companyappms.companyms.company.Company;
import com.companyappms.companyms.company.CompanyRepository;
import com.companyappms.companyms.company.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

	private CompanyRepository companyRepository;
	private ReviewClient reviewClient;

	public CompanyServiceImpl(CompanyRepository companyRepository, ReviewClient reviewClient) {
		this.companyRepository = companyRepository;
        this.reviewClient = reviewClient;
    }

	@Override
	public List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	@Override
	public void addCompany(Company company) {
		companyRepository.save(company);
	}
	
	@Override
	public Company getCompanyById(Long id) {
		return companyRepository.findById(id).orElse(null);
		
	}

	@Override
	public boolean deleteCompanyById(Long id) {
		if (companyRepository.existsById(id)) {
			companyRepository.deleteById(id);
			return true;
		}
		return false;

	}

	@Override
	public boolean updateCompany(Long id, Company updatedCompany) {
		Optional<Company> companyOptional = companyRepository.findById(id);
		if (companyOptional.isPresent()) {
			Company company = companyOptional.get();
			company.setName(updatedCompany.getName());
			company.setDescription(updatedCompany.getDescription());
			//company.setJobs(updatedCompany.getJobs());
			companyRepository.save(company);
			return true;
		}
		return false;
	}

	@Override
	public void updateCompanyRating(ReviewMessage reviewMessage) {
		System.out.println(reviewMessage.getDescription());
		Company company = companyRepository.findById(reviewMessage.getCompanyId())
				.orElseThrow(() -> new NotFoundException("Company not found"+reviewMessage.getCompanyId()));

		double averageRating = reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());
		//System.out.println(averageRating);
		company.setRating(averageRating);
		companyRepository.save(company);
	}


}
