package com.example.Gestion.d.absence.Entities;

import com.example.Gestion.d.absence.Dto.LeaveReportDTO;
import com.example.Gestion.d.absence.enumera.LeaveStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
@Data
@Entity
@Table(name = "leave_request")

@NamedQuery(name = "Leave.findLeaveByDate",
        query = "SELECT l FROM Leave l WHERE ((l.fromDate BETWEEN ?1 AND ?2) OR (l.toDate BETWEEN ?1 AND ?2)) " +
                "AND l.status='APPROVED' ")

@NamedNativeQuery(
        name = "Leave.generateLeaveReport",
        query =
                "SELECT count(lr.leave_id) AS count, lt.type_name AS leaveType, EXTRACT(MONTH FROM lr.from_date) AS month, lr.status AS status " +
                        "FROM leave_request lr " +
                        "INNER JOIN user_entity e ON e.id = lr.employee_id " +
                        "INNER JOIN leave_type lt ON lt.leave_type_id = lr.leave_type " +
                        "WHERE lr.from_date >= DATE_TRUNC('YEAR', NOW() - INTERVAL '1 YEAR') " +
                        "OR lr.to_date >= DATE_TRUNC('YEAR', NOW() - INTERVAL '1 YEAR') " +
                        "GROUP BY lt.type_name, EXTRACT(MONTH FROM lr.from_date), lr.status " +
                        "ORDER BY EXTRACT(MONTH FROM lr.from_date)",
        resultSetMapping = "LeaveReportDTO"
)

@SqlResultSetMapping(
        name = "LeaveReportDTO",
        classes = @ConstructorResult(
                targetClass = LeaveReportDTO.class,
                columns = {
                        @ColumnResult(name = "count", type = Integer.class),
                        @ColumnResult(name = "leaveType", type = String.class),
                        @ColumnResult(name = "month", type = Integer.class),
                        @ColumnResult(name = "status", type = String.class)
                }
        )
)
public class Leave {

    @Id
    @Column(name = "leave_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaveId;


    @JoinColumn(name = "employee_id", nullable = false)
    private String employeeId;

    @ManyToOne
    @JoinColumn(name = "leave_type", nullable = false)
    private LeaveType leaveType;

    @Column(name = "leave_reason", columnDefinition = "TEXT", nullable = false)
    private String leaveReason;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

//    @Column(name = "approved")
//    private boolean approved;

    @Column(name = "denied_reason", columnDefinition = "TEXT")
    private String deniedReason;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;


    @JoinColumn(name = "reviewed_by")
    private String reviewedBy;
}
