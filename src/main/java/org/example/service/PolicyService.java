
package org.example.service;

import org.example.dao.PolicyDAO;
import org.example.model.Policy;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class PolicyService {

    private final PolicyDAO policyDAO = new PolicyDAO();

    private static final Set<String> VALID_STATUSES =
            Set.of("ACTIVE", "EXPIRED", "CANCELLED");

    // 1. Add Policy
    public boolean addPolicy(Policy policy) throws SQLException {

        validatePolicy(policy);

        return policyDAO.addPolicy(policy);
    }

    // 2. View All Policies
    public List<Policy> getAllPolicies() throws SQLException {

        return policyDAO.getAllPolicies();
    }

    // 3. Search Policy
    public Policy getPolicyById(int policyId) throws SQLException {

        if (policyId <= 0) {
            throw new IllegalArgumentException("Invalid policy ID.");
        }

        return policyDAO.getPolicyById(policyId);
    }

    // 4. Update Policy
    public boolean updatePolicy(Policy policy) throws SQLException {

        if (policy.getPolicyId() <= 0) {
            throw new IllegalArgumentException("Invalid policy ID.");
        }

        validatePolicy(policy);

        return policyDAO.updatePolicy(policy);
    }

    // 5. Delete Policy
    public boolean deletePolicy(int policyId) throws SQLException {

        if (policyId <= 0) {
            throw new IllegalArgumentException("Invalid policy ID.");
        }

        return policyDAO.deletePolicy(policyId);
    }

    // Policy Validation
    private void validatePolicy(Policy policy) {

        if (policy == null) {
            throw new IllegalArgumentException("Policy cannot be null.");
        }

        if (policy.getPolicyNumber() == null ||
                policy.getPolicyNumber().isBlank()) {
            throw new IllegalArgumentException(
                    "Policy number is required.");
        }

        if (policy.getCustomerId() <= 0) {
            throw new IllegalArgumentException(
                    "Valid customer ID is required.");
        }

        if (policy.getTypeId() <= 0) {
            throw new IllegalArgumentException(
                    "Valid insurance type ID is required.");
        }

        if (policy.getCoverageAmount() == null ||
                policy.getCoverageAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Coverage amount must be greater than zero.");
        }

        if (policy.getPremiumAmount() == null ||
                policy.getPremiumAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Premium amount must be greater than zero.");
        }

        if (policy.getStartDate() == null ||
                policy.getEndDate() == null) {
            throw new IllegalArgumentException(
                    "Policy dates are required.");
        }

        LocalDate start = policy.getStartDate().toLocalDate();
        LocalDate end = policy.getEndDate().toLocalDate();

        if (!end.isAfter(start)) {
            throw new IllegalArgumentException(
                    "End date must be after start date.");
        }

        String status = policy.getPolicyStatus();

        if (status == null ||
                !VALID_STATUSES.contains(status.toUpperCase())) {
            throw new IllegalArgumentException(
                    "Status must be ACTIVE, EXPIRED or CANCELLED.");
        }

        policy.setPolicyStatus(status.toUpperCase());
    }
}
