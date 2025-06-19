package com.unilink.user_service.repository.user;


import com.unilink.user_service.entity.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.BitSet;
import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findById(Long id);

    List<Users> findByIsDeactivated(boolean active);

//    List<Users> findById(List<Long> userIds);

    boolean existsByEmail(String email);

    boolean existsByUserName(String username);

    Optional<Users> findByUserName(String usernameOrEmail);

    Optional<Users> findByEmail(String usernameOrEmail);
}