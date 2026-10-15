package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Base implementation for our {@link ReportingStructureService}
 */
@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
    private final static Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    private final EmployeeService employeeService;

    @Autowired
    public ReportingStructureServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public ReportingStructure read(String employeeId) {
        LOG.debug("Reading reporting structure for employee id [{}]", employeeId);

        ReportingStructure report = new ReportingStructure();
        Employee employee = employeeService.read(employeeId);
        int numberOfReports = getNumberOfReports(employee, new HashSet<>());

        report.setEmployee(employee);
        report.setNumberOfReports(numberOfReports);
        return report;
    }

    /**
     * Get the number of direct reports for a given employee
     *
     * @param employee
     */
    private int getNumberOfReports(Employee employee, Set<String> checkedEmployees) {
        // Avoid cyclic reporting
        if (!checkedEmployees.add(employee.getEmployeeId())) {
            return 0;
        }

        List<Employee> directReports = employee.getDirectReports();
        if (directReports == null) {
            return 0;
        }

        int numberOfReports = 0;
        for (Employee directReport : directReports) {
            if (checkedEmployees.contains(directReport.getEmployeeId())) {
                continue;
            }

            numberOfReports++;
            // Recursively get number of reports for every direct report
            Employee directReportEmployee = employeeService.read(directReport.getEmployeeId());
            numberOfReports += getNumberOfReports(directReportEmployee, checkedEmployees);
        }
        return numberOfReports;
    }
}
