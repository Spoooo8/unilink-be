package com.unilink.user_service.repository.user;


import com.unilink.user_service.entity.user.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {
   Optional<Skill> findById(Long skillId);
   List<Skill> findByIsDeactivated(boolean active);

    Optional<Skill> findByName(String skillName);
}