package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * {@link ReportingStructureController} integration tests
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationControllerTest {
    private String compensationUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeService employeeService;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation/{employeeId}";
    }

    @Test
    public void testCreateAndRead() {
        Employee employee = new Employee();
        employee.setFirstName("Controller");
        employee.setLastName("Integration Test");
        employee = employeeService.create(employee);

        Date effectiveDate = Date.from(Instant.parse("2024-01-15T00:00:00Z"));
        Compensation request = new Compensation();
        request.setSalary(new BigDecimal("123456.78"));
        request.setEffectiveDate(effectiveDate);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Compensation> entity = new HttpEntity<>(request, headers);
        Compensation created = restTemplate.postForEntity(compensationUrl, entity, Compensation.class,
                employee.getEmployeeId()).getBody();

        assertNotNull(created);
        assertNotNull(created.getCompensationId());
        assertEquals(0, request.getSalary().compareTo(created.getSalary()));
        assertEquals(effectiveDate, created.getEffectiveDate());

        Compensation read = restTemplate.getForEntity(compensationUrl, Compensation.class,
                employee.getEmployeeId()).getBody();
        assertNotNull(read);
        assertEquals(created.getCompensationId(), read.getCompensationId());
        assertEquals(0, created.getSalary().compareTo(read.getSalary()));
        assertEquals(effectiveDate, read.getEffectiveDate());
    }
}
