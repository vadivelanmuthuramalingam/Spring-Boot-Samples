package com.stub.entity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stub.entity.OrganizationAddress;

@Repository
public interface OrganaizationAddressRepository extends JpaRepository<OrganizationAddress,Long> {
	
	}
