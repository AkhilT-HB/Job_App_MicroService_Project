package com.companyappms.companyms.company.impl;

import java.util.List;
import java.util.Optional;

import com.companyappms.companyms.company.dto.ReviewMessage;
import org.springframework.stereotype.Service;

import com.companyappms.companyms.company.Company;
import com.companyappms.companyms.company.CompanyRepository;
import com.companyappms.companyms.company.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

	private CompanyRepository companyRepository;

	public CompanyServiceImpl(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
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

	}


}
