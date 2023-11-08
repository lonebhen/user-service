package com.example.userservice.repository;

import com.example.userservice.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByUsername(String username);
}
