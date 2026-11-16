
package org.example.service;

import org.example.dao.ClaimDocumentDAO;
import org.example.model.ClaimDocument;

import java.sql.SQLException;
import java.util.List;

public class ClaimDocumentService {

    private final ClaimDocumentDAO documentDAO = new ClaimDocumentDAO();

    public int addDocument(ClaimDocument document) throws SQLException {

        validateDocument(document);
        return documentDAO.addDocument(document);
    }

    public List<ClaimDocument> getAllDocuments() throws SQLException {
        return documentDAO.getAllDocuments();
    }

    public ClaimDocument getDocumentById(int documentId) throws SQLException {

        if (documentId <= 0) {
            throw new IllegalArgumentException("Invalid document ID.");
        }

        return documentDAO.getDocumentById(documentId);
    }

    public List<ClaimDocument> getDocumentsByClaimId(int claimId)
            throws SQLException {

        if (claimId <= 0) {
            throw new IllegalArgumentException("Invalid claim ID.");
        }

        return documentDAO.getDocumentsByClaimId(claimId);
    }

    public boolean updateDocument(ClaimDocument document) throws SQLException {

        if (document.getDocumentId() <= 0) {
            throw new IllegalArgumentException("Invalid document ID.");
        }

        validateDocument(document);
        return documentDAO.updateDocument(document);
    }

    public boolean deleteDocument(int documentId) throws SQLException {

        if (documentId <= 0) {
            throw new IllegalArgumentException("Invalid document ID.");
        }

        return documentDAO.deleteDocument(documentId);
    }

    private void validateDocument(ClaimDocument document) {

        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null.");
        }

        if (document.getClaimId() <= 0) {
            throw new IllegalArgumentException("Claim ID must be positive.");
        }

        if (document.getDocumentName() == null ||
                document.getDocumentName().isBlank()) {
            throw new IllegalArgumentException("Document name is required.");
        }

        if (document.getDocumentPath() == null ||
                document.getDocumentPath().isBlank()) {
            throw new IllegalArgumentException("Document path is required.");
        }

        document.setDocumentName(document.getDocumentName().trim());
        document.setDocumentPath(document.getDocumentPath().trim());
    }
}

