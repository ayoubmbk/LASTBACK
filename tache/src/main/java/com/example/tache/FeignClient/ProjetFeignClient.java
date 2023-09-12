package com.example.tache.FeignClient;

import com.example.tache.Dto.ProjetDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "projet", url = "http://localhost:9095") // Remplacez l'URL par celle du microservice demo
public interface ProjetFeignClient {

    @GetMapping("/projects")
    List<ProjetDto> getAllProjets();

    @GetMapping("/projects/{id}")
    ProjetDto getProjetById(@PathVariable Long id);

    @PostMapping("/projects")
    ProjetDto createProjet(@RequestBody ProjetDto projet, @RequestParam("createdBy") String createdBy);

    @PutMapping("/projects/{id}")
    ProjetDto updateProjet(@PathVariable Long id, @RequestBody ProjetDto project);

    @DeleteMapping("/projects/{id}")
    void deleteProjet(@PathVariable Long id);

    // Define other methods for interacting with the Projet microservice here
}
