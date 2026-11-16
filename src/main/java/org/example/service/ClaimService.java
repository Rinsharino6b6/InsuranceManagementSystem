
package org.example.service;

import org.example.dao.ClaimDAO;
import org.example.model.Claim;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class ClaimService {

    private final ClaimDAO claimDAO = new ClaimDAO();

    private static final Set<String> VALID_STATUSES = Set.of(
            "SUBMITTED",
            "UNDER_REVIEW",
            "APPROVED",
            "REJECTED",
            "SETTLED"
    );

    // 1. Submit Claim
    public boolean addClaim(Claim claim) throws SQLException {

        validateClaim(claim);

        return claimDAO.addClaim(claim);
    }

    // 2. View All Claims
    public List<Claim> getAllClaims() throws SQLException {

        return claimDAO.getAllClaims();
    }

    // 3. Search Claim
    public Claim getClaimById(int claimId) throws SQLException {

        if (claimId <= 0) {
            throw new IllegalArgumentException("Invalid claim ID.");
        }

        return claimDAO.getClaimById(claimId);
    }

    // 4. View Claims By Policy
    public List<Claim> getClaimsByPolicyId(int policyId)
            throws SQLException {

        if (policyId <= 0) {
            throw new IllegalArgumentException("Invalid policy ID.");
        }

        return claimDAO.getClaimsByPolicyId(policyId);
    }

    // 5. Update Claim
    public boolean updateClaim(Claim claim) throws SQLException {

        if (claim == null || claim.getClaimId() <= 0) {
            throw new IllegalArgumentException(
                    "Valid claim ID is required.");
        }

        validateClaim(claim);

        return claimDAO.updateClaim(claim);
    }

    // 6. Delete Claim
    public boolean deleteClaim(int claimId) throws SQLException {

        if (claimId <= 0) {
            throw new IllegalArgumentException("Invalid claim ID.");
        }

        return claimDAO.deleteClaim(claimId);
    }

    // Claim Validation
    private void validateClaim(Claim claim) {

        if (claim == null) {
            throw new IllegalArgumentException(
                    "Claim cannot be null.");
        }

        if (claim.getPolicyId() <= 0) {
            throw new IllegalArgumentException(
                    "Valid policy ID is required.");
        }

        if (claim.getClaimAmount() == null ||
                claim.getClaimAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Claim amount must be greater than zero.");
        }

        if (claim.getClaimDate() == null) {
            throw new IllegalArgumentException(
                    "Claim date is required.");
        }

        if (claim.getClaimReason() == null ||
                claim.getClaimReason().isBlank()) {
            throw new IllegalArgumentException(
                    "Claim reason is required.");
        }

        String status = claim.getClaimStatus();

        if (status == null ||
                !VALID_STATUSES.contains(status.toUpperCase())) {
            throw new IllegalArgumentException(
                    "Invalid claim status.");
        }

        BigDecimal approved = claim.getApprovedAmount();

        if (approved != null) {

            if (approved.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException(
                        "Approved amount cannot be negative.");
            }

            if (approved.compareTo(claim.getClaimAmount()) > 0) {
                throw new IllegalArgumentException(
                        "Approved amount cannot exceed claim amount.");
            }
        }

        status = status.toUpperCase();

        if ((status.equals("APPROVED") ||
                status.equals("SETTLED")) && approved == null) {
            throw new IllegalArgumentException(
                    "Approved amount is required for APPROVED or SETTLED claims.");
        }

        claim.setClaimStatus(status);
    }
}
