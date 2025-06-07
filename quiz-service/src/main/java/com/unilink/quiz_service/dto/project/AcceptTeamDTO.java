package com.unilink.quiz_service.dto.project;

import com.unilink.quiz_service.utils.TeamMemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcceptTeamDTO{
    private Long id;
    private Long userId;
    private TeamMemberRole teamMemberRole;
    private Long memberRoleId;
    private Long teamId;
}