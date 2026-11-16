
package org.example.model;

import java.sql.Timestamp;

public class ClaimDocument {

    private int documentId;
    private int claimId;
    private String documentName;
    private String documentPath;
    private Timestamp uploadedAt;

    public ClaimDocument() {
    }

    public ClaimDocument(int claimId, String documentName, String documentPath) {
        this.claimId = claimId;
        this.documentName = documentName;
        this.documentPath = documentPath;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public int getClaimId() {
        return claimId;
    }

    public void setClaimId(int claimId) {
        this.claimId = claimId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public Timestamp getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Timestamp uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    @Override
    public String toString() {
        return "ClaimDocument{" +
                "documentId=" + documentId +
                ", claimId=" + claimId +
                ", documentName='" + documentName + '\'' +
                ", documentPath='" + documentPath + '\'' +
                ", uploadedAt=" + uploadedAt +
                '}';
    }
}
