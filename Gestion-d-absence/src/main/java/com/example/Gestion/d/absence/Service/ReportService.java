package com.example.Gestion.d.absence.Service;



import com.example.Gestion.d.absence.Dto.LeaveReportDTO;

import java.util.List;

public interface ReportService {

    List<LeaveReportDTO> retrieveLeaveReports();
}
