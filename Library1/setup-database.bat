@echo off
REM Library Management System - Database Setup Script for Windows
REM This script creates the PostgreSQL database and loads the schema and sample data

echo ========================================
echo Library Management System - Database Setup
echo ========================================
echo.

REM Set PostgreSQL bin path (modify this if your PostgreSQL is installed elsewhere)
set PSQL_PATH=C:\Program Files\PostgreSQL\18\bin
set PGPASSWORD=root

REM Check if psql exists
if not exist "%PSQL_PATH%\psql.exe" (
    echo ERROR: PostgreSQL not found at %PSQL_PATH%
    echo Please update PSQL_PATH in this script to match your PostgreSQL installation
    echo Common locations:
    echo   C:\Program Files\PostgreSQL\16\bin
    echo   C:\Program Files\PostgreSQL\15\bin
    echo   C:\Program Files\PostgreSQL\14\bin
    pause
    exit /b 1
)

echo Step 1: Creating database library_db_1...
"%PSQL_PATH%\psql.exe" -U postgres -c "CREATE DATABASE library_db_1;"
if %ERRORLEVEL% NEQ 0 (
    echo WARNING: Database creation failed. It may already exist.
    echo Continuing with schema setup...
)
echo.

echo Step 2: Loading database schema...
"%PSQL_PATH%\psql.exe" -U postgres -d library_db_1 -f database\schema.sql
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Failed to load schema
    pause
    exit /b 1
)
echo Schema loaded successfully!
echo.

echo Step 3: Loading sample data...
"%PSQL_PATH%\psql.exe" -U postgres -d library_db_1 -f database\sample_data.sql
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Failed to load sample data
    pause
    exit /b 1
)
echo Sample data loaded successfully!
echo.

echo ========================================
echo Database setup completed successfully!
echo ========================================
echo.
echo Database: library_db_1
echo Default Admin Login:
echo   Email: admin@library.com
echo   Password: password123
echo.
echo Default Librarian Login:
echo   Email: john.doe@library.com
echo   Password: password123
echo.
echo IMPORTANT: Change these passwords after first login!
echo ========================================
pause
