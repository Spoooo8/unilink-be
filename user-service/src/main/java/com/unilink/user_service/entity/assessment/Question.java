package com.unilink.user_service.entity.assessment;

import com.unilink.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "quiz" ,name = "tbl_question")
public class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private Integer rightAnswer;

    @ManyToOne
    @JoinColumn(name = "level_id", referencedColumnName = "id")
    private Level level;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;
}