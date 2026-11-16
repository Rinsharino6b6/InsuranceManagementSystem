
package org.example.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class PolicyRenewal {

    private int renewalId;
    private int policyId;
    private Date previousEndDate;
    private Date renewedStartDate;
    private Date renewedEndDate;
    private BigDecimal renewalPremium;
    private String renewalStatus;
    private Date renewalDate;
    private Timestamp createdAt;

    public PolicyRenewal() {
    }

    public PolicyRenewal(int policyId, Date previousEndDate,
                         Date renewedStartDate, Date renewedEndDate,
                         BigDecimal renewalPremium, String renewalStatus,
                         Date renewalDate) {

        this.policyId = policyId;
        this.previousEndDate = previousEndDate;
        this.renewedStartDate = renewedStartDate;
        this.renewedEndDate = renewedEndDate;
        this.renewalPremium = renewalPremium;
        this.renewalStatus = renewalStatus;
        this.renewalDate = renewalDate;
    }

    public int getRenewalId() {
        return renewalId;
    }

    public void setRenewalId(int renewalId) {
        this.renewalId = renewalId;
    }

    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public Date getPreviousEndDate() {
        return previousEndDate;
    }

    public void setPreviousEndDate(Date previousEndDate) {
        this.previousEndDate = previousEndDate;
    }

    public Date getRenewedStartDate() {
        return renewedStartDate;
    }

    public void setRenewedStartDate(Date renewedStartDate) {
        this.renewedStartDate = renewedStartDate;
    }

    public Date getRenewedEndDate() {
        return renewedEndDate;
    }

    public void setRenewedEndDate(Date renewedEndDate) {
        this.renewedEndDate = renewedEndDate;
    }

    public BigDecimal getRenewalPremium() {
        return renewalPremium;
    }

    public void setRenewalPremium(BigDecimal renewalPremium) {
        this.renewalPremium = renewalPremium;
    }

    public String getRenewalStatus() {
        return renewalStatus;
    }

    public void setRenewalStatus(String renewalStatus) {
        this.renewalStatus = renewalStatus;
    }

    public Date getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(Date renewalDate) {
        this.renewalDate = renewalDate;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "PolicyRenewal{" +
                "renewalId=" + renewalId +
                ", policyId=" + policyId +
                ", previousEndDate=" + previousEndDate +
                ", renewedStartDate=" + renewedStartDate +
                ", renewedEndDate=" + renewedEndDate +
                ", renewalPremium=" + renewalPremium +
                ", renewalStatus='" + renewalStatus + '\'' +
                ", renewalDate=" + renewalDate +
                ", createdAt=" + createdAt +
                '}';
    }
}
