package com.mindex.challenge.service;

import com.mindex.challenge.data.ReportingStructure;

/**
 * Interface for report structure based services
 */
public interface ReportingStructureService {
    /**
     * Get the {@link ReportingStructure} for a given employee Id
     *
     * @param employeeId
     */
    ReportingStructure read(String employeeId);
}
