
package org.example.dao;

import org.example.config.DBConnection;
import org.example.model.InsuranceType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InsuranceTypeDAO {

    // 1. Add Insurance Type
    public boolean addInsuranceType(InsuranceType type)
            throws SQLException {

        String sql = """
                INSERT INTO insurance_types
                (type_name, description)
                VALUES (?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, type.getTypeName());
            ps.setString(2, type.getDescription());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        type.setTypeId(rs.getInt(1));
                    }
                }
                return true;
            }

            return false;
        }
    }

    // 2. View All Insurance Types
    public List<InsuranceType> getAllInsuranceTypes()
            throws SQLException {

        List<InsuranceType> types = new ArrayList<>();

        String sql = """
                SELECT * FROM insurance_types
                ORDER BY type_id
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                types.add(mapInsuranceType(rs));
            }
        }

        return types;
    }

    // 3. Search Insurance Type By ID
    public InsuranceType getInsuranceTypeById(int typeId)
            throws SQLException {

        String sql = """
                SELECT * FROM insurance_types
                WHERE type_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, typeId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapInsuranceType(rs);
                }
            }
        }

        return null;
    }

    // 4. Update Insurance Type
    public boolean updateInsuranceType(InsuranceType type)
            throws SQLException {

        String sql = """
                UPDATE insurance_types
                SET type_name = ?, description = ?
                WHERE type_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, type.getTypeName());
            ps.setString(2, type.getDescription());
            ps.setInt(3, type.getTypeId());

            return ps.executeUpdate() > 0;
        }
    }

    // 5. Delete Insurance Type
    public boolean deleteInsuranceType(int typeId)
            throws SQLException {

        String sql = """
                DELETE FROM insurance_types
                WHERE type_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, typeId);

            return ps.executeUpdate() > 0;
        }
    }

    // 6. ResultSet Mapping
    private InsuranceType mapInsuranceType(ResultSet rs)
            throws SQLException {

        InsuranceType type = new InsuranceType();

        type.setTypeId(rs.getInt("type_id"));
        type.setTypeName(rs.getString("type_name"));
        type.setDescription(rs.getString("description"));
        type.setCreatedAt(rs.getTimestamp("created_at"));

        return type;
    }
}
