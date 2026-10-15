package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompensationServiceImplTest {
    @Autowired
    private CompensationService compensationService;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testCreateAndRead() {
        Employee employee = new Employee();
        employee.setFirstName("Service");
        employee.setLastName("Integration Test");
        employee = employeeService.create(employee);

        Date effectiveDate = Date.from(Instant.parse("2024-01-15T00:00:00Z"));
        Compensation request = new Compensation();
        request.setSalary(new BigDecimal("98765.43"));
        request.setEffectiveDate(effectiveDate);

        Compensation created = compensationService.create(employee.getEmployeeId(), request);

        assertNotNull(created.getCompensationId());
        assertEquals(0, request.getSalary().compareTo(created.getSalary()));
        assertEquals(effectiveDate, created.getEffectiveDate());
        assertEquals(created.getCompensationId(),
                employeeService.read(employee.getEmployeeId()).getCompensationId());

        Compensation read = compensationService.read(employee.getEmployeeId());
        assertEquals(created.getCompensationId(), read.getCompensationId());
        assertEquals(0, created.getSalary().compareTo(read.getSalary()));
        assertEquals(effectiveDate, read.getEffectiveDate());
    }

    @Test
    public void testRead_EmployeeHasNoCompensation() {
        Employee employee = new Employee();
        employee.setFirstName("No Compensation");
        employee = employeeService.create(employee);

        try {
            compensationService.read(employee.getEmployeeId());
            fail("Expected a RuntimeException for an employee without compensation");
        } catch (RuntimeException e) {
            assertEquals("Invalid compensationId: null", e.getMessage());
        }
    }
}
