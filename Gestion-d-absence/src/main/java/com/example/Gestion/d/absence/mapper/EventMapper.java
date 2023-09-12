package com.example.Gestion.d.absence.mapper;

import com.example.Gestion.d.absence.Dto.EventDTO;
import com.example.Gestion.d.absence.Entities.Event;
import com.example.Gestion.d.absence.Entities.Leave;

public class EventMapper {

    public static EventDTO mapLeaveToEventDto(Leave leave) {
        EventDTO eventDTO = new EventDTO();
        String employeeID = leave.getEmployeeId();
        eventDTO.setEventType("leave");
        eventDTO.setEventId(leave.getLeaveId());
        eventDTO.setStartDate(leave.getFromDate());
        eventDTO.setEndDate(leave.getToDate());
        eventDTO.setCreatedAt(leave.getCreatedAt());
        return eventDTO;
    }

    public static EventDTO mapEventToEventDto(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setEventId(event.getId());
        eventDTO.setTitle(event.getTitle());
        eventDTO.setEventType(event.getEventType());
        eventDTO.setStartDate(event.getStartDate());
        eventDTO.setEndDate(event.getEndDate());
        eventDTO.setCreatedAt(event.getCreatedAt());
        return eventDTO;
    }

    public static Event mapEventToEntity(EventDTO eventDTO){
        Event event = new Event();
        event.setId(eventDTO.getEventId());
        event.setTitle(eventDTO.getTitle());
        event.setStartDate(eventDTO.getStartDate());
        event.setEndDate(eventDTO.getEndDate());
        event.setEventType(eventDTO.getEventType());
        event.setCreatedAt(eventDTO.getCreatedAt());
        return event;
    }
}
