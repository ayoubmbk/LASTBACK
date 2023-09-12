package com.example.Projet.Repository;

import com.example.Projet.Entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Utilisateur,Long> {
    Optional<Utilisateur> findById(String userId);


}