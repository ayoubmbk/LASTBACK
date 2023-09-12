package com.example.Gestion.d.absence.Service;
import com.example.Gestion.d.absence.Dto.LeaveDTO;
import com.example.Gestion.d.absence.Dto.UtilisateurDto;
import com.example.Gestion.d.absence.Entities.ApprouveRequesst;
import com.example.Gestion.d.absence.Entities.Leave;
import com.example.Gestion.d.absence.Entities.LeaveRequest;
import com.example.Gestion.d.absence.FeignClient.UserFeignClient;
import com.example.Gestion.d.absence.exception.DataConflictException;
import com.example.Gestion.d.absence.exception.DataNotFoundException;
import com.example.Gestion.d.absence.exception.UnauthorizedRequest;
import com.example.Gestion.d.absence.mapper.LeaveMapper;
import com.example.Gestion.d.absence.mapper.StatusMapper;
import com.example.Gestion.d.absence.repository.LeaveRepository;
import com.example.Gestion.d.absence.utils.DateUtil;
import com.example.Gestion.d.absence.utils.ExceptionConstants;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveServiceImpl implements LeaveService {
    @Autowired
    UserFeignClient UserFeignClient;



    private final LeaveRepository employeeLeaveRepository;
    @Autowired
    UserFeignClient userFeignClient;

    public LeaveServiceImpl( final LeaveRepository employeeLeaveRepository){

        this.employeeLeaveRepository = employeeLeaveRepository;

    }

    /**
     * Get all Leave Record
     * @return List of Leave
     */
    @Override
    public Page<LeaveDTO> getAllEmployeeLeaves(Pageable pageable) {

        return employeeLeaveRepository.findAll(pageable)
                .map(employeeLeave -> LeaveMapper.mapToDto(employeeLeave));
    }

    /**
     * Get single Leave Record
     * @param id
     * @return If present Leave else throw Exception
     */
    @Override
    public LeaveDTO getEmployeeLeaveById(Long id) {

        Leave employeeLeave = employeeLeaveRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND));
        return LeaveMapper.mapToDto(employeeLeave);
    }



    /**
     * Create New Leave Request
     * @param
     * @return saved Leave
     */
    @Override
    public LeaveDTO createEmployeeLeave(LeaveRequest leaveRequest) {
        if(leaveRequest.getLeaveDTO().getLeaveTypeDTO() == null){
            throw new DataNotFoundException(ExceptionConstants.LEAVE_TYPE_RECORD_NOT_FOUND);
        }

    ;
        // Continuez avec le traitement du LeaveDTO
        UtilisateurDto userDto = new UtilisateurDto();
        userDto.setId(leaveRequest.getEmployeeId());
        System.out.println(leaveRequest.getEmployeeId()+"ayouboobiididid");
       leaveRequest.getLeaveDTO().setEmployeeId(leaveRequest.getEmployeeId());
        leaveRequest.getLeaveDTO().setStatus(leaveRequest.getLeaveDTO().getStatus());
        Leave employeeLeave = employeeLeaveRepository.save(LeaveMapper.mapToEntity(leaveRequest.getLeaveDTO()));
        return LeaveMapper.mapToDto(employeeLeave);
    }

    /**
     * Update Leave
     * Leave Record must be present in database else throws Exception
     * Leave status must be in pending else throws Exception
     * @param leaveDTO
     * @return updated Leave
     */
    @Override
    public LeaveDTO updateEmployeeLeave(LeaveDTO leaveDTO) {

        Leave returnedEmployeeLeave = employeeLeaveRepository.findById(leaveDTO.getLeaveId())
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND));

        // Leave status must be in pending
        if(returnedEmployeeLeave.getReviewedBy() != null){
            throw new DataConflictException(ExceptionConstants.EMPLOYEE_LEAVE_ACTION_ALREADY_TAKEN);
        }
        returnedEmployeeLeave.setFromDate(leaveDTO.getFromDate());
        returnedEmployeeLeave.setToDate(leaveDTO.getToDate());
        returnedEmployeeLeave.setLeaveReason(leaveDTO.getLeaveReason());
        return LeaveMapper.mapToDto(employeeLeaveRepository.save(returnedEmployeeLeave));
    }

    /**
     * Approved Leave request
     * Leave Record must be present in database else throws Exception
     *
     * @param
     * @return approved Leave
     */
    @Override
    public LeaveDTO approveEmployeeLeave(ApprouveRequesst approuveRequesst) {
        Leave returnedEmployeeLeave = employeeLeaveRepository.findById(approuveRequesst.getLeaveDTO().getLeaveId())
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND));


        String approverId = approuveRequesst.getEmployeeId();
        System.out.println(approverId + " monicaaaa");
        System.out.println(approuveRequesst.getRoles() + "manouuch");

        // Check if the current user is an admin (based on the 'role' parameter)
        boolean isAdmin = approuveRequesst.getRoles() != null && approuveRequesst.getRoles().contains("ADMIN");
        System.out.println(isAdmin + " aaaaaa");

        if (!isAdmin) {
            throw new UnauthorizedRequest(ExceptionConstants.YOU_CANT_REVIEW_THIS_REQUEST);
        }

        // Update the status, reviewedBy, and deniedReason
        returnedEmployeeLeave.setStatus(StatusMapper.mapLeaveStatus(approuveRequesst.getLeaveDTO().getStatus()));
        returnedEmployeeLeave.setReviewedBy(approverId);
        returnedEmployeeLeave.setDeniedReason(approuveRequesst.getLeaveDTO().getDeniedReason());

        // Save the updated leave request
        returnedEmployeeLeave = employeeLeaveRepository.save(returnedEmployeeLeave);

        // Map the updated entity to a DTO
        return LeaveMapper.mapToDto(returnedEmployeeLeave);
    }


    /**
     * Delete Leave request that is still pending
     * Leave Record must be present in database else throws Exception
     * Leave status must be in pending else throws Exception
     *
     * @param id
     * @return boolean value of Leave Deletion
     */
    @Override
    public LeaveDTO ChangeEmployeeLeaveStatus(Long id, String status) {

        Leave returnedEmployeeLeave = employeeLeaveRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND));

        // Leave status must be in pending
        if(returnedEmployeeLeave.getReviewedBy() != null){
            throw new DataConflictException(ExceptionConstants.EMPLOYEE_LEAVE_ACTION_ALREADY_TAKEN);
        }
        returnedEmployeeLeave.setStatus(StatusMapper.mapLeaveStatus(status));
        return LeaveMapper.mapToDto(employeeLeaveRepository.save(returnedEmployeeLeave));
    }

    @Override
    public List<LeaveDTO> retrieveEmployeeLeaveByDate(String date1, String date2) {
        return employeeLeaveRepository.findLeaveByDate(DateUtil.convertToDate(date1), DateUtil.convertToDate(date2))
                .stream()
                .map(leave -> LeaveMapper.mapToDto(leave))
                .collect(Collectors.toList());
    }
    public String getCurrentUserUsername() {
        System.out.println("sssssssssssss");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("aaaaaaaaaaa");
        if (authentication != null && authentication.getPrincipal() != null) {
            System.out.println("pppkkkkkksss");
            return authentication.getName();
        }

        return null;
    }

}
