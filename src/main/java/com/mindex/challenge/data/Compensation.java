package com.mindex.challenge.data;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.Date;

public class Compensation {
    @Id
    public String compensationId;
    public Date effectiveDate;
    public BigDecimal salary;

    public String getCompensationId() {
        return compensationId;
    }

    public void setCompensationId(String compensationId) {
        this.compensationId = compensationId;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
