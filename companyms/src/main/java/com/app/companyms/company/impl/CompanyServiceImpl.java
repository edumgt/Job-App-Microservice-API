package com.app.companyms.company.impl;


import com.app.companyms.company.entity.Company;
import com.app.companyms.company.repository.CompanyRepository;
import com.app.companyms.company.service.CompanyService;
import com.app.companyms.company.clients.ReviewClient;
import com.app.companyms.company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CompanyServiceImpl implements CompanyService {


    private final CompanyRepository companyRepository;
    private ReviewClient reviewClient;


    public CompanyServiceImpl(CompanyRepository companyRepository,ReviewClient reviewClient) {
        this.companyRepository = companyRepository;
        this.reviewClient = reviewClient;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(Long id) {

        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage){
        Company company = companyRepository.findById(reviewMessage.getCompanyId()).orElseThrow(()-> new NotFoundException("Company not found" + reviewMessage.getCompanyId()));

        double averageRating= reviewClient.getAverageRating(reviewMessage.getCompanyId());
        company.setRating(averageRating);
        companyRepository.save(company);
    }

    @Override
    public Boolean updateCompany(Long id,Company updatedCompany) {
        Optional<Company> companyOptional = companyRepository.findById(id);

        if(companyOptional.isPresent()){
            Company companyToUpdate = companyOptional.get();
            companyToUpdate.setDescription(updatedCompany.getDescription());
            companyToUpdate.setName(updatedCompany.getName());
            companyRepository.save(companyToUpdate);
            return true;
        }

        return false;
    }

    @Override
    public void addCompany(Company newCompany) {
        companyRepository.save(newCompany);

    }

    @Override
    public Boolean deleteCompany(Long id) {
//        Optional<Company> companyDelete = companyRepository.findById(id);
         if(companyRepository.existsById(id)){
            companyRepository.deleteById(id);
            return true;
        }
         else{
            return false;

         }

    }
}
