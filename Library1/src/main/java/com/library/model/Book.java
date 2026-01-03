package com.library.model;

import java.sql.Timestamp;

/**
 * Book model class
 * Corresponds to the 'books' table in the database
 */
public class Book {
    private int bookId;
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private String category;
    private int totalQuantity;
    private int availableQuantity;
    private int publicationYear;
    private String description;
    private boolean isActive;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Default constructor
    public Book() {
    }

    // Constructor with essential fields
    public Book(String isbn, String title, String author, int totalQuantity) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = totalQuantity;
        this.isActive = true;
    }

    // Getters and Setters
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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

    // Utility methods
    public boolean isAvailable() {
        return this.availableQuantity > 0;
    }

    public int getIssuedQuantity() {
        return this.totalQuantity - this.availableQuantity;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", availableQuantity=" + availableQuantity +
                ", totalQuantity=" + totalQuantity +
                '}';
    }
}
