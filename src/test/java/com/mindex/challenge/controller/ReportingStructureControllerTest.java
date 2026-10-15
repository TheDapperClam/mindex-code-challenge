package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * {@link ReportingStructureController} integration tests
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureControllerTest {
    private String reportStructureEmployeeIdUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        reportStructureEmployeeIdUrl = String.format("http://localhost:%d/reportingstructure/{employeeId}", port);
    }

    @Test
    public void testRead_Success() {
        String employeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";
        int expected = 4;
        ReportingStructure reportingStructure = restTemplate.getForEntity(reportStructureEmployeeIdUrl,
                ReportingStructure.class, employeeId).getBody();

        Assert.assertNotNull(reportingStructure);
        Assert.assertEquals(employeeId, reportingStructure.getEmployee().getEmployeeId());
        Assert.assertEquals(expected, reportingStructure.getNumberOfReports());
    }
}
