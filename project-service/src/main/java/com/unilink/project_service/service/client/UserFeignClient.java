package com.unilink.project_service.service.client;

import com.unilink.project_service.dto.UserResponse;
import com.unilink.project_service.dto.project.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name ="user-service", configuration = FeignClientConfig.class)
public interface UserFeignClient {

    @GetMapping(value ="/users/{userId}",consumes = "application/json")
    public UserResponse getUserById(@PathVariable("userId") Long userId);

    @GetMapping(value ="/profile/{userId}", consumes = "application/json")
    public UserProfileResponse getUserProfileById(@PathVariable("userId") Long userId);

}
