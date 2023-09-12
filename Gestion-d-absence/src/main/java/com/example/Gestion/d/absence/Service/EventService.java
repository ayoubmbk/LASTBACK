package com.example.Gestion.d.absence.Service;

import com.example.Gestion.d.absence.Dto.EventDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface EventService {

    Page<EventDTO> getAllEvents(Pageable pageable);

    EventDTO getEventsById(Long id);

    EventDTO createEvent(EventDTO eventDTO);

    EventDTO updateEvent(EventDTO eventDTO);

    List<EventDTO> retrieveLeaveAndEventsByDate(String dateFrom, String dateTo);
}
