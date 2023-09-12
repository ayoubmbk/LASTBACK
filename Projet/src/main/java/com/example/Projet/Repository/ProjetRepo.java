package com.example.Projet.Repository;

import com.example.Projet.Entities.Projet;
import com.example.Projet.Entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetRepo extends JpaRepository<Projet,Long> {
    List<Projet> findByUtilisateurs_Id(String userId);
    List<Projet> findByCreatedBy(Utilisateur createdBy);





}
