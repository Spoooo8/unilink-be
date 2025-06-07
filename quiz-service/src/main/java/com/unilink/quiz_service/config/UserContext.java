package com.unilink.quiz_service.config;

import org.springframework.stereotype.Component;

@Component
public class UserContext {

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
