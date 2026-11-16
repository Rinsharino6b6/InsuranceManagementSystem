
package org.example.service;

import org.example.dao.InsuranceTypeDAO;
import org.example.model.InsuranceType;

import java.sql.SQLException;
import java.util.List;

public class InsuranceTypeService {

    private final InsuranceTypeDAO typeDAO =
            new InsuranceTypeDAO();

    // 1. Add Insurance Type
    public boolean addInsuranceType(InsuranceType type)
            throws SQLException {

        validateType(type);

        return typeDAO.addInsuranceType(type);
    }

    // 2. View All Insurance Types
    public List<InsuranceType> getAllInsuranceTypes()
            throws SQLException {

        return typeDAO.getAllInsuranceTypes();
    }

    // 3. Search By ID
    public InsuranceType getInsuranceTypeById(int id)
            throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Invalid insurance type ID");
        }

        return typeDAO.getInsuranceTypeById(id);
    }

    // 4. Update Insurance Type
    public boolean updateInsuranceType(InsuranceType type)
            throws SQLException {

        if (type == null || type.getTypeId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid insurance type");
        }

        validateType(type);

        return typeDAO.updateInsuranceType(type);
    }

    // 5. Delete Insurance Type
    public boolean deleteInsuranceType(int id)
            throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Invalid insurance type ID");
        }

        return typeDAO.deleteInsuranceType(id);
    }

    // Validation
    private void validateType(InsuranceType type) {

        if (type == null) {
            throw new IllegalArgumentException(
                    "Insurance type cannot be null");
        }

        if (type.getTypeName() == null ||
                type.getTypeName().isBlank()) {
            throw new IllegalArgumentException(
                    "Insurance type name is required");
        }

        if (type.getDescription() == null ||
                type.getDescription().isBlank()) {
            throw new IllegalArgumentException(
                    "Description is required");
        }

        type.setTypeName(
                type.getTypeName().trim().toUpperCase());

        type.setDescription(
                type.getDescription().trim());
    }
}
