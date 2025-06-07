package com.unilink.user_service.entity;

import com.unilink.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "users", name = "user_skills")
public class UserSkill extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    private Boolean isPrimarySkill;
    private String interestLevel;
    private String certificationUrl;
    private Integer yearsOfExperience;
    private Integer endorsementCount;
    private String proficiencyLevel;

}
