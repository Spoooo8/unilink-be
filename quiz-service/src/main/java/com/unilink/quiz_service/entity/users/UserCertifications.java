package com.unilink.quiz_service.entity.users;


import com.unilink.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_user_certifications", schema = "users")
public class UserCertifications extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String certificationName;
    private String certificationIssuer;
    private LocalDateTime certificationDate;
    private LocalDateTime expiryDate;
    private String certificationUrl;
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;



}