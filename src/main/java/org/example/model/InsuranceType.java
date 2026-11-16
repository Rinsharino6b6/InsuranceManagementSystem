
package org.example.model;

import java.sql.Timestamp;

public class InsuranceType {

    private int typeId;
    private String typeName;
    private String description;
    private Timestamp createdAt;

    // Default Constructor
    public InsuranceType() {
    }

    // Parameterized Constructor
    public InsuranceType(String typeName, String description) {
        this.typeName = typeName;
        this.description = description;
    }

    // Getters and Setters

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "InsuranceType{" +
                "typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
