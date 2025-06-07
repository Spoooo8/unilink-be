package com.unilink.user_service.entity;

import com.unilink.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "users", name = "user_certifications")
public class UserCertification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(nullable = false)
    private String certificationName;

    private String certificationIssuer;
    private LocalDateTime certificationDate;
    private LocalDateTime expiryDate;
    private String certificationUrl;

    private LocalDateTime createdAt;

}
