package com.example.tache.entitie;

import com.example.tache.enumeration.Niveau;
import com.example.tache.enumeration.Situation;
import com.example.tache.enumeration.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long idTache;
    @Enumerated(EnumType.STRING)
    private Status status=Status.unassigned;
    @Enumerated(EnumType.ORDINAL)
    private Niveau nivTache;
    @Enumerated(EnumType.STRING)
    private Situation situation;
    private String nomTache;

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    @Temporal(TemporalType.TIMESTAMP)
    private  Date dateEchanche;

    public Long getIdTache() {
        return idTache;
    }

    public void setIdTache(Long idTache) {
        this.idTache = idTache;
    }

    public Date getDateEchanche() {
        return dateEchanche;
    }

    public void setDateEchanche(Date dateEchanche) {
        this.dateEchanche = dateEchanche;
    }

    private String  userId;

    private String createdBy;

    private Long phaseId;

    private Long projetId;

}