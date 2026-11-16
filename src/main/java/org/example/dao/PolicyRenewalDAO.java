
package org.example.dao;

import org.example.config.DBConnection;
import org.example.model.PolicyRenewal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PolicyRenewalDAO {

    public int addRenewal(PolicyRenewal renewal) throws SQLException {

        String sql = "INSERT INTO policy_renewals " +
                "(policy_id, previous_end_date, renewed_start_date, " +
                "renewed_end_date, renewal_premium, renewal_status, renewal_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, renewal.getPolicyId());
            ps.setDate(2, renewal.getPreviousEndDate());
            ps.setDate(3, renewal.getRenewedStartDate());
            ps.setDate(4, renewal.getRenewedEndDate());
            ps.setBigDecimal(5, renewal.getRenewalPremium());
            ps.setString(6, renewal.getRenewalStatus());
            ps.setDate(7, renewal.getRenewalDate());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    public List<PolicyRenewal> getAllRenewals() throws SQLException {

        List<PolicyRenewal> renewals = new ArrayList<>();

        String sql = "SELECT * FROM policy_renewals ORDER BY renewal_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                renewals.add(mapRenewal(rs));
            }
        }

        return renewals;
    }

    public PolicyRenewal getRenewalById(int renewalId) throws SQLException {

        String sql = "SELECT * FROM policy_renewals WHERE renewal_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, renewalId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRenewal(rs);
                }
            }
        }

        return null;
    }

    public List<PolicyRenewal> getRenewalsByPolicyId(int policyId)
            throws SQLException {

        List<PolicyRenewal> renewals = new ArrayList<>();

        String sql = "SELECT * FROM policy_renewals WHERE policy_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, policyId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    renewals.add(mapRenewal(rs));
                }
            }
        }

        return renewals;
    }

    public boolean updateRenewal(PolicyRenewal renewal) throws SQLException {

        String sql = "UPDATE policy_renewals SET policy_id = ?, " +
                "previous_end_date = ?, renewed_start_date = ?, " +
                "renewed_end_date = ?, renewal_premium = ?, " +
                "renewal_status = ?, renewal_date = ? WHERE renewal_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, renewal.getPolicyId());
            ps.setDate(2, renewal.getPreviousEndDate());
            ps.setDate(3, renewal.getRenewedStartDate());
            ps.setDate(4, renewal.getRenewedEndDate());
            ps.setBigDecimal(5, renewal.getRenewalPremium());
            ps.setString(6, renewal.getRenewalStatus());
            ps.setDate(7, renewal.getRenewalDate());
            ps.setInt(8, renewal.getRenewalId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteRenewal(int renewalId) throws SQLException {

        String sql = "DELETE FROM policy_renewals WHERE renewal_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, renewalId);

            return ps.executeUpdate() > 0;
        }
    }

    private PolicyRenewal mapRenewal(ResultSet rs) throws SQLException {

        PolicyRenewal renewal = new PolicyRenewal();

        renewal.setRenewalId(rs.getInt("renewal_id"));
        renewal.setPolicyId(rs.getInt("policy_id"));
        renewal.setPreviousEndDate(rs.getDate("previous_end_date"));
        renewal.setRenewedStartDate(rs.getDate("renewed_start_date"));
        renewal.setRenewedEndDate(rs.getDate("renewed_end_date"));
        renewal.setRenewalPremium(rs.getBigDecimal("renewal_premium"));
        renewal.setRenewalStatus(rs.getString("renewal_status"));
        renewal.setRenewalDate(rs.getDate("renewal_date"));
        renewal.setCreatedAt(rs.getTimestamp("created_at"));

        return renewal;
    }
}
