package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;

/**
 * Interface for compensation based services
 */
public interface CompensationService {
    /**
     * Create a {@link Compensation} for a given employeeId
     *
     * @param employeeId
     * @param compensation
     */
    Compensation create(String employeeId, Compensation compensation);

    /**
     * Get the {@link Compensation} for a given employeeId
     *
     * @param employeeId
     */
    Compensation read(String employeeId);
}
