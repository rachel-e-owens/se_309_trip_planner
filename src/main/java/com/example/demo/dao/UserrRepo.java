package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.Model.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository of all Users
 * @author david
 */
@Repository
public interface UserrRepo extends CrudRepository<Userr, String>{
	Optional<Userr> findByUsername(String username);
}
