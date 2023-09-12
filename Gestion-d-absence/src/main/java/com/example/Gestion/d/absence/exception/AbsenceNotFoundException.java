package com.example.Gestion.d.absence.exception;

public class AbsenceNotFoundException extends RuntimeException {
    public AbsenceNotFoundException(Long id) {
        super("Absence not found with id: " + id);
    }
}
