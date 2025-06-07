package com.unilink.quiz_service.repository.users;

import com.unilink.quiz_service.entity.users.UserSkillMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSkillMappingRepostiory extends JpaRepository<UserSkillMapping, Long> {
    List<UserSkillMapping> findByUserId(Long id);

    void deleteByUserId(Long userId);
}
