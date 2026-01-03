# Library Management System

A complete web-based Library Management System built with Java, JSP, Servlets, JDBC, and PostgreSQL following MVC architecture. This project is suitable for college mini/major projects with clean code, comprehensive comments, and professional folder structure.

## 🎯 Features

### 🔐 Authentication & Security
- Login system with email & password
- BCrypt password hashing
- Session management
- Role-based access control (Admin & Librarian)
- OTP verification via email for:
  - User signup
  - Forgot password
- OTP expiry and validation

### 🧑‍💼 Admin Module
- Admin dashboard with statistics
- Add, update, and delete librarian users
- Activate/deactivate users
- View all users
- Access system reports
- Review book recommendations

### 📚 Librarian Module

**Book Management**
- Add new books
- Update book details
- Delete books
- Search books by title, author, ISBN, category
- View available quantity

**Student Management**
- Add students
- Update student details
- Delete students
- Search students

**Issue & Return Books**
- Issue books to students
- Automatic due date calculation (14 days from issue date)
- Return books
- Update book availability automatically

### 💸 Penalty System
- Automatic penalty calculation for late returns
- Penalty: ₹5 per day late
- Formula: `if (returnDate > dueDate) { penalty = daysLate × 5; }`
- Penalty stored in database with transaction

### 📊 Reports Module
- Transaction reports for book issuance and returns
- Report fields:
  - Student name
  - Book name
  - Issue date
  - Due date
  - Return date
  - Penalty amount
- Filter reports by date range

### 💡 Additional Features
- Book recommendation module for librarians
- Admin can review and approve recommendations
- Email notifications for OTP
- Responsive UI using Bootstrap
- Clean and professional design

## 🛠 Technology Stack

- **Backend**: Java 11, Servlets, JSP
- **Database**: PostgreSQL
- **Build Tool**: Maven
- **Security**: BCrypt password hashing
- **Email**: JavaMail API
- **Frontend**: HTML, CSS, JavaScript, Bootstrap
- **Server**: Apache Tomcat 9.x

## 📁 Project Structure

```
Library1/
├── database/
│   ├── schema.sql              # Database schema
│   └── sample_data.sql         # Sample data for testing
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── library/
│       │           ├── controller/     # Servlets
│       │           │   ├── admin/      # Admin servlets
│       │           │   └── librarian/  # Librarian servlets
│       │           ├── dao/            # Data Access Objects
│       │           │   └── impl/       # DAO implementations
│       │           ├── filter/         # Authentication filters
│       │           ├── model/          # POJOs/Models
│       │           └── util/           # Utility classes
│       ├── resources/
│       │   └── config.properties.example  # Configuration template
│       └── webapp/
│           ├── admin/              # Admin JSP pages
│           ├── librarian/          # Librarian JSP pages
│           ├── includes/           # Common JSP includes
│           ├── css/                # Stylesheets
│           ├── js/                 # JavaScript files
│           └── WEB-INF/
│               └── web.xml         # Deployment descriptor
├── pom.xml                     # Maven configuration
└── README.md                   # This file
```

## 🚀 Setup Instructions

