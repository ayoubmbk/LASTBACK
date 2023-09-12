package com.example.tache.FeignClient;

import com.example.tache.Dto.PhaseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "phase", url = "http://localhost:9097") // Remplacez l'URL par celle du microservice demo

public interface PhaseFeignClient

{
    @GetMapping("/phases/{id}")
    public PhaseDto getPhaseById(@PathVariable Long id);
}
