package com.example.demo.dao;

import com.example.demo.Model.Trip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepo extends CrudRepository<Trip, String> {
    Trip findByid(String id);
}