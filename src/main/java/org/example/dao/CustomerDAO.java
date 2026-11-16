
package org.example.dao;

import org.example.config.DBConnection;
import org.example.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    // 1. Register Customer
    public boolean addCustomer(Customer customer) throws SQLException {

        String sql = """
                INSERT INTO customers
                (first_name, last_name, email, phone,
                 date_of_birth, gender, address, city, state, pincode)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPhone());
            ps.setDate(5, Date.valueOf(customer.getDateOfBirth()));
            ps.setString(6, customer.getGender());
            ps.setString(7, customer.getAddress());
            ps.setString(8, customer.getCity());
            ps.setString(9, customer.getState());
            ps.setString(10, customer.getPincode());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        customer.setCustomerId(rs.getInt(1));
                    }
                }
                return true;
            }

            return false;
        }
    }

    // 2. View All Customers
    public List<Customer> getAllCustomers() throws SQLException {

        List<Customer> customers = new ArrayList<>();

        String sql = "SELECT * FROM customers ORDER BY customer_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                customers.add(mapCustomer(rs));
            }
        }

        return customers;
    }

    // 3. Find Customer By ID
    public Customer getCustomerById(int customerId)
            throws SQLException {

        String sql = """
                SELECT * FROM customers
                WHERE customer_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapCustomer(rs);
                }
            }
        }

        return null;
    }

    // 4. Update Customer
    public boolean updateCustomer(Customer customer)
            throws SQLException {

        String sql = """
                UPDATE customers SET
                first_name = ?, last_name = ?, email = ?,
                phone = ?, date_of_birth = ?, gender = ?,
                address = ?, city = ?, state = ?, pincode = ?
                WHERE customer_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPhone());
            ps.setDate(5, Date.valueOf(customer.getDateOfBirth()));
            ps.setString(6, customer.getGender());
            ps.setString(7, customer.getAddress());
            ps.setString(8, customer.getCity());
            ps.setString(9, customer.getState());
            ps.setString(10, customer.getPincode());
            ps.setInt(11, customer.getCustomerId());

            return ps.executeUpdate() > 0;
        }
    }

    // 5. Delete Customer
    public boolean deleteCustomer(int customerId)
            throws SQLException {

        String sql = """
                DELETE FROM customers
                WHERE customer_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerId);

            return ps.executeUpdate() > 0;
        }
    }

    // Reusable ResultSet Mapping
    private Customer mapCustomer(ResultSet rs)
            throws SQLException {

        Customer customer = new Customer();

        customer.setCustomerId(rs.getInt("customer_id"));
        customer.setFirstName(rs.getString("first_name"));
        customer.setLastName(rs.getString("last_name"));
        customer.setEmail(rs.getString("email"));
        customer.setPhone(rs.getString("phone"));
        customer.setDateOfBirth(
                rs.getDate("date_of_birth").toLocalDate());
        customer.setGender(rs.getString("gender"));
        customer.setAddress(rs.getString("address"));
        customer.setCity(rs.getString("city"));
        customer.setState(rs.getString("state"));
        customer.setPincode(rs.getString("pincode"));

        return customer;
    }
}
