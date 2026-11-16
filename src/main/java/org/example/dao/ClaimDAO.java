
package org.example.dao;

import org.example.config.DBConnection;
import org.example.model.Claim;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClaimDAO {

    // 1. Submit Claim
    public boolean addClaim(Claim claim) throws SQLException {

        String sql = """
                INSERT INTO claims
                (policy_id, claim_amount, claim_date, claim_reason,
                 claim_status, approved_amount, remarks)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, claim.getPolicyId());
            ps.setBigDecimal(2, claim.getClaimAmount());
            ps.setDate(3, claim.getClaimDate());
            ps.setString(4, claim.getClaimReason());
            ps.setString(5, claim.getClaimStatus());
            ps.setBigDecimal(6, claim.getApprovedAmount());
            ps.setString(7, claim.getRemarks());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        claim.setClaimId(rs.getInt(1));
                    }
                }
                return true;
            }
        }

        return false;
    }

    // 2. View All Claims
    public List<Claim> getAllClaims() throws SQLException {

        List<Claim> claims = new ArrayList<>();

        String sql = "SELECT * FROM claims ORDER BY claim_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                claims.add(mapClaim(rs));
            }
        }

        return claims;
    }

    // 3. Search Claim By ID
    public Claim getClaimById(int claimId) throws SQLException {

        String sql = "SELECT * FROM claims WHERE claim_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, claimId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapClaim(rs);
                }
            }
        }

        return null;
    }

    // 4. View Claims By Policy ID
    public List<Claim> getClaimsByPolicyId(int policyId)
            throws SQLException {

        List<Claim> claims = new ArrayList<>();

        String sql = """
                SELECT * FROM claims
                WHERE policy_id = ?
                ORDER BY claim_id
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, policyId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    claims.add(mapClaim(rs));
                }
            }
        }

        return claims;
    }

    // 5. Update Claim
    public boolean updateClaim(Claim claim) throws SQLException {

        String sql = """
                UPDATE claims
                SET policy_id = ?,
                    claim_amount = ?,
                    claim_date = ?,
                    claim_reason = ?,
                    claim_status = ?,
                    approved_amount = ?,
                    remarks = ?
                WHERE claim_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, claim.getPolicyId());
            ps.setBigDecimal(2, claim.getClaimAmount());
            ps.setDate(3, claim.getClaimDate());
            ps.setString(4, claim.getClaimReason());
            ps.setString(5, claim.getClaimStatus());
            ps.setBigDecimal(6, claim.getApprovedAmount());
            ps.setString(7, claim.getRemarks());
            ps.setInt(8, claim.getClaimId());

            return ps.executeUpdate() > 0;
        }
    }

    // 6. Delete Claim
    public boolean deleteClaim(int claimId) throws SQLException {

        String sql = "DELETE FROM claims WHERE claim_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, claimId);

            return ps.executeUpdate() > 0;
        }
    }

    // 7. ResultSet Mapping
    private Claim mapClaim(ResultSet rs) throws SQLException {

        Claim claim = new Claim();

        claim.setClaimId(rs.getInt("claim_id"));
        claim.setPolicyId(rs.getInt("policy_id"));
        claim.setClaimAmount(rs.getBigDecimal("claim_amount"));
        claim.setClaimDate(rs.getDate("claim_date"));
        claim.setClaimReason(rs.getString("claim_reason"));
        claim.setClaimStatus(rs.getString("claim_status"));
        claim.setApprovedAmount(rs.getBigDecimal("approved_amount"));
        claim.setRemarks(rs.getString("remarks"));
        claim.setCreatedAt(rs.getTimestamp("created_at"));

        return claim;
    }
}
