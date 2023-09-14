package com.example.demo.dao;

import com.example.demo.Model.Friend;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for friend repo
 * @author david
 *
 */
@Repository
public interface FriendRepo extends CrudRepository<Friend, Integer> {
    
	Friend findById(int id);
	
	@Query(value = "SELECT * FROM friend WHERE friend_username = :usernameWant", nativeQuery = true)
	List<Friend> allFriends(@Param("usernameWant") String usernameWant);
}