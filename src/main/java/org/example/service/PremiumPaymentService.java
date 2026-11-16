
package org.example.service;

import org.example.dao.PremiumPaymentDAO;
import org.example.model.PremiumPayment;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class PremiumPaymentService {

    private final PremiumPaymentDAO paymentDAO =
            new PremiumPaymentDAO();

    private static final Set<String> VALID_METHODS =
            Set.of("CASH", "CARD", "UPI", "BANK_TRANSFER");

    private static final Set<String> VALID_STATUSES =
            Set.of("PAID", "PENDING", "FAILED");

    // 1. Add Payment
    public boolean addPayment(PremiumPayment payment)
            throws SQLException {

        validatePayment(payment);

        return paymentDAO.addPayment(payment);
    }

    // 2. View All Payments
    public List<PremiumPayment> getAllPayments()
            throws SQLException {

        return paymentDAO.getAllPayments();
    }

    // 3. Search Payment
    public PremiumPayment getPaymentById(int paymentId)
            throws SQLException {

        if (paymentId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid payment ID.");
        }

        return paymentDAO.getPaymentById(paymentId);
    }

    // 4. View Payments By Policy
    public List<PremiumPayment> getPaymentsByPolicyId(int policyId)
            throws SQLException {

        if (policyId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid policy ID.");
        }

        return paymentDAO.getPaymentsByPolicyId(policyId);
    }

    // 5. Update Payment
    public boolean updatePayment(PremiumPayment payment)
            throws SQLException {

        if (payment == null || payment.getPaymentId() <= 0) {
            throw new IllegalArgumentException(
                    "Valid payment ID is required.");
        }

        validatePayment(payment);

        return paymentDAO.updatePayment(payment);
    }

    // 6. Delete Payment
    public boolean deletePayment(int paymentId)
            throws SQLException {

        if (paymentId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid payment ID.");
        }

        return paymentDAO.deletePayment(paymentId);
    }

    // Payment Validation
    private void validatePayment(PremiumPayment payment) {

        if (payment == null) {
            throw new IllegalArgumentException(
                    "Payment cannot be null.");
        }

        if (payment.getPolicyId() <= 0) {
            throw new IllegalArgumentException(
                    "Valid policy ID is required.");
        }

        if (payment.getAmount() == null ||
                payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Payment amount must be greater than zero.");
        }

        if (payment.getDueDate() == null) {
            throw new IllegalArgumentException(
                    "Due date is required.");
        }

        String method = payment.getPaymentMethod();

        if (method == null ||
                !VALID_METHODS.contains(method.toUpperCase())) {
            throw new IllegalArgumentException(
                    "Invalid payment method.");
        }

        String status = payment.getPaymentStatus();

        if (status == null ||
                !VALID_STATUSES.contains(status.toUpperCase())) {
            throw new IllegalArgumentException(
                    "Invalid payment status.");
        }

        if ("PAID".equalsIgnoreCase(status) &&
                payment.getPaymentDate() == null) {
            throw new IllegalArgumentException(
                    "Payment date is required for PAID status.");
        }

        if (payment.getTransactionReference() != null) {
            String reference =
                    payment.getTransactionReference().trim();

            payment.setTransactionReference(
                    reference.isEmpty() ? null : reference);
        }

        payment.setPaymentMethod(method.toUpperCase());
        payment.setPaymentStatus(status.toUpperCase());
    }
}
