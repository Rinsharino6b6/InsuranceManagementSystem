
package org.example.service;

import org.example.dao.CustomerDAO;
import org.example.model.Customer;

import java.sql.SQLException;
import java.util.List;

public class CustomerService {

    private final CustomerDAO customerDAO = new CustomerDAO();

    public boolean registerCustomer(Customer customer)
            throws SQLException {

        validateCustomer(customer);

        return customerDAO.addCustomer(customer);
    }

    public List<Customer> getAllCustomers()
            throws SQLException {

        return customerDAO.getAllCustomers();
    }

    public Customer getCustomerById(int id)
            throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Invalid customer ID");
        }

        return customerDAO.getCustomerById(id);
    }

    public boolean updateCustomer(Customer customer)
            throws SQLException {

        if (customer.getCustomerId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid customer ID");
        }

        validateCustomer(customer);

        return customerDAO.updateCustomer(customer);
    }

    public boolean deleteCustomer(int id)
            throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Invalid customer ID");
        }

        return customerDAO.deleteCustomer(id);
    }

    private void validateCustomer(Customer customer) {

        if (customer == null) {
            throw new IllegalArgumentException(
                    "Customer cannot be null");
        }

        if (customer.getFirstName() == null ||
                customer.getFirstName().isBlank()) {
            throw new IllegalArgumentException(
                    "First name is required");
        }

        if (customer.getLastName() == null ||
                customer.getLastName().isBlank()) {
            throw new IllegalArgumentException(
                    "Last name is required");
        }

        if (customer.getEmail() == null ||
                !customer.getEmail().matches(
                        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException(
                    "Invalid email address");
        }

        if (customer.getPhone() == null ||
                !customer.getPhone().matches("\\+?[0-9]{10,15}")) {
            throw new IllegalArgumentException(
                    "Invalid phone number");
        }

        if (customer.getDateOfBirth() == null) {
            throw new IllegalArgumentException(
                    "Date of birth is required");
        }

        if (customer.getDateOfBirth().isAfter(
                java.time.LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Date of birth cannot be in the future");
        }

        if (customer.getGender() == null ||
                !List.of("MALE", "FEMALE", "OTHER")
                        .contains(customer.getGender().toUpperCase())) {
            throw new IllegalArgumentException(
                    "Invalid gender");
        }

        if (customer.getPincode() == null ||
                customer.getPincode().isBlank()) {
            throw new IllegalArgumentException(
                    "Pincode is required");
        }
    }
}
