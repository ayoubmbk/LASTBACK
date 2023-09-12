package com.example.Gestion.d.absence.Dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LeaveDTO {

    private Long leaveId;

    private String employeeId;

    private LeaveTypeDTO leaveTypeDTO;

    private String leaveReason;

    private LocalDate fromDate;

    private LocalDate toDate;

    private String deniedReason;

    private String status;

    private LocalDateTime createdAt;

    private String reviewedBy;


}
