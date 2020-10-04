package com.stub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stub.entity.Organization;
import com.stub.entity.repo.OrganaizationAddressRepository;
import com.stub.entity.repo.OrganizationRepository;
@Service
public class OrganizationService {
	@Autowired
    private OrganizationRepository organizationRepository;
	@Autowired
    private OrganaizationAddressRepository organaizationAddressRepository;


    @Transactional
    public ResponseEntity<Object> createOrganization(Organization organization) {
        Organization org = new Organization();
        org.setName(organization.getName());
        org.setOrgId(organization.getOrgId());
        org.setOrganaizationAddress(organization.getOrganaizationAddress());
        Organization savedOrg = organizationRepository.save(org);
        if(organizationRepository.findById(savedOrg.getId()).isPresent())
            return ResponseEntity.ok().body("Organization created successfully.");
        else return ResponseEntity.unprocessableEntity().body("Failed to create the organization specified.");
    }
    @Transactional
    public ResponseEntity<Object> updateOrganization(Long id, Organization org) {
        if(organizationRepository.findById(id).isPresent()) {
            Organization organization = organizationRepository.findById(id).get();
            organization.setName(org.getName());
            organization.setOrgId(org.getName());
            organaizationAddressRepository.deleteById(organization.getOrganaizationAddress().getId());
            organization.setOrganaizationAddress(org.getOrganaizationAddress());
            Organization savedOrganization = organizationRepository.save(organization);
            if(organizationRepository.findById(savedOrganization.getId()).isPresent())
                return ResponseEntity.ok().body("Successfully Updated Organization");
            else return ResponseEntity.unprocessableEntity().body("Failed to update the specified Organization");
        } else return ResponseEntity.unprocessableEntity().body("The specified Organization is not found");
    }
}