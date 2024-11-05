package com.app.companyms.company.service;

import com.app.companyms.company.entity.Company;
import com.app.companyms.company.dto.ReviewMessage;

import java.util.List;

public interface CompanyService {

    List<Company> getAllCompanies();


    Company getCompanyById(Long id);

    Boolean updateCompany(Long id,Company updatedCompany);

    void addCompany(Company newCompany);
    public void updateCompanyRating(ReviewMessage reviewMessage);

    Boolean deleteCompany(Long id);
}
