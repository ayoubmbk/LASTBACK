package com.example.phase.FeignClient;

import com.example.phase.Dto.UtilisateurDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user", url = "http://localhost:9095") // Remplacez l'URL par celle du microservice demo


public interface UserFeignClient {
    @PostMapping("/users")
    UtilisateurDto addUser(@RequestBody UtilisateurDto user);

    @GetMapping("/users")
    List<UtilisateurDto> getAllUsers();

    @GetMapping("/users/userById/{userId}")
    UtilisateurDto getUserById(@PathVariable("userId") String userId);

    @PutMapping("/users/{userId}/conges/{dureeEnJours}")
    void updateCongesUtilises(@PathVariable("userId") String userId, @PathVariable("dureeEnJours") Integer dureeEnJours);

    @PutMapping("/users/{userId}/solde-actuel/{dureeEnJours}")
    void updateSoldeActuelCongee(@PathVariable("userId") String userId, @PathVariable("dureeEnJours") Integer dureeEnJours);

    @Scheduled(cron = "0 0 0 1 * ?")
    @PostMapping("/users/update-conges-mensuels")
    void updateCongesMensuels();

    @PutMapping("/users/{userId}/initialize-solde-actuel")
    void initializeSoldeActuelConges(@PathVariable("userId") String userId);

}
