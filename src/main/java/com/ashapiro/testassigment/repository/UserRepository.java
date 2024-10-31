package com.ashapiro.testassigment.repository;

import com.ashapiro.testassigment.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends MongoRepository<User, UUID> {

    Optional<User> findUserByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsById(UUID id);
}
