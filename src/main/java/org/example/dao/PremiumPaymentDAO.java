
package org.example.dao;

import org.example.config.DBConnection;
import org.example.model.PremiumPayment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PremiumPaymentDAO {

    // 1. Add Payment
    public boolean addPayment(PremiumPayment payment)
            throws SQLException {

        String sql = """
                INSERT INTO premium_payments
                (policy_id, amount, payment_date, due_date,
                 payment_method, payment_status, transaction_reference)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, payment.getPolicyId());
            ps.setBigDecimal(2, payment.getAmount());
            ps.setDate(3, payment.getPaymentDate());
            ps.setDate(4, payment.getDueDate());
            ps.setString(5, payment.getPaymentMethod());
            ps.setString(6, payment.getPaymentStatus());
            ps.setString(7, payment.getTransactionReference());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        payment.setPaymentId(rs.getInt(1));
                    }
                }
                return true;
            }
        }

        return false;
    }

    // 2. View All Payments
    public List<PremiumPayment> getAllPayments()
            throws SQLException {

        List<PremiumPayment> payments = new ArrayList<>();

        String sql = """
                SELECT * FROM premium_payments
                ORDER BY payment_id
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                payments.add(mapPayment(rs));
            }
        }

        return payments;
    }

    // 3. Search Payment By ID
    public PremiumPayment getPaymentById(int paymentId)
            throws SQLException {

        String sql = """
                SELECT * FROM premium_payments
                WHERE payment_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, paymentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapPayment(rs);
                }
            }
        }

        return null;
    }

    // 4. View Payments By Policy ID
    public List<PremiumPayment> getPaymentsByPolicyId(int policyId)
            throws SQLException {

        List<PremiumPayment> payments = new ArrayList<>();

        String sql = """
                SELECT * FROM premium_payments
                WHERE policy_id = ?
                ORDER BY payment_id
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, policyId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    payments.add(mapPayment(rs));
                }
            }
        }

        return payments;
    }

    // 5. Update Payment
    public boolean updatePayment(PremiumPayment payment)
            throws SQLException {

        String sql = """
                UPDATE premium_payments
                SET policy_id = ?,
                    amount = ?,
                    payment_date = ?,
                    due_date = ?,
                    payment_method = ?,
                    payment_status = ?,
                    transaction_reference = ?
                WHERE payment_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, payment.getPolicyId());
            ps.setBigDecimal(2, payment.getAmount());
            ps.setDate(3, payment.getPaymentDate());
            ps.setDate(4, payment.getDueDate());
            ps.setString(5, payment.getPaymentMethod());
            ps.setString(6, payment.getPaymentStatus());
            ps.setString(7, payment.getTransactionReference());
            ps.setInt(8, payment.getPaymentId());

            return ps.executeUpdate() > 0;
        }
    }

    // 6. Delete Payment
    public boolean deletePayment(int paymentId)
            throws SQLException {

        String sql = """
                DELETE FROM premium_payments
                WHERE payment_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, paymentId);

            return ps.executeUpdate() > 0;
        }
    }

    // 7. ResultSet Mapping
    private PremiumPayment mapPayment(ResultSet rs)
            throws SQLException {

        PremiumPayment payment = new PremiumPayment();

        payment.setPaymentId(rs.getInt("payment_id"));
        payment.setPolicyId(rs.getInt("policy_id"));
        payment.setAmount(rs.getBigDecimal("amount"));
        payment.setPaymentDate(rs.getDate("payment_date"));
        payment.setDueDate(rs.getDate("due_date"));
        payment.setPaymentMethod(rs.getString("payment_method"));
        payment.setPaymentStatus(rs.getString("payment_status"));
        payment.setTransactionReference(
                rs.getString("transaction_reference"));
        payment.setCreatedAt(rs.getTimestamp("created_at"));

        return payment;
    }
}