### Prerequisites
1. **Java Development Kit (JDK) 11 or higher**
   - Download from [Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or use OpenJDK
   - Verify installation: `java -version`

2. **Apache Maven**
   - Download from [Maven Official Site](https://maven.apache.org/download.cgi)
   - Verify installation: `mvn -version`

3. **PostgreSQL Database**
   - Download from [PostgreSQL Official Site](https://www.postgresql.org/download/)
   - Version 12 or higher recommended

4. **Apache Tomcat 9.x**
   - Download from [Tomcat Official Site](https://tomcat.apache.org/download-90.cgi)

### Database Setup

1. **Create Database**
   ```sql
   CREATE DATABASE library_db_1;
   ```

2. **Run Schema Script**
   ```bash
   psql -U postgres -d library_db_1 -f database/schema.sql
   ```

3. **Load Sample Data** (Optional)
   ```bash
   psql -U postgres -d library_db_1 -f database/sample_data.sql
   ```

### Application Configuration

1. **Copy Configuration File**
   ```bash
   cp src/main/resources/config.properties.example src/main/resources/config.properties
   ```

2. **Update Database Configuration** in `config.properties`:
   ```properties
   db.url=jdbc:postgresql://localhost:5432/library_db_1
   db.username=postgres
   db.password=your_password
   ```

3. **Update Email Configuration** for OTP functionality:
   ```properties
   email.smtp.host=smtp.gmail.com
   email.smtp.port=587
   email.smtp.username=your_email@gmail.com
   email.smtp.password=your_app_password
   email.from=your_email@gmail.com
   ```

   **Note**: For Gmail, you need to generate an [App Password](https://support.google.com/accounts/answer/185833)

### Build and Deploy

1. **Build the Project**
   ```bash
   mvn clean install
   ```

2. **Deploy to Tomcat**
   - Copy the generated WAR file from `target/library-management-system.war` to Tomcat's `webapps` directory
   - Or use Maven Tomcat plugin:
   ```bash
   mvn tomcat7:deploy
   ```

3. **Start Tomcat**
   - Windows: `catalina.bat run`
   - Linux/Mac: `./catalina.sh run`

4. **Access the Application**
   - Open browser and navigate to: `http://localhost:8080/library-management-system/`

## 👤 Default Login Credentials

### Admin Account
- **Email**: admin@library.com
- **Password**: password123

### Librarian Account
- **Email**: john.doe@library.com
- **Password**: password123

**⚠️ Important**: Change these default passwords after first login!

## 📖 Usage Guide

### For Admin

1. **Login** with admin credentials
2. **Dashboard** shows system statistics
3. **Manage Users**:
   - Add new librarian users
   - Update user details
   - Activate/deactivate users
   - Delete users
4. **View Reports**: Access system-wide transaction reports
5. **Review Recommendations**: Approve or reject book recommendations from librarians

### For Librarian

1. **Login** with librarian credentials
2. **Dashboard** shows quick statistics
3. **Manage Books**:
   - Add new books with ISBN, title, author, quantity
   - Update book information
   - Search books
4. **Manage Students**:
   - Add students with unique student code
   - Update student information
   - Search students
5. **Issue Books**:
   - Select student and book
   - System automatically sets due date (14 days)
6. **Return Books**:
   - Select transaction
   - System calculates penalty if late
   - Penalty: ₹5 per day
7. **View Reports**: Filter transactions by date range
8. **Recommend Books**: Submit book recommendations for admin review

## 🔧 Development

### Running in Development Mode

```bash
mvn clean compile
mvn tomcat7:run
```

### Running Tests

```bash
mvn test
```

## 📝 Database Schema

### Main Tables
- **users**: Admin and librarian accounts
- **students**: Student information
- **books**: Book inventory with quantity tracking
- **transactions**: Book issue/return records
- **otp_verification**: OTP codes for email verification
- **book_recommendations**: Book recommendations from librarians

## 🐛 Troubleshooting

### Database Connection Issues
- Verify PostgreSQL is running
- Check database credentials in `config.properties`
- Ensure database `library_db_1` exists

### Email/OTP Issues
- Verify SMTP settings in `config.properties`
- For Gmail, ensure "App Password" is used, not regular password
- Check if "Less secure app access" is enabled (if not using App Password)

### Tomcat Deployment Issues
- Ensure Tomcat is running
- Check Tomcat logs in `logs/catalina.out`
- Verify Java version compatibility

## 📚 API/Servlet Endpoints

### Authentication
- `/login` - User login
- `/logout` - User logout
- `/signup` - Librarian signup
- `/verify-otp` - OTP verification
- `/forgot-password` - Password reset

### Admin
- `/admin/dashboard` - Admin dashboard
- `/admin/manage-users` - User management
- `/admin/reports` - System reports

### Librarian
- `/librarian/dashboard` - Librarian dashboard
- `/librarian/manage-books` - Book management
- `/librarian/manage-students` - Student management
- `/librarian/issue-book` - Issue books
- `/librarian/return-book` - Return books
- `/librarian/reports` - Transaction reports
- `/librarian/recommendations` - Book recommendations

## 🤝 Contributing

This is a college project. For improvements or bug fixes:
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is created for educational purposes.

## 👨‍💻 Author

Created as a college mini/major project for Library Management System.

## 📞 Support

For issues or questions:
- Check the troubleshooting section
- Review the setup guide
- Contact your project supervisor

---

**Note**: This project demonstrates MVC architecture, DAO pattern, session management, role-based access control, and database operations using JDBC. It's designed to be easy to understand and explain during viva/presentations.
