package com.app.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.Role;
import com.app.pojos.UserRoles;

public interface IRoleRepository extends JpaRepository<Role, Integer> {
	Optional<Role> findByUserRole(UserRoles role);
}
