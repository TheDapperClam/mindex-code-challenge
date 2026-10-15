package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * {@link CompensationService} web service controller
 */
@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    private final CompensationService compensationService;

    @Autowired
    public CompensationController(CompensationService compensationService) {
        this.compensationService = compensationService;
    }

    @PostMapping("/compensation/{employeeId}")
    public Compensation create(@PathVariable String employeeId, @RequestBody Compensation compensation) {
        LOG.debug("Received create request with employeeId [{}] and compensation [{}]", employeeId,
                compensation);

        return compensationService.create(employeeId, compensation);
    }

    @GetMapping("/compensation/{employeeId}")
    public Compensation read(@PathVariable String employeeId) {
        LOG.debug("Received read request with employeeId [{}]", employeeId);

        return compensationService.read(employeeId);
    }
}
