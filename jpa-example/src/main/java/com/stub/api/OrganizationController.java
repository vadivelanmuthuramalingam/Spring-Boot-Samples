package com.stub.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stub.entity.Organization;
import com.stub.entity.repo.OrganizationRepository;
import com.stub.service.OrganizationService;

import io.swagger.annotations.ApiOperation;

import java.util.List;
@RestController
public class OrganizationController {
	@Autowired
    private OrganizationService organizationService;
	@Autowired
    private OrganizationRepository organizationRepository;


    @PostMapping("/organization/create")
    @ApiOperation(value = "Create a new organizartion")
    public ResponseEntity<Object> createOrganization(@RequestBody Organization organization) {
        return organizationService.createOrganization(organization);
    }
    @DeleteMapping("/organization/delete/{id}")
    @ApiOperation(value = "Delete organization")
    public ResponseEntity<Object> deleteOrganization(@PathVariable Long id) {
        if(organizationRepository.findById(id).isPresent()) {
            organizationRepository.deleteById(id);
            if (organizationRepository.findById(id).isPresent())
                return ResponseEntity.unprocessableEntity().body("Failed to delete the specified organization");
            else return ResponseEntity.ok("Successfully deleted the specified organization");
        } else return ResponseEntity.unprocessableEntity().body("Specified organization not present");
    }
    @GetMapping("/organization/get/{id}")
    @ApiOperation(value = "get particular organization details")
    public Organization getOrganization(@PathVariable Long id) {
        if(organizationRepository.findById(id).isPresent())
            return organizationRepository.findById(id).get();
        else return null;
    }
    @GetMapping("/organization/get")
    @ApiOperation(value = "Get All organization Details")
    public List<Organization> getOrganizations() {
        return organizationRepository.findAll();
    }
    @PutMapping("/organization/update/{id}")
    @ApiOperation(value = "Modify organization details.")
    public ResponseEntity<Object> updateOrganization(@PathVariable Long id, @RequestBody Organization org) {
        return organizationService.updateOrganization(id, org);
    }

}