package com.unilink.chat_service.service.client;


import com.unilink.chat_service.dto.CollaboratorsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name ="project-service", configuration = FeignClientConfig.class)
public interface ProjectFeignClient {
    @GetMapping(value = "/{projectId}/members",consumes = "application/json")
    public List<CollaboratorsDTO> getCollaborators(@PathVariable Long projectId);

}
