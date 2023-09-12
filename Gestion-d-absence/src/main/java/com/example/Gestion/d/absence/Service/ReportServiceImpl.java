package com.example.Gestion.d.absence.Service;

import com.example.Gestion.d.absence.Dto.LeaveReportDTO;
import com.example.Gestion.d.absence.repository.LeaveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final LeaveRepository leaveRepository;

    public ReportServiceImpl(final LeaveRepository leaveRepository) {
        this.leaveRepository = leaveRepository;
    }



    @Override
    public List<LeaveReportDTO> retrieveLeaveReports() {
        return leaveRepository.generateLeaveReport();
    }


}
