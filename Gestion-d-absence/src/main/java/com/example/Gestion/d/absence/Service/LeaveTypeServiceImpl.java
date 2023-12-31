package com.example.Gestion.d.absence.Service;

import com.example.Gestion.d.absence.Dto.LeaveTypeDTO;
import com.example.Gestion.d.absence.Entities.LeaveType;
import com.example.Gestion.d.absence.exception.DataNotFoundException;
import com.example.Gestion.d.absence.mapper.LeaveTypeMapper;
import com.example.Gestion.d.absence.mapper.StatusMapper;
import com.example.Gestion.d.absence.repository.LeaveTypeRepository;
import com.example.Gestion.d.absence.utils.ExceptionConstants;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveTypeServiceImpl implements LeaveTypeService {

    private final LeaveTypeRepository leaveTypeRepository;

    public LeaveTypeServiceImpl(final LeaveTypeRepository leaveTypeRepository){
        this.leaveTypeRepository = leaveTypeRepository;
    }

    /**
     * Get all LeaveTypes Record
     * @return List of LeaveType
     */
    @Override
    public List<LeaveTypeDTO> getAllLeaveTypes() {

        return leaveTypeRepository.findAll()
                .stream()
                .map((leaveType) -> LeaveTypeMapper.mapToDto(leaveType))
                .collect(Collectors.toList());
    }

    /**
     * Get single LeaveType Record
     * @param id
     * @return If present LeaveType else throws Exception
     */
    @Override
    public LeaveTypeDTO getLeaveTypeById(Long id) {

        LeaveType leaveType = leaveTypeRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException(ExceptionConstants.LEAVE_TYPE_RECORD_NOT_FOUND));
        return LeaveTypeMapper.mapToDto(leaveType);
    }

    /**
     * Create New LeaveType
     * @param leaveTypeDTO
     * @return saved LeaveType
     */
    @Override
    public LeaveTypeDTO createLeaveType(LeaveTypeDTO leaveTypeDTO) {

        if (leaveTypeDTO.getTypeName() == null || leaveTypeRepository.findByTypeName(leaveTypeDTO.getTypeName()) != null){
            throw new DataNotFoundException(ExceptionConstants.LEAVE_TYPE_NAME_NOT_VALID);
        }
        leaveTypeDTO.setStatus(leaveTypeDTO.getStatus());
        LeaveType leaveType = leaveTypeRepository.save(LeaveTypeMapper.mapToEntity(leaveTypeDTO));
        return LeaveTypeMapper.mapToDto(leaveType);
    }

    /**
     * Update LeaveType
     * LeaveType must be present in database else throws Exception
     * @param leaveTypeDTO
     * @return updated LeaveType
     */
    @Override
    public LeaveTypeDTO updateLeaveType(LeaveTypeDTO leaveTypeDTO) {

        LeaveType returnedLeaveType = leaveTypeRepository.findById(leaveTypeDTO.getLeaveTypeId())
                .orElseThrow(()-> new DataNotFoundException(ExceptionConstants.LEAVE_TYPE_RECORD_NOT_FOUND));

        returnedLeaveType.setTypeName(leaveTypeDTO.getTypeName());
        returnedLeaveType.setStatus(StatusMapper.mapLeaveTypeStatus(leaveTypeDTO.getStatus()));
        return LeaveTypeMapper.mapToDto(leaveTypeRepository.save(returnedLeaveType));
    }

    @Override
    public List<LeaveTypeDTO> searchOnLeaveType(String q) {
        return leaveTypeRepository.findAllByTypeNameContaining(q)
                .stream()
                .map((leaveType -> LeaveTypeMapper.mapToDto(leaveType)))
                .collect(Collectors.toList());
    }
}
