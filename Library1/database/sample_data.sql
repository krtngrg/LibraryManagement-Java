-- ============================================
-- Library Management System - Sample Data
-- ============================================

-- ============================================
-- Sample Users (Admin and Librarians)
-- Password for all users: password123
-- BCrypt hash: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy
-- ============================================

INSERT INTO users (full_name, email, password_hash, role, phone, is_active) VALUES
('System Administrator', 'admin@library.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN', '9876543210', TRUE),
('John Doe', 'john.doe@library.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'LIBRARIAN', '9876543211', TRUE),
('Jane Smith', 'jane.smith@library.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'LIBRARIAN', '9876543212', TRUE),
('Robert Johnson', 'robert.j@library.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'LIBRARIAN', '9876543213', FALSE);

-- ============================================
-- Sample Students
-- ============================================

INSERT INTO students (student_code, full_name, email, phone, address, course, year_of_study, is_active) VALUES
('STU001', 'Alice Williams', 'alice.w@student.edu', '9123456781', '123 Main St, City', 'Computer Science', 2, TRUE),
('STU002', 'Bob Brown', 'bob.b@student.edu', '9123456782', '456 Oak Ave, City', 'Information Technology', 3, TRUE),
('STU003', 'Charlie Davis', 'charlie.d@student.edu', '9123456783', '789 Pine Rd, City', 'Electronics', 1, TRUE),
('STU004', 'Diana Evans', 'diana.e@student.edu', '9123456784', '321 Elm St, City', 'Mechanical Engineering', 4, TRUE),
('STU005', 'Edward Foster', 'edward.f@student.edu', '9123456785', '654 Maple Dr, City', 'Civil Engineering', 2, TRUE),
('STU006', 'Fiona Green', 'fiona.g@student.edu', '9123456786', '987 Cedar Ln, City', 'Computer Science', 3, TRUE),
('STU007', 'George Harris', 'george.h@student.edu', '9123456787', '147 Birch Ct, City', 'Business Administration', 1, TRUE),
('STU008', 'Hannah Irving', 'hannah.i@student.edu', '9123456788', '258 Spruce Way, City', 'Mathematics', 2, TRUE);

-- ============================================
-- Sample Books
-- ============================================

INSERT INTO books (isbn, title, author, publisher, category, total_quantity, available_quantity, publication_year, description, is_active) VALUES
('978-0132350884', 'Clean Code', 'Robert C. Martin', 'Prentice Hall', 'Programming', 5, 5, 2008, 'A handbook of agile software craftsmanship', TRUE),
('978-0201633610', 'Design Patterns', 'Gang of Four', 'Addison-Wesley', 'Programming', 3, 3, 1994, 'Elements of reusable object-oriented software', TRUE),
('978-0596007126', 'Head First Design Patterns', 'Eric Freeman', 'O''Reilly Media', 'Programming', 4, 4, 2004, 'A brain-friendly guide to design patterns', TRUE),
('978-0134685991', 'Effective Java', 'Joshua Bloch', 'Addison-Wesley', 'Programming', 6, 6, 2017, 'Best practices for Java programming', TRUE),
('978-1449355739', 'Learning SQL', 'Alan Beaulieu', 'O''Reilly Media', 'Database', 4, 4, 2020, 'Master SQL fundamentals', TRUE),
('978-0321573513', 'Algorithms', 'Robert Sedgewick', 'Addison-Wesley', 'Computer Science', 5, 5, 2011, 'Comprehensive guide to algorithms', TRUE),
('978-0262033848', 'Introduction to Algorithms', 'Thomas H. Cormen', 'MIT Press', 'Computer Science', 4, 4, 2009, 'The classic algorithms textbook', TRUE),
('978-0596517748', 'JavaScript: The Good Parts', 'Douglas Crockford', 'O''Reilly Media', 'Web Development', 3, 3, 2008, 'Unearthing the excellence in JavaScript', TRUE),
('978-1491950296', 'Web Design with HTML, CSS, JavaScript', 'Jon Duckett', 'Wiley', 'Web Development', 5, 5, 2014, 'A modern guide to web design', TRUE),
('978-0134494166', 'Clean Architecture', 'Robert C. Martin', 'Prentice Hall', 'Software Engineering', 3, 3, 2017, 'A craftsman''s guide to software structure', TRUE),
('978-0135957059', 'The Pragmatic Programmer', 'David Thomas', 'Addison-Wesley', 'Software Engineering', 4, 4, 2019, 'Your journey to mastery', TRUE),
('978-0321125215', 'Domain-Driven Design', 'Eric Evans', 'Addison-Wesley', 'Software Engineering', 2, 2, 2003, 'Tackling complexity in the heart of software', TRUE),
('978-0596009205', 'Head First Java', 'Kathy Sierra', 'O''Reilly Media', 'Programming', 6, 6, 2005, 'A brain-friendly guide to Java', TRUE),
('978-1617294945', 'Spring in Action', 'Craig Walls', 'Manning', 'Framework', 3, 3, 2018, 'Covers Spring 5', TRUE),
('978-0134757599', 'Refactoring', 'Martin Fowler', 'Addison-Wesley', 'Software Engineering', 3, 3, 2018, 'Improving the design of existing code', TRUE);

-- ============================================
-- Sample Transactions
-- ============================================

-- Active transactions (books currently issued)
INSERT INTO transactions (student_id, book_id, issued_by, issue_date, due_date, status) VALUES
(1, 1, 2, CURRENT_DATE - INTERVAL '5 days', CURRENT_DATE + INTERVAL '9 days', 'ISSUED'),
(2, 5, 2, CURRENT_DATE - INTERVAL '3 days', CURRENT_DATE + INTERVAL '11 days', 'ISSUED'),
(3, 8, 3, CURRENT_DATE - INTERVAL '7 days', CURRENT_DATE + INTERVAL '7 days', 'ISSUED');

-- Update book quantities for issued books
UPDATE books SET available_quantity = available_quantity - 1 WHERE book_id IN (1, 5, 8);

-- Completed transactions (books returned on time)
INSERT INTO transactions (student_id, book_id, issued_by, issue_date, due_date, return_date, penalty_amount, status) VALUES
(4, 2, 2, CURRENT_DATE - INTERVAL '20 days', CURRENT_DATE - INTERVAL '6 days', CURRENT_DATE - INTERVAL '7 days', 0.00, 'RETURNED'),
(5, 3, 3, CURRENT_DATE - INTERVAL '25 days', CURRENT_DATE - INTERVAL '11 days', CURRENT_DATE - INTERVAL '12 days', 0.00, 'RETURNED'),
(6, 4, 2, CURRENT_DATE - INTERVAL '30 days', CURRENT_DATE - INTERVAL '16 days', CURRENT_DATE - INTERVAL '17 days', 0.00, 'RETURNED');

-- Completed transactions with penalties (late returns)
INSERT INTO transactions (student_id, book_id, issued_by, issue_date, due_date, return_date, penalty_amount, status, remarks) VALUES
(7, 6, 2, CURRENT_DATE - INTERVAL '35 days', CURRENT_DATE - INTERVAL '21 days', CURRENT_DATE - INTERVAL '18 days', 15.00, 'RETURNED', 'Returned 3 days late. Penalty: ₹5 x 3 = ₹15'),
(8, 7, 3, CURRENT_DATE - INTERVAL '40 days', CURRENT_DATE - INTERVAL '26 days', CURRENT_DATE - INTERVAL '21 days', 25.00, 'RETURNED', 'Returned 5 days late. Penalty: ₹5 x 5 = ₹25'),
(1, 9, 2, CURRENT_DATE - INTERVAL '50 days', CURRENT_DATE - INTERVAL '36 days', CURRENT_DATE - INTERVAL '35 days', 5.00, 'RETURNED', 'Returned 1 day late. Penalty: ₹5 x 1 = ₹5');

-- ============================================
-- Sample Book Recommendations
-- ============================================

INSERT INTO book_recommendations (recommended_by, book_title, author, isbn, publisher, reason, status) VALUES
(2, 'Microservices Patterns', 'Chris Richardson', '978-1617294549', 'Manning', 'Excellent resource for modern architecture patterns', 'PENDING'),
(3, 'Designing Data-Intensive Applications', 'Martin Kleppmann', '978-1449373320', 'O''Reilly Media', 'Essential for understanding distributed systems', 'PENDING'),
(2, 'The DevOps Handbook', 'Gene Kim', '978-1942788003', 'IT Revolution Press', 'Great guide for DevOps practices', 'APPROVED'),
(3, 'Site Reliability Engineering', 'Betsy Beyer', '978-1491929124', 'O''Reilly Media', 'Google''s approach to running production systems', 'APPROVED');

-- Update reviewed_by for approved recommendations
UPDATE book_recommendations SET reviewed_by = 1 WHERE status = 'APPROVED';

-- ============================================
-- Verification Queries
-- ============================================

-- Uncomment to verify data insertion

-- SELECT 'Users' as table_name, COUNT(*) as count FROM users
-- UNION ALL
-- SELECT 'Students', COUNT(*) FROM students
-- UNION ALL
-- SELECT 'Books', COUNT(*) FROM books
-- UNION ALL
-- SELECT 'Transactions', COUNT(*) FROM transactions
-- UNION ALL
-- SELECT 'Book Recommendations', COUNT(*) FROM book_recommendations;
