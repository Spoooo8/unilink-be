package com.unilink.common.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Column(name = "entry_ts")
    private LocalDateTime entryTs;

    @Column(name = "is_deactivated")
    private Boolean isDeactivated = false;

    @Column(name = "deactivated_ts")
    private LocalDateTime deactivatedTs;

    @Column(name = "deactivated_by")
    private Integer deactivatedBy = 0;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "created_ts", nullable = false)
    private LocalDateTime createdTs;

    @Column(name = "modified_by")
    private Integer modifiedBy = 0;

    @Column(name = "modified_ts")
    private LocalDateTime modifiedTs;



    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.entryTs = now;
        this.createdTs = now;
        this.createdBy = 0;
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedTs = LocalDateTime.now();
        this.modifiedBy =0;
        if (Boolean.TRUE.equals(this.isDeactivated)) {
            this.deactivatedTs = LocalDateTime.now();
            this.deactivatedBy = 0;
        }
    }
}
