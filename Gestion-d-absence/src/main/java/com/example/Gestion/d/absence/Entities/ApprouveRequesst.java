package com.example.Gestion.d.absence.Entities;

import com.example.Gestion.d.absence.Dto.LeaveDTO;
import lombok.Data;

import java.util.List;

@Data
public class ApprouveRequesst {
    public LeaveDTO leaveDTO;
    public String employeeId;
    public List<String> roles; // Change 'role' to List<String>

}
