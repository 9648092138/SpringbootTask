package com.op.task.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.op.task.entity.Users;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<Users, Long>, JpaSpecificationExecutor<Users>{

	@Query( value = "SELECT * FROM users  WHERE mobile_number = ?1", nativeQuery = true)
	Optional<Users> FindbyMob(String mobile_number);

}
