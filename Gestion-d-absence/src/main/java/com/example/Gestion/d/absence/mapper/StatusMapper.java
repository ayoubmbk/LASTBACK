package com.example.Gestion.d.absence.mapper;

import com.example.Gestion.d.absence.enumera.EmployeeStatus;
import com.example.Gestion.d.absence.enumera.LeaveStatus;
import com.example.Gestion.d.absence.enumera.LeaveTypeStatus;


public class StatusMapper {

    public static EmployeeStatus mapEmployeeStatus(String status) {

        if(status == null){
            return EmployeeStatus.INACTIVE;
        }

        if (status.equals(String.valueOf(EmployeeStatus.ACTIVE))) {
            return EmployeeStatus.ACTIVE;
        } else {
            return EmployeeStatus.INACTIVE;
        }
    }

    public static LeaveStatus mapLeaveStatus(String status) {

        if(status == null){
            return LeaveStatus.PENDING;
        }

        if (status.equals(String.valueOf(LeaveStatus.APPROVED))) {
            return LeaveStatus.APPROVED;
        } else if (status.equals(String.valueOf(LeaveStatus.DENIED))) {
            return LeaveStatus.DENIED;
        } else {
            return LeaveStatus.PENDING;
        }
    }

    public static LeaveTypeStatus mapLeaveTypeStatus(String status) {

        if(status == null){
            return LeaveTypeStatus.INACTIVE;
        }

        if (status.equals(String.valueOf(LeaveTypeStatus.ACTIVE))) {
            return LeaveTypeStatus.ACTIVE;
        } else {
            return LeaveTypeStatus.INACTIVE;
        }
    }

}
