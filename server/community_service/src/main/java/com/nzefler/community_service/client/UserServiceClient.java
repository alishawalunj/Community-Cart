package com.nzefler.community_service.client;

import com.nzefler.community_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="user_management", url="https://localhost:8080")
public interface UserServiceClient {

    @GetMapping("/api/users")
    List<UserDTO> getUserByCommunityId(@RequestParam("communityId") Long communityId);
}
