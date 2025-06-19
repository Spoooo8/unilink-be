package com.unilink.common.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseDTO {
    private LocalDateTime createdAt;
    private Integer createdBy;
    private LocalDateTime modifiedAt;
    private Integer modifiedBy;
    private LocalDateTime deletedAt;
    private Integer deletedBy;
    private Boolean isDeactivated;
}