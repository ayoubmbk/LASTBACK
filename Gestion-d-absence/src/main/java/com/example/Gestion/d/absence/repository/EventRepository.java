package com.example.Gestion.d.absence.repository;

import com.example.Gestion.d.absence.Entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Override
    Page<Event> findAll(Pageable pageable);

    List<Event> findEventByDate(LocalDate date1, LocalDate date2);
}
