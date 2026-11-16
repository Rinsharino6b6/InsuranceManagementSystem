
package org.example;

import org.example.model.Customer;
import org.example.model.InsuranceType;
import org.example.model.Policy;
import org.example.model.PremiumPayment;
import org.example.model.Claim;
import org.example.model.ClaimDocument;
import org.example.model.PolicyRenewal;

import org.example.service.CustomerService;
import org.example.service.InsuranceTypeService;
import org.example.service.PolicyService;
import org.example.service.PremiumPaymentService;
import org.example.service.ClaimService;
import org.example.service.ClaimDocumentService;
import org.example.service.PolicyRenewalService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.math.BigDecimal;
import java.sql.Date;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    private static final CustomerService customerService =
            new CustomerService();

    private static final InsuranceTypeService typeService =
            new InsuranceTypeService();
    private static final PolicyService policyService =
            new PolicyService();
    private static final PremiumPaymentService paymentService =
            new PremiumPaymentService();
    private static final ClaimService claimService =
            new ClaimService();
    static ClaimDocumentService claimDocumentService =
            new ClaimDocumentService();
    static PolicyRenewalService policyRenewalService =
            new PolicyRenewalService();
    public static void main(String[] args) {

        while (true) {

            System.out.println("\n==================================");
            System.out.println("   INSURANCE MANAGEMENT SYSTEM");
            System.out.println("==================================");
            System.out.println("1. Customer Management");
            System.out.println("2. Insurance Type Management");
            System.out.println("3. Policy Management");
            System.out.println("4. Premium Payment Management");
            System.out.println("5. Claims Management");
            System.out.println("6. Claim Documents Management");
            System.out.println("7. Policy Renewals Management");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");

            String choice = sc.nextLine().trim();

            try {
                switch (choice) {

                    case "1" -> customerMenu();

                    case "2" -> insuranceTypeMenu();

                    case "3" -> policyMenu();
                    case "4" -> paymentMenu();
                    case "5" -> claimMenu();
                    case "6" -> claimDocumentMenu();
                    case "7" -> policyRenewalMenu();
                    case "0" -> {
                        System.out.println("Thank you!");
                        return;
                    }

                    default -> System.out.println("Invalid choice!");
                }

            } catch (SQLException e) {
                System.out.println("Database Error: " + e.getMessage());

            } catch (IllegalArgumentException e) {
                System.out.println("Validation Error: " + e.getMessage());

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ====================================
    // CUSTOMER MANAGEMENT
    // ====================================

    private static void customerMenu() throws SQLException {

        while (true) {

            System.out.println("\n===== CUSTOMER MANAGEMENT =====");
            System.out.println("1. Register Customer");
            System.out.println("2. View All Customers");
            System.out.println("3. Search Customer");
            System.out.println("4. Update Customer");
            System.out.println("5. Delete Customer");
            System.out.println("0. Back");

            System.out.print("Enter choice: ");

            String choice = sc.nextLine().trim();

            switch (choice) {

                case "1" -> registerCustomer();
                case "2" -> viewCustomers();
                case "3" -> searchCustomer();
                case "4" -> updateCustomer();
                case "5" -> deleteCustomer();

                case "0" -> {
                    return;
                }

                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static Customer readCustomer() {

        System.out.print("First Name: ");
        String firstName = sc.nextLine().trim();

        System.out.print("Last Name: ");
        String lastName = sc.nextLine().trim();

        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        System.out.print("Phone: ");
        String phone = sc.nextLine().trim();

        System.out.print("Date of Birth (YYYY-MM-DD): ");
        LocalDate dob = LocalDate.parse(sc.nextLine().trim());

        System.out.print("Gender (MALE/FEMALE/OTHER): ");
        String gender = sc.nextLine().trim().toUpperCase();

        System.out.print("Address: ");
        String address = sc.nextLine().trim();

        System.out.print("City: ");
        String city = sc.nextLine().trim();

        System.out.print("State: ");
        String state = sc.nextLine().trim();

        System.out.print("Pincode: ");
        String pincode = sc.nextLine().trim();

        return new Customer(
                firstName, lastName, email, phone,
                dob, gender, address, city, state, pincode
        );
    }

    private static void registerCustomer() throws SQLException {

        Customer customer = readCustomer();

        if (customerService.registerCustomer(customer)) {

            System.out.println("Customer registered successfully!");
            System.out.println("Generated Customer ID: "
                    + customer.getCustomerId());
        }
    }

    private static void viewCustomers() throws SQLException {

        List<Customer> customers =
                customerService.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        for (Customer c : customers) {
            displayCustomer(c);
        }
    }

    private static void searchCustomer() throws SQLException {

        System.out.print("Enter Customer ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        Customer customer = customerService.getCustomerById(id);

        if (customer == null) {
            System.out.println("Customer not found.");
        } else {
            displayCustomer(customer);
        }
    }

    private static void updateCustomer() throws SQLException {

        System.out.print("Enter Customer ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        Customer existing = customerService.getCustomerById(id);

        if (existing == null) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.println("Enter updated customer details:");

        Customer updated = readCustomer();
        updated.setCustomerId(id);

        if (customerService.updateCustomer(updated)) {
            System.out.println("Customer updated successfully!");
        } else {
            System.out.println("Update failed.");
        }
    }

    private static void deleteCustomer() throws SQLException {

        System.out.print("Enter Customer ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        if (customerService.deleteCustomer(id)) {
            System.out.println("Customer deleted successfully!");
        } else {
            System.out.println("Customer not found.");
        }
    }

    private static void displayCustomer(Customer c) {

        System.out.println("\n----- CUSTOMER DETAILS -----");

        System.out.println("Customer ID : " + c.getCustomerId());
        System.out.println("Name        : "
                + c.getFirstName() + " " + c.getLastName());
        System.out.println("Email       : " + c.getEmail());
        System.out.println("Phone       : " + c.getPhone());
        System.out.println("DOB         : " + c.getDateOfBirth());
        System.out.println("Gender      : " + c.getGender());
        System.out.println("Address     : " + c.getAddress());
        System.out.println("City        : " + c.getCity());
        System.out.println("State       : " + c.getState());
        System.out.println("Pincode     : " + c.getPincode());
    }

    // ====================================
    // INSURANCE TYPE MANAGEMENT
    // ====================================

    private static void insuranceTypeMenu() throws SQLException {

        while (true) {

            System.out.println("\n===== INSURANCE TYPE MANAGEMENT =====");
            System.out.println("1. Add Insurance Type");
            System.out.println("2. View All Insurance Types");
            System.out.println("3. Search Insurance Type");
            System.out.println("4. Update Insurance Type");
            System.out.println("5. Delete Insurance Type");
            System.out.println("0. Back");

            System.out.print("Enter choice: ");

            String choice = sc.nextLine().trim();

            switch (choice) {

                case "1" -> addInsuranceType();
                case "2" -> viewInsuranceTypes();
                case "3" -> searchInsuranceType();
                case "4" -> updateInsuranceType();
                case "5" -> deleteInsuranceType();

                case "0" -> {
                    return;
                }

                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void addInsuranceType() throws SQLException {

        System.out.print("Enter Type Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Description: ");
        String description = sc.nextLine();

        InsuranceType type =
                new InsuranceType(name, description);

        if (typeService.addInsuranceType(type)) {

            System.out.println("Insurance type added successfully!");
            System.out.println("Generated Type ID: " + type.getTypeId());
        }
    }

    private static void viewInsuranceTypes() throws SQLException {

        List<InsuranceType> types =
                typeService.getAllInsuranceTypes();

        if (types.isEmpty()) {
            System.out.println("No insurance types found.");
            return;
        }

        for (InsuranceType type : types) {
            displayInsuranceType(type);
        }
    }

    private static void searchInsuranceType() throws SQLException {

        System.out.print("Enter Type ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        InsuranceType type =
                typeService.getInsuranceTypeById(id);

        if (type == null) {
            System.out.println("Insurance type not found.");
        } else {
            displayInsuranceType(type);
        }
    }

    private static void updateInsuranceType() throws SQLException {

        System.out.print("Enter Type ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        InsuranceType existing =
                typeService.getInsuranceTypeById(id);

        if (existing == null) {
            System.out.println("Insurance type not found.");
            return;
        }

        System.out.print("Enter New Type Name: ");
        String name = sc.nextLine();

        System.out.print("Enter New Description: ");
        String description = sc.nextLine();

        InsuranceType updated =
                new InsuranceType(name, description);

        updated.setTypeId(id);

        if (typeService.updateInsuranceType(updated)) {
            System.out.println("Insurance type updated successfully!");
        } else {
            System.out.println("Update failed.");
        }
    }

    private static void deleteInsuranceType() throws SQLException {

        System.out.print("Enter Type ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        if (typeService.deleteInsuranceType(id)) {
            System.out.println("Insurance type deleted successfully!");
        } else {
            System.out.println("Insurance type not found.");
        }
    }


    // ====================================
    // POLICY MANAGEMENT
    // ====================================

    private static void policyMenu() throws SQLException {

        while (true) {

            System.out.println("\n===== POLICY MANAGEMENT =====");
            System.out.println("1. Add Policy");
            System.out.println("2. View All Policies");
            System.out.println("3. Search Policy");
            System.out.println("4. Update Policy");
            System.out.println("5. Delete Policy");
            System.out.println("0. Back");

            System.out.print("Enter choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {

                case "1" -> addPolicy();
                case "2" -> viewPolicies();
                case "3" -> searchPolicy();
                case "4" -> updatePolicy();
                case "5" -> deletePolicy();

                case "0" -> {
                    return;
                }

                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // Read Policy Details
    private static Policy readPolicy() {

        System.out.print("Policy Number: ");
        String policyNumber = sc.nextLine().trim();

        System.out.print("Customer ID: ");
        int customerId = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Insurance Type ID: ");
        int typeId = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Coverage Amount: ");
        BigDecimal coverage = new BigDecimal(sc.nextLine().trim());

        System.out.print("Premium Amount: ");
        BigDecimal premium = new BigDecimal(sc.nextLine().trim());

        System.out.print("Start Date (YYYY-MM-DD): ");
        Date startDate = Date.valueOf(sc.nextLine().trim());

        System.out.print("End Date (YYYY-MM-DD): ");
        Date endDate = Date.valueOf(sc.nextLine().trim());

        System.out.print("Policy Status (ACTIVE/EXPIRED/CANCELLED): ");
        String status = sc.nextLine().trim().toUpperCase();

        return new Policy(
                policyNumber,
                customerId,
                typeId,
                coverage,
                premium,
                startDate,
                endDate,
                status
        );
    }

    // 1. Add Policy
    private static void addPolicy() throws SQLException {

        Policy policy = readPolicy();

        if (policyService.addPolicy(policy)) {

            System.out.println("Policy added successfully!");
            System.out.println("Generated Policy ID: "
                    + policy.getPolicyId());
        }
    }

    // 2. View All Policies
    private static void viewPolicies() throws SQLException {

        List<Policy> policies =
                policyService.getAllPolicies();

        if (policies.isEmpty()) {
            System.out.println("No policies found.");
            return;
        }

        for (Policy policy : policies) {
            displayPolicy(policy);
        }
    }

    // 3. Search Policy
    private static void searchPolicy() throws SQLException {

        System.out.print("Enter Policy ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        Policy policy = policyService.getPolicyById(id);

        if (policy == null) {
            System.out.println("Policy not found.");
        } else {
            displayPolicy(policy);
        }
    }

    // 4. Update Policy
    private static void updatePolicy() throws SQLException {

        System.out.print("Enter Policy ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        Policy existing = policyService.getPolicyById(id);

        if (existing == null) {
            System.out.println("Policy not found.");
            return;
        }

        System.out.println("Enter updated policy details:");

        Policy updated = readPolicy();
        updated.setPolicyId(id);

        if (policyService.updatePolicy(updated)) {
            System.out.println("Policy updated successfully!");
        } else {
            System.out.println("Update failed.");
        }
    }

    // 5. Delete Policy
    private static void deletePolicy() throws SQLException {

        System.out.print("Enter Policy ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        if (policyService.deletePolicy(id)) {
            System.out.println("Policy deleted successfully!");
        } else {
            System.out.println("Policy not found.");
        }
    }

    // Display Policy Details
    private static void displayPolicy(Policy policy) {

        System.out.println("\n----- POLICY DETAILS -----");

        System.out.println("Policy ID       : " + policy.getPolicyId());
        System.out.println("Policy Number   : " + policy.getPolicyNumber());
        System.out.println("Customer ID     : " + policy.getCustomerId());
        System.out.println("Insurance Type  : " + policy.getTypeId());
        System.out.println("Coverage Amount : " + policy.getCoverageAmount());
        System.out.println("Premium Amount  : " + policy.getPremiumAmount());
        System.out.println("Start Date      : " + policy.getStartDate());
        System.out.println("End Date        : " + policy.getEndDate());
        System.out.println("Status          : " + policy.getPolicyStatus());
    }

    // ====================================
    // PREMIUM PAYMENT MANAGEMENT
    // ====================================

    private static void paymentMenu() throws SQLException {

        while (true) {

            System.out.println("\n===== PREMIUM PAYMENT MANAGEMENT =====");
            System.out.println("1. Record Payment");
            System.out.println("2. View All Payments");
            System.out.println("3. Search Payment");
            System.out.println("4. View Payments By Policy");
            System.out.println("5. Update Payment");
            System.out.println("6. Delete Payment");
            System.out.println("0. Back");

            System.out.print("Enter choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {

                case "1" -> addPayment();
                case "2" -> viewPayments();
                case "3" -> searchPayment();
                case "4" -> viewPaymentsByPolicy();
                case "5" -> updatePayment();
                case "6" -> deletePayment();

                case "0" -> {
                    return;
                }

                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // Read Payment Details
    private static PremiumPayment readPayment() {

        System.out.print("Policy ID: ");
        int policyId = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Payment Amount: ");
        BigDecimal amount = new BigDecimal(
                sc.nextLine().trim());

        System.out.print("Payment Date (YYYY-MM-DD, blank if pending): ");
        String paymentDateInput = sc.nextLine().trim();

        Date paymentDate = paymentDateInput.isEmpty()
                ? null
                : Date.valueOf(paymentDateInput);

        System.out.print("Due Date (YYYY-MM-DD): ");
        Date dueDate = Date.valueOf(sc.nextLine().trim());

        System.out.print("Payment Method (CASH/CARD/UPI/BANK_TRANSFER): ");
        String method = sc.nextLine().trim().toUpperCase();

        System.out.print("Payment Status (PAID/PENDING/FAILED): ");
        String status = sc.nextLine().trim().toUpperCase();

        System.out.print("Transaction Reference (blank if unavailable): ");
        String reference = sc.nextLine().trim();

        if (reference.isEmpty()) {
            reference = null;
        }

        return new PremiumPayment(
                policyId,
                amount,
                paymentDate,
                dueDate,
                method,
                status,
                reference
        );
    }

    // 1. Add Payment
    private static void addPayment() throws SQLException {

        PremiumPayment payment = readPayment();

        if (paymentService.addPayment(payment)) {

            System.out.println("Payment recorded successfully!");
            System.out.println("Generated Payment ID: "
                    + payment.getPaymentId());
        }
    }

    // 2. View All Payments
    private static void viewPayments() throws SQLException {

        List<PremiumPayment> payments =
                paymentService.getAllPayments();

        if (payments.isEmpty()) {
            System.out.println("No payment records found.");
            return;
        }

        for (PremiumPayment payment : payments) {
            displayPayment(payment);
        }
    }

    // 3. Search Payment
    private static void searchPayment() throws SQLException {

        System.out.print("Enter Payment ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        PremiumPayment payment =
                paymentService.getPaymentById(id);

        if (payment == null) {
            System.out.println("Payment not found.");
        } else {
            displayPayment(payment);
        }
    }

    // 4. View Payments By Policy
    private static void viewPaymentsByPolicy()
            throws SQLException {

        System.out.print("Enter Policy ID: ");
        int policyId = Integer.parseInt(sc.nextLine().trim());

        List<PremiumPayment> payments =
                paymentService.getPaymentsByPolicyId(policyId);

        if (payments.isEmpty()) {
            System.out.println("No payments found for this policy.");
            return;
        }

        for (PremiumPayment payment : payments) {
            displayPayment(payment);
        }
    }

    // 5. Update Payment
    private static void updatePayment() throws SQLException {

        System.out.print("Enter Payment ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        PremiumPayment existing =
                paymentService.getPaymentById(id);

        if (existing == null) {
            System.out.println("Payment not found.");
            return;
        }

        System.out.println("Enter updated payment details:");

        PremiumPayment updated = readPayment();
        updated.setPaymentId(id);

        if (paymentService.updatePayment(updated)) {
            System.out.println("Payment updated successfully!");
        } else {
            System.out.println("Update failed.");
        }
    }

    // 6. Delete Payment
    private static void deletePayment() throws SQLException {

        System.out.print("Enter Payment ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        if (paymentService.deletePayment(id)) {
            System.out.println("Payment deleted successfully!");
        } else {
            System.out.println("Payment not found.");
        }
    }

    // Display Payment Details
    private static void displayPayment(PremiumPayment payment) {

        System.out.println("\n----- PAYMENT DETAILS -----");

        System.out.println("Payment ID           : "
                + payment.getPaymentId());
        System.out.println("Policy ID            : "
                + payment.getPolicyId());
        System.out.println("Amount               : "
                + payment.getAmount());
        System.out.println("Payment Date         : "
                + payment.getPaymentDate());
        System.out.println("Due Date             : "
                + payment.getDueDate());
        System.out.println("Payment Method       : "
                + payment.getPaymentMethod());
        System.out.println("Payment Status       : "
                + payment.getPaymentStatus());
        System.out.println("Transaction Reference: "
                + payment.getTransactionReference());
    }

    // ====================================
    // CLAIMS MANAGEMENT
    // ====================================

    private static void claimMenu() throws SQLException {

        while (true) {

            System.out.println("\n===== CLAIMS MANAGEMENT =====");
            System.out.println("1. Submit Claim");
            System.out.println("2. View All Claims");
            System.out.println("3. Search Claim");
            System.out.println("4. View Claims By Policy");
            System.out.println("5. Update Claim");
            System.out.println("6. Delete Claim");
            System.out.println("0. Back");

            System.out.print("Enter choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {

                case "1" -> addClaim();
                case "2" -> viewClaims();
                case "3" -> searchClaim();
                case "4" -> viewClaimsByPolicy();
                case "5" -> updateClaim();
                case "6" -> deleteClaim();

                case "0" -> {
                    return;
                }

                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // Read Claim Details
    private static Claim readClaim() {

        System.out.print("Policy ID: ");
        int policyId = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Claim Amount: ");
        BigDecimal claimAmount =
                new BigDecimal(sc.nextLine().trim());

        System.out.print("Claim Date (YYYY-MM-DD): ");
        Date claimDate =
                Date.valueOf(sc.nextLine().trim());

        System.out.print("Claim Reason: ");
        String reason = sc.nextLine().trim();

        System.out.print(
                "Claim Status (SUBMITTED/UNDER_REVIEW/APPROVED/REJECTED/SETTLED): ");
        String status = sc.nextLine().trim().toUpperCase();

        System.out.print("Approved Amount (blank if not approved): ");
        String approvedInput = sc.nextLine().trim();

        BigDecimal approvedAmount = approvedInput.isEmpty()
                ? null
                : new BigDecimal(approvedInput);

        System.out.print("Remarks (optional): ");
        String remarks = sc.nextLine().trim();

        if (remarks.isEmpty()) {
            remarks = null;
        }

        return new Claim(
                policyId,
                claimAmount,
                claimDate,
                reason,
                status,
                approvedAmount,
                remarks
        );
    }

    // 1. Submit Claim
    private static void addClaim() throws SQLException {

        Claim claim = readClaim();

        if (claimService.addClaim(claim)) {

            System.out.println("Claim submitted successfully!");
            System.out.println("Generated Claim ID: "
                    + claim.getClaimId());
        }
    }

    // 2. View All Claims
    private static void viewClaims() throws SQLException {

        List<Claim> claims = claimService.getAllClaims();

        if (claims.isEmpty()) {
            System.out.println("No claims found.");
            return;
        }

        for (Claim claim : claims) {
            displayClaim(claim);
        }
    }

    // 3. Search Claim
    private static void searchClaim() throws SQLException {

        System.out.print("Enter Claim ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        Claim claim = claimService.getClaimById(id);

        if (claim == null) {
            System.out.println("Claim not found.");
        } else {
            displayClaim(claim);
        }
    }

    // 4. View Claims By Policy
    private static void viewClaimsByPolicy()
            throws SQLException {

        System.out.print("Enter Policy ID: ");
        int policyId = Integer.parseInt(sc.nextLine().trim());

        List<Claim> claims =
                claimService.getClaimsByPolicyId(policyId);

        if (claims.isEmpty()) {
            System.out.println("No claims found for this policy.");
            return;
        }

        for (Claim claim : claims) {
            displayClaim(claim);
        }
    }

    // 5. Update Claim
    private static void updateClaim() throws SQLException {

        System.out.print("Enter Claim ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        Claim existing = claimService.getClaimById(id);

        if (existing == null) {
            System.out.println("Claim not found.");
            return;
        }

        System.out.println("Enter updated claim details:");

        Claim updated = readClaim();
        updated.setClaimId(id);

        if (claimService.updateClaim(updated)) {
            System.out.println("Claim updated successfully!");
        } else {
            System.out.println("Update failed.");
        }
    }

    // 6. Delete Claim
    private static void deleteClaim() throws SQLException {

        System.out.print("Enter Claim ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        if (claimService.deleteClaim(id)) {
            System.out.println("Claim deleted successfully!");
        } else {
            System.out.println("Claim not found.");
        }
    }

    // Display Claim Details
    private static void displayClaim(Claim claim) {

        System.out.println("\n----- CLAIM DETAILS -----");

        System.out.println("Claim ID       : " + claim.getClaimId());
        System.out.println("Policy ID      : " + claim.getPolicyId());
        System.out.println("Claim Amount   : " + claim.getClaimAmount());
        System.out.println("Claim Date     : " + claim.getClaimDate());
        System.out.println("Claim Reason   : " + claim.getClaimReason());
        System.out.println("Claim Status   : " + claim.getClaimStatus());
        System.out.println("Approved Amount: " + claim.getApprovedAmount());
        System.out.println("Remarks        : " + claim.getRemarks());
    }

    static void claimDocumentMenu() {

        while (true) {
            System.out.println("\n===== CLAIM DOCUMENT MANAGEMENT =====");
            System.out.println("1. Add Document");
            System.out.println("2. View All Documents");
            System.out.println("3. Search Document by ID");
            System.out.println("4. View Documents by Claim ID");
            System.out.println("5. Update Document");
            System.out.println("6. Delete Document");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine();

            try {
                switch (choice) {
                    case "1" -> addClaimDocument();
                    case "2" -> viewClaimDocuments();
                    case "3" -> searchClaimDocument();
                    case "4" -> viewDocumentsByClaim();
                    case "5" -> updateClaimDocument();
                    case "6" -> deleteClaimDocument();
                    case "0" -> {
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    static ClaimDocument readClaimDocument() {

        System.out.print("Enter Claim ID: ");
        int claimId = Integer.parseInt(sc.nextLine());

        System.out.print("Enter Document Name: ");
        String documentName = sc.nextLine();

        System.out.print("Enter Document Path: ");
        String documentPath = sc.nextLine();

        return new ClaimDocument(claimId, documentName, documentPath);
    }

    static void addClaimDocument() throws Exception {

        ClaimDocument document = readClaimDocument();

        int id = claimDocumentService.addDocument(document);

        if (id > 0) {
            System.out.println("Document added successfully. ID: " + id);
        } else {
            System.out.println("Document could not be added.");
        }
    }

    static void viewClaimDocuments() throws Exception {

        List<ClaimDocument> documents =
                claimDocumentService.getAllDocuments();

        if (documents.isEmpty()) {
            System.out.println("No documents found.");
            return;
        }

        for (ClaimDocument document : documents) {
            System.out.println(document);
        }
    }

    static void searchClaimDocument() throws Exception {

        System.out.print("Enter Document ID: ");
        int id = Integer.parseInt(sc.nextLine());

        ClaimDocument document =
                claimDocumentService.getDocumentById(id);

        if (document != null) {
            System.out.println(document);
        } else {
            System.out.println("Document not found.");
        }
    }

    static void viewDocumentsByClaim() throws Exception {

        System.out.print("Enter Claim ID: ");
        int claimId = Integer.parseInt(sc.nextLine());

        List<ClaimDocument> documents =
                claimDocumentService.getDocumentsByClaimId(claimId);

        if (documents.isEmpty()) {
            System.out.println("No documents found for this claim.");
            return;
        }

        for (ClaimDocument document : documents) {
            System.out.println(document);
        }
    }

    static void updateClaimDocument() throws Exception {

        System.out.print("Enter Document ID to update: ");
        int id = Integer.parseInt(sc.nextLine());

        ClaimDocument document = readClaimDocument();
        document.setDocumentId(id);

        boolean updated =
                claimDocumentService.updateDocument(document);

        if (updated) {
            System.out.println("Document updated successfully.");
        } else {
            System.out.println("Document not found.");
        }
    }

    static void deleteClaimDocument() throws Exception {

        System.out.print("Enter Document ID to delete: ");
        int id = Integer.parseInt(sc.nextLine());

        boolean deleted = claimDocumentService.deleteDocument(id);

        if (deleted) {
            System.out.println("Document deleted successfully.");
        } else {
            System.out.println("Document not found.");
        }
    }

    static void policyRenewalMenu() {

        while (true) {
            System.out.println("\n===== POLICY RENEWALS MANAGEMENT =====");
            System.out.println("1. Add Renewal");
            System.out.println("2. View All Renewals");
            System.out.println("3. Search Renewal by ID");
            System.out.println("4. View Renewals by Policy ID");
            System.out.println("5. Update Renewal");
            System.out.println("6. Delete Renewal");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine();

            try {
                switch (choice) {
                    case "1" -> addPolicyRenewal();
                    case "2" -> viewPolicyRenewals();
                    case "3" -> searchPolicyRenewal();
                    case "4" -> viewRenewalsByPolicy();
                    case "5" -> updatePolicyRenewal();
                    case "6" -> deletePolicyRenewal();
                    case "0" -> {
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    static PolicyRenewal readPolicyRenewal() {

        System.out.print("Enter Policy ID: ");
        int policyId = Integer.parseInt(sc.nextLine());

        System.out.print("Enter Previous End Date (yyyy-mm-dd): ");
        Date previousEndDate = Date.valueOf(sc.nextLine());

        System.out.print("Enter Renewed Start Date (yyyy-mm-dd): ");
        Date renewedStartDate = Date.valueOf(sc.nextLine());

        System.out.print("Enter Renewed End Date (yyyy-mm-dd): ");
        Date renewedEndDate = Date.valueOf(sc.nextLine());

        System.out.print("Enter Renewal Premium: ");
        BigDecimal renewalPremium = new BigDecimal(sc.nextLine());

        System.out.print("Enter Renewal Status (PENDING/COMPLETED/REJECTED): ");
        String renewalStatus = sc.nextLine();

        System.out.print("Enter Renewal Date (yyyy-mm-dd, blank if not applicable): ");
        String renewalDateInput = sc.nextLine();

        Date renewalDate = renewalDateInput.isBlank()
                ? null
                : Date.valueOf(renewalDateInput);

        return new PolicyRenewal(
                policyId,
                previousEndDate,
                renewedStartDate,
                renewedEndDate,
                renewalPremium,
                renewalStatus,
                renewalDate
        );
    }

    static void addPolicyRenewal() throws Exception {

        PolicyRenewal renewal = readPolicyRenewal();

        int id = policyRenewalService.addRenewal(renewal);

        if (id > 0) {
            System.out.println("Policy renewal added successfully. ID: " + id);
        } else {
            System.out.println("Policy renewal could not be added.");
        }
    }

    static void viewPolicyRenewals() throws Exception {

        List<PolicyRenewal> renewals =
                policyRenewalService.getAllRenewals();

        if (renewals.isEmpty()) {
            System.out.println("No policy renewals found.");
            return;
        }

        for (PolicyRenewal renewal : renewals) {
            System.out.println(renewal);
        }
    }

    static void searchPolicyRenewal() throws Exception {

        System.out.print("Enter Renewal ID: ");
        int id = Integer.parseInt(sc.nextLine());

        PolicyRenewal renewal =
                policyRenewalService.getRenewalById(id);

        if (renewal != null) {
            System.out.println(renewal);
        } else {
            System.out.println("Renewal not found.");
        }
    }

    static void viewRenewalsByPolicy() throws Exception {

        System.out.print("Enter Policy ID: ");
        int policyId = Integer.parseInt(sc.nextLine());

        List<PolicyRenewal> renewals =
                policyRenewalService.getRenewalsByPolicyId(policyId);

        if (renewals.isEmpty()) {
            System.out.println("No renewals found for this policy.");
            return;
        }

        for (PolicyRenewal renewal : renewals) {
            System.out.println(renewal);
        }
    }

    static void updatePolicyRenewal() throws Exception {

        System.out.print("Enter Renewal ID to update: ");
        int id = Integer.parseInt(sc.nextLine());

        PolicyRenewal renewal = readPolicyRenewal();
        renewal.setRenewalId(id);

        boolean updated =
                policyRenewalService.updateRenewal(renewal);

        if (updated) {
            System.out.println("Policy renewal updated successfully.");
        } else {
            System.out.println("Renewal not found.");
        }
    }

    static void deletePolicyRenewal() throws Exception {

        System.out.print("Enter Renewal ID to delete: ");
        int id = Integer.parseInt(sc.nextLine());

        boolean deleted =
                policyRenewalService.deleteRenewal(id);

        if (deleted) {
            System.out.println("Policy renewal deleted successfully.");
        } else {
            System.out.println("Renewal not found.");
        }
    }

    private static void displayInsuranceType(InsuranceType type) {

        System.out.println("\n----- INSURANCE TYPE DETAILS -----");

        System.out.println("Type ID     : " + type.getTypeId());
        System.out.println("Type Name   : " + type.getTypeName());
        System.out.println("Description : " + type.getDescription());
    }
}

