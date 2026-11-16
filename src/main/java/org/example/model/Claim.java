
package org.example.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Claim {

    private int claimId;
    private int policyId;
    private BigDecimal claimAmount;
    private Date claimDate;
    private String claimReason;
    private String claimStatus;
    private BigDecimal approvedAmount;
    private String remarks;
    private Timestamp createdAt;

    public Claim() {
    }

    public Claim(int policyId, BigDecimal claimAmount,
                 Date claimDate, String claimReason,
                 String claimStatus, BigDecimal approvedAmount,
                 String remarks) {

        this.policyId = policyId;
        this.claimAmount = claimAmount;
        this.claimDate = claimDate;
        this.claimReason = claimReason;
        this.claimStatus = claimStatus;
        this.approvedAmount = approvedAmount;
        this.remarks = remarks;
    }

    public int getClaimId() {
        return claimId;
    }

    public void setClaimId(int claimId) {
        this.claimId = claimId;
    }

    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public BigDecimal getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(BigDecimal claimAmount) {
        this.claimAmount = claimAmount;
    }

    public Date getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }

    public String getClaimReason() {
        return claimReason;
    }

    public void setClaimReason(String claimReason) {
        this.claimReason = claimReason;
    }

    public String getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(String claimStatus) {
        this.claimStatus = claimStatus;
    }

    public BigDecimal getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(BigDecimal approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Claim ID: " + claimId +
                "\nPolicy ID: " + policyId +
                "\nClaim Amount: " + claimAmount +
                "\nClaim Date: " + claimDate +
                "\nClaim Reason: " + claimReason +
                "\nClaim Status: " + claimStatus +
                "\nApproved Amount: " + approvedAmount +
                "\nRemarks: " + remarks;
    }
}
