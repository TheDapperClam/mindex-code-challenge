package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Base implementation for our {@link CompensationService}
 */
@Service
public class CompensationServiceImpl implements CompensationService {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    private final CompensationRepository compensationRepository;
    private final EmployeeService employeeService;

    @Autowired
    public CompensationServiceImpl(CompensationRepository compensationRepository,
                                   EmployeeService employeeService) {
        this.compensationRepository = compensationRepository;
        this.employeeService = employeeService;
    }

    @Override
    public Compensation create(String employeeId, Compensation compensation) {
        LOG.debug("Received create request for employee id [{}], with compensation [{}]", employeeId,
                compensation);

        Employee employee = employeeService.read(employeeId);
        compensation.setCompensationId(UUID.randomUUID().toString());

        compensationRepository.insert(compensation);
        employee.setCompensationId(compensation.getCompensationId());
        employeeService.update(employee);

        return compensation;
    }

    @Override
    public Compensation read(String employeeId) {
        LOG.debug("Received read request for employee id [{}]", employeeId);

        Employee employee = employeeService.read(employeeId);
        Compensation compensation = compensationRepository.findByCompensationId(employee.getCompensationId());

        if (compensation == null) {
            throw new RuntimeException("Invalid compensationId: " + employee.getCompensationId());
        }

        return compensation;
    }
}
