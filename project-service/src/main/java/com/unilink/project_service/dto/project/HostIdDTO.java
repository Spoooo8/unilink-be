package com.unilink.project_service.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HostIdDTO {
    private Long userId;
    private Long hostId;
    private Long projectId;
    private Long teamMemberId;
}
