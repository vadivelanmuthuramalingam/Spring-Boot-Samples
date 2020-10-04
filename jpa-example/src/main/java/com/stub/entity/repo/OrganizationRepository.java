package com.stub.entity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stub.entity.Organization;
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

}