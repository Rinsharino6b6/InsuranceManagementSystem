
package org.example.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class PremiumPayment {

    private int paymentId;
    private int policyId;
    private BigDecimal amount;
    private Date paymentDate;
    private Date dueDate;
    private String paymentMethod;
    private String paymentStatus;
    private String transactionReference;
    private Timestamp createdAt;

    public PremiumPayment() {
    }

    public PremiumPayment(int policyId, BigDecimal amount,
                          Date paymentDate, Date dueDate,
                          String paymentMethod, String paymentStatus,
                          String transactionReference) {

        this.policyId = policyId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.dueDate = dueDate;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.transactionReference = transactionReference;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Payment ID: " + paymentId +
                "\nPolicy ID: " + policyId +
                "\nAmount: " + amount +
                "\nPayment Date: " + paymentDate +
                "\nDue Date: " + dueDate +
                "\nPayment Method: " + paymentMethod +
                "\nPayment Status: " + paymentStatus +
                "\nTransaction Reference: " + transactionReference;
    }
}
