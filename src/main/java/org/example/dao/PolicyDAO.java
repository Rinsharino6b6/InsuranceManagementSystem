
package org.example.dao;

import org.example.config.DBConnection;
import org.example.model.Policy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PolicyDAO {

    // 1. Add Policy
    public boolean addPolicy(Policy policy) throws SQLException {

        String sql = """
                INSERT INTO policies
                (policy_number, customer_id, type_id,
                 coverage_amount, premium_amount,
                 start_date, end_date, policy_status)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, policy.getPolicyNumber());
            ps.setInt(2, policy.getCustomerId());
            ps.setInt(3, policy.getTypeId());
            ps.setBigDecimal(4, policy.getCoverageAmount());
            ps.setBigDecimal(5, policy.getPremiumAmount());
            ps.setDate(6, policy.getStartDate());
            ps.setDate(7, policy.getEndDate());
            ps.setString(8, policy.getPolicyStatus());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        policy.setPolicyId(rs.getInt(1));
                    }
                }
                return true;
            }
        }

        return false;
    }

    // 2. View All Policies
    public List<Policy> getAllPolicies() throws SQLException {

        List<Policy> policies = new ArrayList<>();

        String sql = "SELECT * FROM policies ORDER BY policy_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                policies.add(mapPolicy(rs));
            }
        }

        return policies;
    }

    // 3. Search Policy By ID
    public Policy getPolicyById(int policyId) throws SQLException {

        String sql = "SELECT * FROM policies WHERE policy_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, policyId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapPolicy(rs);
                }
            }
        }

        return null;
    }

    // 4. Update Policy
    public boolean updatePolicy(Policy policy) throws SQLException {

        String sql = """
                UPDATE policies
                SET policy_number = ?,
                    customer_id = ?,
                    type_id = ?,
                    coverage_amount = ?,
                    premium_amount = ?,
                    start_date = ?,
                    end_date = ?,
                    policy_status = ?
                WHERE policy_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, policy.getPolicyNumber());
            ps.setInt(2, policy.getCustomerId());
            ps.setInt(3, policy.getTypeId());
            ps.setBigDecimal(4, policy.getCoverageAmount());
            ps.setBigDecimal(5, policy.getPremiumAmount());
            ps.setDate(6, policy.getStartDate());
            ps.setDate(7, policy.getEndDate());
            ps.setString(8, policy.getPolicyStatus());
            ps.setInt(9, policy.getPolicyId());

            return ps.executeUpdate() > 0;
        }
    }

    // 5. Delete Policy
    public boolean deletePolicy(int policyId) throws SQLException {

        String sql = "DELETE FROM policies WHERE policy_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, policyId);

            return ps.executeUpdate() > 0;
        }
    }

    // 6. ResultSet Mapping
    private Policy mapPolicy(ResultSet rs) throws SQLException {

        Policy policy = new Policy();

        policy.setPolicyId(rs.getInt("policy_id"));
        policy.setPolicyNumber(rs.getString("policy_number"));
        policy.setCustomerId(rs.getInt("customer_id"));
        policy.setTypeId(rs.getInt("type_id"));
        policy.setCoverageAmount(rs.getBigDecimal("coverage_amount"));
        policy.setPremiumAmount(rs.getBigDecimal("premium_amount"));
        policy.setStartDate(rs.getDate("start_date"));
        policy.setEndDate(rs.getDate("end_date"));
        policy.setPolicyStatus(rs.getString("policy_status"));
        policy.setCreatedAt(rs.getTimestamp("created_at"));

        return policy;
    }
}

