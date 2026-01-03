package com.library.dao;

import com.library.model.BookRecommendation;
import java.sql.SQLException;
import java.util.List;

/**
 * DAO interface for BookRecommendation operations
 */
public interface RecommendationDAO {

    /**
     * Create a new book recommendation
     * 
     * @param recommendation BookRecommendation object to create
     * @return Generated recommendation ID
     * @throws SQLException if database error occurs
     */
    int createRecommendation(BookRecommendation recommendation) throws SQLException;

    /**
     * Get recommendation by ID
     * 
     * @param recommendationId Recommendation ID
     * @return BookRecommendation object or null if not found
     * @throws SQLException if database error occurs
     */
    BookRecommendation getRecommendationById(int recommendationId) throws SQLException;

    /**
     * Get all recommendations
     * 
     * @return List of all recommendations
     * @throws SQLException if database error occurs
     */
    List<BookRecommendation> getAllRecommendations() throws SQLException;

    /**
     * Get recommendations by status
     * 
     * @param status Status (PENDING, APPROVED, REJECTED)
     * @return List of recommendations with the specified status
     * @throws SQLException if database error occurs
     */
    List<BookRecommendation> getRecommendationsByStatus(String status) throws SQLException;

    /**
     * Get recommendations by librarian
     * 
     * @param userId User ID of the librarian
     * @return List of recommendations by the librarian
     * @throws SQLException if database error occurs
     */
    List<BookRecommendation> getRecommendationsByLibrarian(int userId) throws SQLException;

    /**
     * Update recommendation status
     * 
     * @param recommendationId Recommendation ID
     * @param status           New status
     * @param reviewedBy       User ID of the reviewer
     * @return true if update successful, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean updateRecommendationStatus(int recommendationId, String status, int reviewedBy) throws SQLException;

    /**
     * Delete recommendation
     * 
     * @param recommendationId Recommendation ID to delete
     * @return true if deletion successful, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean deleteRecommendation(int recommendationId) throws SQLException;
}
