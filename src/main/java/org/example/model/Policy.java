
package org.example.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Policy {

    private int policyId;
    private String policyNumber;
    private int customerId;
    private int typeId;
    private BigDecimal coverageAmount;
    private BigDecimal premiumAmount;
    private Date startDate;
    private Date endDate;
    private String policyStatus;
    private Timestamp createdAt;

    public Policy() {
    }

    public Policy(String policyNumber, int customerId, int typeId,
                  BigDecimal coverageAmount, BigDecimal premiumAmount,
                  Date startDate, Date endDate, String policyStatus) {

        this.policyNumber = policyNumber;
        this.customerId = customerId;
        this.typeId = typeId;
        this.coverageAmount = coverageAmount;
        this.premiumAmount = premiumAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.policyStatus = policyStatus;
    }

    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public BigDecimal getCoverageAmount() {
        return coverageAmount;
    }

    public void setCoverageAmount(BigDecimal coverageAmount) {
        this.coverageAmount = coverageAmount;
    }

    public BigDecimal getPremiumAmount() {
        return premiumAmount;
    }

    public void setPremiumAmount(BigDecimal premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(String policyStatus) {
        this.policyStatus = policyStatus;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Policy ID: " + policyId +
                "\nPolicy Number: " + policyNumber +
                "\nCustomer ID: " + customerId +
                "\nInsurance Type ID: " + typeId +
                "\nCoverage Amount: " + coverageAmount +
                "\nPremium Amount: " + premiumAmount +
                "\nStart Date: " + startDate +
                "\nEnd Date: " + endDate +
                "\nStatus: " + policyStatus;
    }
}
