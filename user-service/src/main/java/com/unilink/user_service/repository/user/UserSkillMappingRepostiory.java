package com.unilink.user_service.repository.user;


import com.unilink.user_service.entity.user.UserSkillMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSkillMappingRepostiory extends JpaRepository<UserSkillMapping, Long> {
    List<UserSkillMapping> findByUserId(Long id);

    void deleteByUserId(Long userId);
}
