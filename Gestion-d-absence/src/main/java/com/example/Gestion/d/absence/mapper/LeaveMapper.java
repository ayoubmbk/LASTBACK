package com.example.Gestion.d.absence.mapper;


import com.example.Gestion.d.absence.Dto.LeaveDTO;
import com.example.Gestion.d.absence.Entities.Leave;

public class LeaveMapper {

    public static LeaveDTO mapToDto(Leave employeeLeave){
        LeaveDTO employeeLeaveDTO = new LeaveDTO();
        employeeLeaveDTO.setLeaveId(employeeLeave.getLeaveId());
        employeeLeaveDTO.setEmployeeId(employeeLeave.getEmployeeId());
        if(employeeLeave.getLeaveType() != null) {
            employeeLeaveDTO.setLeaveTypeDTO(LeaveTypeMapper.mapToDto(employeeLeave.getLeaveType()));
        }
        employeeLeaveDTO.setLeaveReason(employeeLeave.getLeaveReason());
        employeeLeaveDTO.setFromDate(employeeLeave.getFromDate());
        employeeLeaveDTO.setToDate(employeeLeave.getToDate());
        employeeLeaveDTO.setDeniedReason(employeeLeave.getDeniedReason());
        employeeLeaveDTO.setStatus(String.valueOf(employeeLeave.getStatus()));
        employeeLeaveDTO.setCreatedAt(employeeLeave.getCreatedAt());
       employeeLeaveDTO.setReviewedBy(employeeLeave.getReviewedBy());
        return employeeLeaveDTO;
    }

    public static Leave mapToEntity(LeaveDTO employeeLeaveDTO){
        Leave employeeLeave = new Leave();
        employeeLeave.setLeaveId(employeeLeaveDTO.getLeaveId());
       employeeLeave.setEmployeeId(employeeLeaveDTO.getEmployeeId());
        if(employeeLeaveDTO.getLeaveTypeDTO() != null) {
            employeeLeave.setLeaveType(LeaveTypeMapper.mapToEntity(employeeLeaveDTO.getLeaveTypeDTO()));
        }
        employeeLeave.setLeaveReason(employeeLeaveDTO.getLeaveReason());
        employeeLeave.setFromDate(employeeLeaveDTO.getFromDate());
        employeeLeave.setToDate(employeeLeaveDTO.getToDate());
        employeeLeave.setDeniedReason(employeeLeaveDTO.getDeniedReason());
        employeeLeave.setStatus(StatusMapper.mapLeaveStatus(employeeLeaveDTO.getStatus()));
        employeeLeave.setCreatedAt(employeeLeaveDTO.getCreatedAt());
       employeeLeave.setReviewedBy(employeeLeaveDTO.getReviewedBy());
        return employeeLeave;
    }
}
