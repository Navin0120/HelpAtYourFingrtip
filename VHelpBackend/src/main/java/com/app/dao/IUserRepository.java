package com.app.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.pojos.User;

public interface IUserRepository extends JpaRepository<User,Long>{
	@Query("select distinct u from User u left outer join fetch u.roles where u.email=:nm")
	Optional<User> findByUserEmail(@Param("nm") String email);
	
	@Query("select  u from User u where u.email=:nm")
	Optional<User> fetchUserDetails(@Param("nm") String email);

}
