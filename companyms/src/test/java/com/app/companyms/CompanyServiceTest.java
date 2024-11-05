package com.app.companyms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.app.companyms.company.entity.Company;
import com.app.companyms.company.repository.CompanyRepository;
import com.app.companyms.company.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    private Company company;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        company = new Company();
        company.setId(1L);
        company.setName("Example Company");
    }

    @Test
    public void testGetCompanyById() {
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        Company found = companyService.getCompanyById(1L);
        assertEquals("Example Company", found.getName());
    }

    @Test
    public void testSaveCompany() {
        companyService.addCompany(company);
        verify(companyRepository, times(1)).save(company);
    }
}