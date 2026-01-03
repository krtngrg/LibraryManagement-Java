package com.library.dao;

import com.library.model.Transaction;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * DAO interface for Transaction operations
 */
public interface TransactionDAO {

    /**
     * Create a new transaction (issue book)
     * 
     * @param transaction Transaction object to create
     * @return Generated transaction ID
     * @throws SQLException if database error occurs
     */
    int createTransaction(Transaction transaction) throws SQLException;

    /**
     * Get transaction by ID
     * 
     * @param transactionId Transaction ID
     * @return Transaction object or null if not found
     * @throws SQLException if database error occurs
     */
    Transaction getTransactionById(int transactionId) throws SQLException;

    /**
     * Get all transactions
     * 
     * @return List of all transactions
     * @throws SQLException if database error occurs
     */
    List<Transaction> getAllTransactions() throws SQLException;

    /**
     * Get transactions by student ID
     * 
     * @param studentId Student ID
     * @return List of transactions for the student
     * @throws SQLException if database error occurs
     */
    List<Transaction> getTransactionsByStudent(int studentId) throws SQLException;

    /**
     * Get transactions by book ID
     * 
     * @param bookId Book ID
     * @return List of transactions for the book
     * @throws SQLException if database error occurs
     */
    List<Transaction> getTransactionsByBook(int bookId) throws SQLException;

    /**
     * Get active transactions (issued books not yet returned)
     * 
     * @return List of active transactions
     * @throws SQLException if database error occurs
     */
    List<Transaction> getActiveTransactions() throws SQLException;

    /**
     * Get active transactions by student
     * 
     * @param studentId Student ID
     * @return List of active transactions for the student
     * @throws SQLException if database error occurs
     */
    List<Transaction> getActiveTransactionsByStudent(int studentId) throws SQLException;

    /**
     * Get overdue transactions
     * 
     * @return List of overdue transactions
     * @throws SQLException if database error occurs
     */
    List<Transaction> getOverdueTransactions() throws SQLException;

    /**
     * Return a book (update transaction)
     * 
     * @param transactionId Transaction ID
     * @param returnDate    Return date
     * @param penaltyAmount Penalty amount
     * @param remarks       Remarks
     * @return true if update successful, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean returnBook(int transactionId, Date returnDate, java.math.BigDecimal penaltyAmount, String remarks)
            throws SQLException;

    /**
     * Get transactions by date range
     * 
     * @param startDate Start date
     * @param endDate   End date
     * @return List of transactions in the date range
     * @throws SQLException if database error occurs
     */
    List<Transaction> getTransactionsByDateRange(Date startDate, Date endDate) throws SQLException;

    /**
     * Get transaction report (with student and book details)
     * 
     * @param startDate Start date (optional)
     * @param endDate   End date (optional)
     * @return List of transactions with details
     * @throws SQLException if database error occurs
     */
    List<Transaction> getTransactionReport(Date startDate, Date endDate) throws SQLException;

    /**
     * Check if student has active transactions
     * 
     * @param studentId Student ID
     * @return true if student has active transactions, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean hasActiveTransactions(int studentId) throws SQLException;
}
