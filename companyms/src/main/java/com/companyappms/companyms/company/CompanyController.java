package com.companyappms.companyms.company;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/companies")
public class CompanyController {

	private CompanyService companyService;

	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}

	@GetMapping
	public ResponseEntity<List<Company>> getCompanies() {
		List<Company> companies = companyService.getAllCompanies();

		return new ResponseEntity<>(companies, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<String> addCompany(@RequestBody Company company) {
		companyService.addCompany(company);

		return new ResponseEntity<>("Company added successfully", HttpStatus.CREATED);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Company> getCompanyById(@PathVariable Long id){
		Company company = companyService.getCompanyById(id);
		
		if (company != null) {
			return new ResponseEntity<>(company,HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCompanyById(@PathVariable Long id){
		if(companyService.deleteCompanyById(id)) {
			return new ResponseEntity<>("Company deleted successfully",HttpStatus.OK);
		}
		
		return new ResponseEntity<>("Company not found",HttpStatus.NOT_FOUND);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateCompany(@PathVariable Long id, @RequestBody Company updatedCompany) {
		if (companyService.updateCompany(id, updatedCompany)) {
			return new ResponseEntity<>("Company updated successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
