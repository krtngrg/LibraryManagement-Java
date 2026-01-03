package com.library.dao;

import com.library.model.Book;
import java.sql.SQLException;
import java.util.List;

/**
 * DAO interface for Book operations
 */
public interface BookDAO {

    /**
     * Create a new book
     * 
     * @param book Book object to create
     * @return Generated book ID
     * @throws SQLException if database error occurs
     */
    int createBook(Book book) throws SQLException;

    /**
     * Get book by ID
     * 
     * @param bookId Book ID
     * @return Book object or null if not found
     * @throws SQLException if database error occurs
     */
    Book getBookById(int bookId) throws SQLException;

    /**
     * Get book by ISBN
     * 
     * @param isbn ISBN
     * @return Book object or null if not found
     * @throws SQLException if database error occurs
     */
    Book getBookByISBN(String isbn) throws SQLException;

    /**
     * Get all books
     * 
     * @return List of all books
     * @throws SQLException if database error occurs
     */
    List<Book> getAllBooks() throws SQLException;

    /**
     * Search books by title
     * 
     * @param title Title to search for
     * @return List of matching books
     * @throws SQLException if database error occurs
     */
    List<Book> searchBooksByTitle(String title) throws SQLException;

    /**
     * Search books by author
     * 
     * @param author Author to search for
     * @return List of matching books
     * @throws SQLException if database error occurs
     */
    List<Book> searchBooksByAuthor(String author) throws SQLException;

    /**
     * Get books by category
     * 
     * @param category Category name
     * @return List of books in the category
     * @throws SQLException if database error occurs
     */
    List<Book> getBooksByCategory(String category) throws SQLException;

    /**
     * Get available books (quantity > 0)
     * 
     * @return List of available books
     * @throws SQLException if database error occurs
     */
    List<Book> getAvailableBooks() throws SQLException;

    /**
     * Update book
     * 
     * @param book Book object with updated data
     * @return true if update successful, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean updateBook(Book book) throws SQLException;

    /**
     * Delete book
     * 
     * @param bookId Book ID to delete
     * @return true if deletion successful, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean deleteBook(int bookId) throws SQLException;

    /**
     * Update book quantity
     * 
     * @param bookId            Book ID
     * @param totalQuantity     New total quantity
     * @param availableQuantity New available quantity
     * @return true if update successful, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean updateBookQuantity(int bookId, int totalQuantity, int availableQuantity) throws SQLException;

    /**
     * Decrease available quantity (when issuing a book)
     * 
     * @param bookId Book ID
     * @return true if update successful, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean decreaseAvailableQuantity(int bookId) throws SQLException;

    /**
     * Increase available quantity (when returning a book)
     * 
     * @param bookId Book ID
     * @return true if update successful, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean increaseAvailableQuantity(int bookId) throws SQLException;

    /**
     * Check if ISBN exists
     * 
     * @param isbn ISBN to check
     * @return true if exists, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean isbnExists(String isbn) throws SQLException;
}
