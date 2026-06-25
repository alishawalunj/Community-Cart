package com.nzefler.auth.client;

import com.nzefler.auth.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${services.user-service.url}")
public interface UserClient {

    @GetMapping("users/email/{email}")
    UserResponseDTO findByEmail(@PathVariable("email") String email);
}
