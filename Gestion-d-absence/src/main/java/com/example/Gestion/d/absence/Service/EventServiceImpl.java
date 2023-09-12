package com.example.Gestion.d.absence.Service;

import com.example.Gestion.d.absence.Dto.EventDTO;
import com.example.Gestion.d.absence.Entities.Event;
import com.example.Gestion.d.absence.exception.DataNotFoundException;
import com.example.Gestion.d.absence.mapper.EventMapper;
import com.example.Gestion.d.absence.repository.EventRepository;
import com.example.Gestion.d.absence.repository.LeaveRepository;
import com.example.Gestion.d.absence.utils.DateUtil;
import com.example.Gestion.d.absence.utils.ExceptionConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements  EventService{

    private final EventRepository eventRepository;

    private final LeaveRepository leaveRepository;

    public EventServiceImpl(final EventRepository eventRepository, final LeaveRepository leaveRepository){
        this.eventRepository = eventRepository;
        this.leaveRepository = leaveRepository;
    }

    @Override
    public Page<EventDTO> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable)
                .map((event -> EventMapper.mapEventToEventDto(event)));
    }

    @Override
    public EventDTO getEventsById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException(ExceptionConstants.EVENT_RECORD_NOT_FOUND));
        return EventMapper.mapEventToEventDto(event);
    }

    @Override
    public EventDTO createEvent(EventDTO eventDTO) {
        Event event =  eventRepository.save(EventMapper.mapEventToEntity(eventDTO));
        return EventMapper.mapEventToEventDto(event);
    }

    @Override
    public EventDTO updateEvent(EventDTO eventDTO) {
        Event event = eventRepository.findById(eventDTO.getEventId())
                .orElseThrow(()-> new DataNotFoundException(ExceptionConstants.EVENT_RECORD_NOT_FOUND));
        event.setTitle(eventDTO.getTitle());
        event.setStartDate(eventDTO.getStartDate());
        event.setEndDate(eventDTO.getEndDate());
        return EventMapper.mapEventToEventDto(eventRepository.save(event));
    }

    @Override
    public List<EventDTO> retrieveLeaveAndEventsByDate(String dateFrom, String dateTo) {
        List<EventDTO> eventDTOList = eventRepository.findEventByDate(DateUtil.convertToDate(dateFrom), DateUtil.convertToDate(dateTo))
                .stream()
                .map( event -> EventMapper.mapEventToEventDto(event))
                .collect(Collectors.toList());
        List<EventDTO> eventDTOList1 = leaveRepository.findLeaveByDate(DateUtil.convertToDate(dateFrom), DateUtil.convertToDate(dateTo))
                .stream()
                .map(leave -> EventMapper.mapLeaveToEventDto(leave))
                .collect(Collectors.toList());
        eventDTOList.addAll(eventDTOList1);
        return eventDTOList;
    }
}
