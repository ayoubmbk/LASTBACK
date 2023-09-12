package com.example.Gestion.d.absence.Entities;

import com.example.Gestion.d.absence.Dto.LeaveDTO;
import lombok.Data;

@Data
public class LeaveRequest {
    public LeaveDTO leaveDTO;
    public String employeeId;
}
