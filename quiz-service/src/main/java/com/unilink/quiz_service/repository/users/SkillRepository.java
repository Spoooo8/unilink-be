package com.unilink.quiz_service.repository.users;

import com.unilink.quiz_service.entity.users.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {
   Optional<Skill> findById(Long skillId);
   List<Skill> findByIsDeactivated(boolean active);

    Optional<Skill> findByName(String skillName);
}
