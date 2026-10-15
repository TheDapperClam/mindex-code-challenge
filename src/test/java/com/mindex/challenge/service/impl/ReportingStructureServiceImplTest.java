package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * {@link ReportingStructureServiceImpl} integration tests
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportingStructureServiceImplTest {
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private ReportingStructureService reportingStructureService;

	@Test
	public void testRead_CyclicReport() {
		String firstEmployeeId = UUID.randomUUID().toString();
		String secondEmployeeId = UUID.randomUUID().toString();

		Employee firstEmployee = new Employee();
		firstEmployee.setEmployeeId(firstEmployeeId);
		Employee secondEmployee = new Employee();
		secondEmployee.setEmployeeId(secondEmployeeId);

		Employee secondEmployeeReport = new Employee();
		secondEmployeeReport.setEmployeeId(secondEmployeeId);
		firstEmployee.setDirectReports(new ArrayList<>(List.of(secondEmployeeReport)));

		Employee firstEmployeeReport = new Employee();
		firstEmployeeReport.setEmployeeId(firstEmployeeId);
		secondEmployee.setDirectReports(new ArrayList<>(List.of(firstEmployeeReport)));

		employeeRepository.insert(firstEmployee);
		employeeRepository.insert(secondEmployee);

		assertNumberOfReports(firstEmployeeId, 1);
	}

	@Test
	public void testRead_HasDirectReports() {
		String employeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";
		int expected = 4;

		assertNumberOfReports(employeeId, expected);
	}

	@Test
	public void testRead_NoDirectReports() {
		String employeeId = "c0c2293d-16bd-4603-8e08-638a9d18b22c";
		int expected = 0;

		assertNumberOfReports(employeeId, expected);
	}

	/**
	 * Get an employee by id and assert that their number of direct reports is equal to expected
	 *
	 * @param employeeId
	 * @param expected
	 */
	private void assertNumberOfReports(String employeeId, int expected) {
		ReportingStructure reportingStructure = reportingStructureService.read(employeeId);
		Assert.assertEquals(employeeId, reportingStructure.getEmployee().getEmployeeId());
		Assert.assertEquals(expected, reportingStructure.getNumberOfReports());
	}
}
