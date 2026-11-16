
package org.example.service;

import org.example.dao.PolicyRenewalDAO;
import org.example.model.PolicyRenewal;

import java.sql.SQLException;
import java.util.List;

public class PolicyRenewalService {

    private final PolicyRenewalDAO renewalDAO = new PolicyRenewalDAO();

    public int addRenewal(PolicyRenewal renewal) throws SQLException {
        validateRenewal(renewal);
        return renewalDAO.addRenewal(renewal);
    }

    public List<PolicyRenewal> getAllRenewals() throws SQLException {
        return renewalDAO.getAllRenewals();
    }

    public PolicyRenewal getRenewalById(int renewalId) throws SQLException {

        if (renewalId <= 0) {
            throw new IllegalArgumentException("Invalid renewal ID.");
        }

        return renewalDAO.getRenewalById(renewalId);
    }

    public List<PolicyRenewal> getRenewalsByPolicyId(int policyId)
            throws SQLException {

        if (policyId <= 0) {
            throw new IllegalArgumentException("Invalid policy ID.");
        }

        return renewalDAO.getRenewalsByPolicyId(policyId);
    }

    public boolean updateRenewal(PolicyRenewal renewal) throws SQLException {

        if (renewal.getRenewalId() <= 0) {
            throw new IllegalArgumentException("Invalid renewal ID.");
        }

        validateRenewal(renewal);
        return renewalDAO.updateRenewal(renewal);
    }

    public boolean deleteRenewal(int renewalId) throws SQLException {

        if (renewalId <= 0) {
            throw new IllegalArgumentException("Invalid renewal ID.");
        }

        return renewalDAO.deleteRenewal(renewalId);
    }

    private void validateRenewal(PolicyRenewal renewal) {

        if (renewal == null) {
            throw new IllegalArgumentException("Renewal cannot be null.");
        }

        if (renewal.getPolicyId() <= 0) {
            throw new IllegalArgumentException("Policy ID must be positive.");
        }

        if (renewal.getPreviousEndDate() == null ||
                renewal.getRenewedStartDate() == null ||
                renewal.getRenewedEndDate() == null) {
            throw new IllegalArgumentException("All policy dates are required.");
        }

        if (renewal.getRenewedStartDate()
                .before(renewal.getPreviousEndDate())) {
            throw new IllegalArgumentException(
                    "Renewed start date cannot be before the previous end date.");
        }

        if (!renewal.getRenewedEndDate()
                .after(renewal.getRenewedStartDate())) {
            throw new IllegalArgumentException(
                    "Renewed end date must be after renewed start date.");
        }

        if (renewal.getRenewalPremium() == null ||
                renewal.getRenewalPremium().signum() <= 0) {
            throw new IllegalArgumentException(
                    "Renewal premium must be greater than zero.");
        }

        if (renewal.getRenewalStatus() == null) {
            throw new IllegalArgumentException("Renewal status is required.");
        }

        String status = renewal.getRenewalStatus().trim().toUpperCase();

        if (!status.equals("PENDING") &&
                !status.equals("COMPLETED") &&
                !status.equals("REJECTED")) {
            throw new IllegalArgumentException("Invalid renewal status.");
        }

        renewal.setRenewalStatus(status);
    }
}
