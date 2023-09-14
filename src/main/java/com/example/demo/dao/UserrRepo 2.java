package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.dao.*;
import com.example.demo.Model.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserrRepo extends CrudRepository<Userr, String>{
	Userr findByUsername(String username);
}
