package com.unilink.quiz_service.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OngoingTitlesDTO {
    private Long id;
    private String title;

    public OngoingTitlesDTO(String title) {
        this.title = title;
    }
}