package com.compass.ms_order.web.controller.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-services", url = "http://ms-client:8082/api/v1/client")
public interface UserClient {


    @GetMapping("/email/{email}")
    ResponseEntity<String> consultEmailUser(@PathVariable String email);
}
