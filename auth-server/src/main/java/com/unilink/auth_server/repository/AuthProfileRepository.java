package com.unilink.auth_server.repository;

import com.unilink.auth_server.entity.AuthProfile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthProfileRepository extends CrudRepository<AuthProfile, Long> {
    Optional<AuthProfile> findByEmail(String username);
}
