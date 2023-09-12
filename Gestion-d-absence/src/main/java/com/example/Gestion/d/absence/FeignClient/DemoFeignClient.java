package com.example.Gestion.d.absence.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user", url = "http://localhost:9095") // Remplacez l'URL par celle du microservice demo
public interface DemoFeignClient {

    @PostMapping("/users/updateSoldeActuelConges/{userId}/{dureeEnJours}")
    ResponseEntity<String> updateSoldeActuelConges(
            @PathVariable String userId,
            @PathVariable Integer dureeEnJours);


}
