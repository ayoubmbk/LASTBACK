package com.example.phase.FeignClient;

import com.example.phase.Dto.TacheDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "tache", url = "http://localhost:9096") // Remplacez l'URL par celle du microservice demo


public interface TaskFeignClient {
    @GetMapping("/taches/byPhase/{phaseId}")
    List<TacheDto> getTasksByPhaseId(@PathVariable Long phaseId);
    @GetMapping("/taches/{phaseId}/progress")
    public double calculatePhaseProgress(@PathVariable Long phaseId);
}
