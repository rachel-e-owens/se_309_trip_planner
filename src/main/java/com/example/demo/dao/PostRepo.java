package com.example.demo.dao;

import com.example.demo.Model.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends CrudRepository<Post, String> {
    Post findByid(int id);
}
