package com.library.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Transaction model class for book issue and return
 * Corresponds to the 'transactions' table in the database
 */
public class Transaction {
    private int transactionId;
    private int studentId;
    private int bookId;
    private int issuedBy;
    private Date issueDate;
    private Date dueDate;
    private Date returnDate;
    private BigDecimal penaltyAmount;
    private String status; // ISSUED or RETURNED
    private String remarks;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Additional fields for display purposes (not in database)
    private String studentName;
    private String bookTitle;
    private String issuedByName;

    // Default constructor
    public Transaction() {
        this.penaltyAmount = BigDecimal.ZERO;
        this.status = "ISSUED";
    }

    // Constructor with essential fields
    public Transaction(int studentId, int bookId, int issuedBy, Date issueDate, Date dueDate) {
        this.studentId = studentId;
        this.bookId = bookId;
        this.issuedBy = issuedBy;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.penaltyAmount = BigDecimal.ZERO;
        this.status = "ISSUED";
    }

    // Getters and Setters
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(int issuedBy) {
        this.issuedBy = issuedBy;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public BigDecimal getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(BigDecimal penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getIssuedByName() {
        return issuedByName;
    }

    public void setIssuedByName(String issuedByName) {
        this.issuedByName = issuedByName;
    }

    // Utility methods
    public boolean isIssued() {
        return "ISSUED".equals(this.status);
    }

    public boolean isReturned() {
        return "RETURNED".equals(this.status);
    }

    public boolean hasPenalty() {
        return this.penaltyAmount != null && this.penaltyAmount.compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", studentId=" + studentId +
                ", bookId=" + bookId +
                ", issueDate=" + issueDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", penaltyAmount=" + penaltyAmount +
                ", status='" + status + '\'' +
                '}';
    }
}
