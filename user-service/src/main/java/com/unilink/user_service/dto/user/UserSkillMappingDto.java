package com.unilink.user_service.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSkillMappingDto {
    private Long id;
    private Long userId;
    private List<Long> skillIds;
    private Boolean isPrimarySkill;
    private String interestLevel;
    private Double yearsOfExperience;
    private String proficiencyLevel;
}