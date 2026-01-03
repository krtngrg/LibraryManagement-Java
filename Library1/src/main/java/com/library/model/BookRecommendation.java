package com.library.model;

import java.sql.Timestamp;

/**
 * BookRecommendation model class
 * Corresponds to the 'book_recommendations' table in the database
 */
public class BookRecommendation {
    private int recommendationId;
    private int recommendedBy;
    private String bookTitle;
    private String author;
    private String isbn;
    private String publisher;
    private String reason;
    private String status; // PENDING, APPROVED, or REJECTED
    private Integer reviewedBy;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Additional fields for display purposes
    private String recommendedByName;
    private String reviewedByName;

    // Default constructor
    public BookRecommendation() {
        this.status = "PENDING";
    }

    // Constructor with essential fields
    public BookRecommendation(int recommendedBy, String bookTitle, String author, String reason) {
        this.recommendedBy = recommendedBy;
        this.bookTitle = bookTitle;
        this.author = author;
        this.reason = reason;
        this.status = "PENDING";
    }

    // Getters and Setters
    public int getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(int recommendationId) {
        this.recommendationId = recommendationId;
    }

    public int getRecommendedBy() {
        return recommendedBy;
    }

    public void setRecommendedBy(int recommendedBy) {
        this.recommendedBy = recommendedBy;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(Integer reviewedBy) {
        this.reviewedBy = reviewedBy;
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

    public String getRecommendedByName() {
        return recommendedByName;
    }

    public void setRecommendedByName(String recommendedByName) {
        this.recommendedByName = recommendedByName;
    }

    public String getReviewedByName() {
        return reviewedByName;
    }

    public void setReviewedByName(String reviewedByName) {
        this.reviewedByName = reviewedByName;
    }

    // Utility methods
    public boolean isPending() {
        return "PENDING".equals(this.status);
    }

    public boolean isApproved() {
        return "APPROVED".equals(this.status);
    }

    public boolean isRejected() {
        return "REJECTED".equals(this.status);
    }

    @Override
    public String toString() {
        return "BookRecommendation{" +
                "recommendationId=" + recommendationId +
                ", bookTitle='" + bookTitle + '\'' +
                ", author='" + author + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
