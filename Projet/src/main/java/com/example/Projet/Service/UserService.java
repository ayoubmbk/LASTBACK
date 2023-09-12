package com.example.Projet.Service;



import com.example.Projet.Entities.Utilisateur;
import com.example.Projet.Repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    public Utilisateur addUser(Utilisateur user) {
        return userRepo.save(user);
    }
    public List<Utilisateur> getAllUsers() {
        return userRepo.findAll();

    }
    public Utilisateur getUserById(String userId) {
        Utilisateur user= userRepo.findById(userId).orElse(null);
        return user ;

    }

    public void updateCongesUtilises(String userId, Integer dureeEnJours) {
        Utilisateur user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException());

        user.setCongeUtilises(user.getCongeUtilises() + dureeEnJours);

        userRepo.save(user);
    }
    public void updateSoldeActuelCongee(String userId, Integer dureeEnJours) {
        Utilisateur utilisateur = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException());

        // Mettre à jour le solde actuel de congés de l'utilisateur
        double soldeActuelConges = utilisateur.getSoldeActuelConges();
        utilisateur.setSoldeActuelConges(soldeActuelConges - dureeEnJours);

        userRepo.save(utilisateur);
    }
    @Scheduled(cron = "0 0 0 1 * ?")
    public void updateCongesMensuels() {
        List<Utilisateur> users = userRepo.findAll();

        for (Utilisateur user : users) {
            user.setSoldeActuelConges(user.getSoldeActuelConges() + 1.4);
            userRepo.save(user);

            System.out.println("Solde actuel de congés mis à jour pour l'utilisateur : " + user.getId());
        }
    }
    public void initializeSoldeActuelConges(String userId) {
        Utilisateur user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException());

        user.setSoldeActuelConges(0.0); // Initialise le solde à 0

        userRepo.save(user);
    }

}
