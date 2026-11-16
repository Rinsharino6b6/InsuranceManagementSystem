
package org.example.dao;

import org.example.config.DBConnection;
import org.example.model.ClaimDocument;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClaimDocumentDAO {

    public int addDocument(ClaimDocument document) throws SQLException {

        String sql = "INSERT INTO claim_documents " +
                "(claim_id, document_name, document_path) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, document.getClaimId());
            ps.setString(2, document.getDocumentName());
            ps.setString(3, document.getDocumentPath());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    public List<ClaimDocument> getAllDocuments() throws SQLException {

        List<ClaimDocument> documents = new ArrayList<>();
        String sql = "SELECT * FROM claim_documents ORDER BY document_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                documents.add(mapDocument(rs));
            }
        }

        return documents;
    }

    public ClaimDocument getDocumentById(int documentId) throws SQLException {

        String sql = "SELECT * FROM claim_documents WHERE document_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, documentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapDocument(rs);
                }
            }
        }

        return null;
    }

    public List<ClaimDocument> getDocumentsByClaimId(int claimId)
            throws SQLException {

        List<ClaimDocument> documents = new ArrayList<>();

        String sql = "SELECT * FROM claim_documents WHERE claim_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, claimId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    documents.add(mapDocument(rs));
                }
            }
        }

        return documents;
    }

    public boolean updateDocument(ClaimDocument document) throws SQLException {

        String sql = "UPDATE claim_documents SET claim_id = ?, " +
                "document_name = ?, document_path = ? WHERE document_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, document.getClaimId());
            ps.setString(2, document.getDocumentName());
            ps.setString(3, document.getDocumentPath());
            ps.setInt(4, document.getDocumentId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteDocument(int documentId) throws SQLException {

        String sql = "DELETE FROM claim_documents WHERE document_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, documentId);

            return ps.executeUpdate() > 0;
        }
    }

    private ClaimDocument mapDocument(ResultSet rs) throws SQLException {

        ClaimDocument document = new ClaimDocument();

        document.setDocumentId(rs.getInt("document_id"));
        document.setClaimId(rs.getInt("claim_id"));
        document.setDocumentName(rs.getString("document_name"));
        document.setDocumentPath(rs.getString("document_path"));
        document.setUploadedAt(rs.getTimestamp("uploaded_at"));

        return document;
    }
}
