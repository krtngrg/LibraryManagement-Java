package com.library.dao;

import com.library.model.Student;
import java.sql.SQLException;
import java.util.List;

/**
 * DAO interface for Student operations
 */
public interface StudentDAO {

    /**
     * Create a new student
     * 
     * @param student Student object to create
     * @return Generated student ID
     * @throws SQLException if database error occurs
     */
    int createStudent(Student student) throws SQLException;

    /**
     * Get student by ID
     * 
     * @param studentId Student ID
     * @return Student object or null if not found
     * @throws SQLException if database error occurs
     */
    Student getStudentById(int studentId) throws SQLException;

    /**
     * Get student by student code
     * 
     * @param studentCode Student code
     * @return Student object or null if not found
     * @throws SQLException if database error occurs
     */
    Student getStudentByCode(String studentCode) throws SQLException;

    /**
     * Get all students
     * 
     * @return List of all students
     * @throws SQLException if database error occurs
     */
    List<Student> getAllStudents() throws SQLException;

    /**
     * Search students by name
     * 
     * @param name Name to search for
     * @return List of matching students
     * @throws SQLException if database error occurs
     */
    List<Student> searchStudentsByName(String name) throws SQLException;

    /**
     * Get students by course
     * 
     * @param course Course name
     * @return List of students in the course
     * @throws SQLException if database error occurs
     */
    List<Student> getStudentsByCourse(String course) throws SQLException;

    /**
     * Update student
     * 
     * @param student Student object with updated data
     * @return true if update successful, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean updateStudent(Student student) throws SQLException;

    /**
     * Delete student
     * 
     * @param studentId Student ID to delete
     * @return true if deletion successful, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean deleteStudent(int studentId) throws SQLException;

    /**
     * Check if student code exists
     * 
     * @param studentCode Student code to check
     * @return true if exists, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean studentCodeExists(String studentCode) throws SQLException;

    /**
     * Check if student email exists
     * 
     * @param email Email to check
     * @return true if exists, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean emailExists(String email) throws SQLException;
}
