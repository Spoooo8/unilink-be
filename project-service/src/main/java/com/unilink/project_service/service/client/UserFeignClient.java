package com.unilink.project_service.service.client;

import com.unilink.project_service.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient("user-service")
public interface UserFeignClient {

    @GetMapping(value ="/users/{userId}",consumes = "application/json")
    public UserResponse getUserById(@PathVariable("userId") Long userId);



}
