-- ============================================
-- Library Management System - Database Schema
-- ============================================

-- Drop existing tables if they exist
DROP TABLE IF EXISTS book_recommendations CASCADE;
DROP TABLE IF EXISTS transactions CASCADE;
DROP TABLE IF EXISTS otp_verification CASCADE;
DROP TABLE IF EXISTS books CASCADE;
DROP TABLE IF EXISTS students CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- ============================================
-- Users Table (Admin and Librarian)
-- ============================================
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'LIBRARIAN')),
    phone VARCHAR(15),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Index for faster email lookups during login
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);

-- ============================================
-- Students Table
-- ============================================
CREATE TABLE students (
    student_id SERIAL PRIMARY KEY,
    student_code VARCHAR(20) UNIQUE NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15),
    address TEXT,
    course VARCHAR(100),
    year_of_study INTEGER,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Index for faster student code and email lookups
CREATE INDEX idx_students_code ON students(student_code);
CREATE INDEX idx_students_email ON students(email);

-- ============================================
-- Books Table
-- ============================================
CREATE TABLE books (
    book_id SERIAL PRIMARY KEY,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    title VARCHAR(200) NOT NULL,
    author VARCHAR(100) NOT NULL,
    publisher VARCHAR(100),
    category VARCHAR(50),
    total_quantity INTEGER NOT NULL DEFAULT 0,
    available_quantity INTEGER NOT NULL DEFAULT 0,
    publication_year INTEGER,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_quantity CHECK (available_quantity >= 0 AND available_quantity <= total_quantity)
);

-- Indexes for faster book searches
CREATE INDEX idx_books_isbn ON books(isbn);
CREATE INDEX idx_books_title ON books(title);
CREATE INDEX idx_books_author ON books(author);
CREATE INDEX idx_books_category ON books(category);

-- ============================================
-- Transactions Table (Issue and Return)
-- ============================================
CREATE TABLE transactions (
    transaction_id SERIAL PRIMARY KEY,
    student_id INTEGER NOT NULL REFERENCES students(student_id) ON DELETE CASCADE,
    book_id INTEGER NOT NULL REFERENCES books(book_id) ON DELETE CASCADE,
    issued_by INTEGER NOT NULL REFERENCES users(user_id),
    issue_date DATE NOT NULL DEFAULT CURRENT_DATE,
    due_date DATE NOT NULL,
    return_date DATE,
    penalty_amount DECIMAL(10, 2) DEFAULT 0.00,
    status VARCHAR(20) NOT NULL DEFAULT 'ISSUED' CHECK (status IN ('ISSUED', 'RETURNED')),
    remarks TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Indexes for faster transaction queries
CREATE INDEX idx_transactions_student ON transactions(student_id);
CREATE INDEX idx_transactions_book ON transactions(book_id);
CREATE INDEX idx_transactions_status ON transactions(status);
CREATE INDEX idx_transactions_issue_date ON transactions(issue_date);
CREATE INDEX idx_transactions_due_date ON transactions(due_date);

-- ============================================
-- OTP Verification Table
-- ============================================
CREATE TABLE otp_verification (
    otp_id SERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    otp_code VARCHAR(6) NOT NULL,
    purpose VARCHAR(20) NOT NULL CHECK (purpose IN ('SIGNUP', 'FORGOT_PASSWORD')),
    is_verified BOOLEAN DEFAULT FALSE,
    expiry_time TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Index for faster OTP lookups
CREATE INDEX idx_otp_email ON otp_verification(email);
CREATE INDEX idx_otp_expiry ON otp_verification(expiry_time);

-- ============================================
-- Book Recommendations Table
-- ============================================
CREATE TABLE book_recommendations (
    recommendation_id SERIAL PRIMARY KEY,
    recommended_by INTEGER NOT NULL REFERENCES users(user_id),
    book_title VARCHAR(200) NOT NULL,
    author VARCHAR(100) NOT NULL,
    isbn VARCHAR(20),
    publisher VARCHAR(100),
    reason TEXT,
    status VARCHAR(20) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED')),
    reviewed_by INTEGER REFERENCES users(user_id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Index for faster recommendation queries
CREATE INDEX idx_recommendations_status ON book_recommendations(status);
CREATE INDEX idx_recommendations_user ON book_recommendations(recommended_by);

-- ============================================
-- Triggers for updating timestamps
-- ============================================

-- Function to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Apply trigger to all tables with updated_at column
CREATE TRIGGER update_users_updated_at BEFORE UPDATE ON users
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_students_updated_at BEFORE UPDATE ON students
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_books_updated_at BEFORE UPDATE ON books
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_transactions_updated_at BEFORE UPDATE ON transactions
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_recommendations_updated_at BEFORE UPDATE ON book_recommendations
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- ============================================
-- Comments for documentation
-- ============================================
COMMENT ON TABLE users IS 'Stores admin and librarian user accounts';
COMMENT ON TABLE students IS 'Stores student information';
COMMENT ON TABLE books IS 'Stores book inventory with quantity tracking';
COMMENT ON TABLE transactions IS 'Stores book issue and return transactions with penalty calculation';
COMMENT ON TABLE otp_verification IS 'Stores OTP codes for signup and password reset';
COMMENT ON TABLE book_recommendations IS 'Stores book recommendations from librarians';
