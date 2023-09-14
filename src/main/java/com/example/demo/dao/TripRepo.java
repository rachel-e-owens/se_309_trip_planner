package com.example.demo.dao;

import com.example.demo.Model.Trip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository of all trips
 * @author hailey
 */
@Repository
public interface TripRepo extends CrudRepository<Trip, String> {
    Trip findByid(int tripid);
}