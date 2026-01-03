# Database Setup Guide for Library Management System

## Quick Setup (Recommended)

### Option 1: Using the Setup Script

1. **Open the setup script** in a text editor:
   - File: `setup-database.bat`

2. **Update PostgreSQL path** (if needed):
   ```batch
   set PSQL_PATH=C:\Program Files\PostgreSQL\16\bin
   ```
   Common locations:
   - `C:\Program Files\PostgreSQL\16\bin`
   - `C:\Program Files\PostgreSQL\15\bin`
   - `C:\Program Files\PostgreSQL\14\bin`

3. **Run the script**:
   - Right-click `setup-database.bat`
   - Select "Run as Administrator"
   - Or open Command Prompt in project folder and run: `setup-database.bat`

4. **Enter PostgreSQL password** when prompted (default: `postgres`)

### Option 2: Using pgAdmin (GUI)

1. **Open pgAdmin 4**

2. **Create Database**:
   - Right-click on "Databases"
   - Select "Create" → "Database"
   - Database name: `library_db_1`
   - Owner: `postgres`
   - Click "Save"

3. **Load Schema**:
   - Right-click on `library_db_1`
   - Select "Query Tool"
   - Open file: `database/schema.sql`
   - Click "Execute" (F5)

4. **Load Sample Data**:
   - In the same Query Tool
   - Open file: `database/sample_data.sql`
   - Click "Execute" (F5)

### Option 3: Using SQL Shell (psql)

1. **Open SQL Shell (psql)** from Start Menu

2. **Connect to PostgreSQL**:
   - Server: `localhost` (press Enter)
   - Database: `postgres` (press Enter)
   - Port: `5432` (press Enter)
   - Username: `postgres` (press Enter)
   - Password: Enter your PostgreSQL password

3. **Create Database**:
   ```sql
   CREATE DATABASE library_db_1;
   ```

4. **Connect to the new database**:
   ```sql
   \c library_db_1
   ```

5. **Load Schema**:
   ```sql
   \i 'C:/Users/krtng/OneDrive/Desktop/actual_project/Library1/database/schema.sql'
   ```

6. **Load Sample Data**:
   ```sql
   \i 'C:/Users/krtng/OneDrive/Desktop/actual_project/Library1/database/sample_data.sql'
   ```

## Verification

After setup, verify the database:

```sql
-- Connect to library_db_1
\c library_db_1

-- Check tables
\dt

-- Verify data
SELECT COUNT(*) FROM users;
SELECT COUNT(*) FROM students;
SELECT COUNT(*) FROM books;
SELECT COUNT(*) FROM transactions;
```

Expected results:
- users: 4 rows
- students: 8 rows
- books: 15 rows
- transactions: 9 rows

## Default Login Credentials

### Admin Account
- **Email**: admin@library.com
- **Password**: password123

### Librarian Account
- **Email**: john.doe@library.com
- **Password**: password123

**⚠️ IMPORTANT**: Change these passwords after first login!

## Troubleshooting

### Issue: "psql is not recognized"
**Solution**: Add PostgreSQL bin folder to Windows PATH:
1. Search for "Environment Variables" in Windows
2. Edit "Path" in System Variables
3. Add: `C:\Program Files\PostgreSQL\16\bin` (adjust version)
4. Restart Command Prompt

### Issue: "database already exists"
**Solution**: Drop and recreate:
```sql
DROP DATABASE library_db_1;
CREATE DATABASE library_db_1;
```

### Issue: "permission denied"
**Solution**: Run Command Prompt or pgAdmin as Administrator

### Issue: "password authentication failed"
**Solution**: Check your PostgreSQL password in pgAdmin or reset it

## Next Steps

After database setup:

1. **Configure Application**:
   ```bash
   cp src/main/resources/config.properties.example src/main/resources/config.properties
   ```

2. **Update config.properties**:
   ```properties
   db.url=jdbc:postgresql://localhost:5432/library_db_1
   db.username=postgres
   db.password=your_actual_password
   ```

3. **Build the project**:
   ```bash
   mvn clean install
   ```

4. **Deploy to Tomcat** and access the application

## Database Schema Overview

### Tables Created
- `users` - Admin and librarian accounts
- `students` - Student information
- `books` - Book inventory with quantity tracking
- `transactions` - Book issue/return records with penalties
- `otp_verification` - OTP codes for email verification
- `book_recommendations` - Book recommendations from librarians

All tables include automatic timestamp tracking and proper indexes for performance.
