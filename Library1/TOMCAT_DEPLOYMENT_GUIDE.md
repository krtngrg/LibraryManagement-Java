# Running Library Management System with Apache Tomcat

This guide will help you deploy and run the Library Management System on Apache Tomcat.

## Prerequisites

Before you begin, ensure you have:

1. ✅ **Java JDK 11 or higher** installed
   - Check: `java -version`
   
2. ✅ **Apache Maven** installed
   - Check: `mvn -version`
   
3. ✅ **Apache Tomcat 9.x or 10.x** downloaded
   - Download from: https://tomcat.apache.org/download-90.cgi
   
4. ✅ **PostgreSQL** installed and running
   - Check: `psql --version`

## Step-by-Step Deployment Guide

### Step 1: Setup Database

1. **Create the database** (if not already done):
   ```bash
   # Open pgAdmin or SQL Shell (psql) and run:
   CREATE DATABASE library_db_1;
   ```

2. **Run the database setup script**:
   ```bash
   # Option 1: Using the batch script (Windows)
   cd c:\Users\krtng\OneDrive\Desktop\actual_project\Library1
   setup-database.bat
   
   # Option 2: Manual setup via pgAdmin
   # - Open pgAdmin
   # - Connect to library_db_1
   # - Run database/schema.sql
   # - Run database/sample_data.sql
   ```

### Step 2: Configure Application

1. **Create configuration file**:
   ```bash
   cd c:\Users\krtng\OneDrive\Desktop\actual_project\Library1
   copy src\main\resources\config.properties.example src\main\resources\config.properties
   ```

2. **Edit `config.properties`** with your actual credentials:
   ```properties
   # Database Configuration
   db.url=jdbc:postgresql://localhost:5432/library_db_1
   db.username=postgres
   db.password=root
   
   # Email Configuration (for OTP - optional for now)
   email.smtp.host=smtp.gmail.com
   email.smtp.port=587
   email.smtp.username=your_email@gmail.com
   email.smtp.password=your_app_password
   ```

### Step 3: Build the Project

1. **Navigate to project directory**:
   ```bash
   cd c:\Users\krtng\OneDrive\Desktop\actual_project\Library1
   ```

2. **Clean and build the project**:
   ```bash
   mvn clean package
   ```

3. **Verify the WAR file is created**:
   - Check: `target\library-management-system.war`
   - This file should be created after successful build

### Step 4: Deploy to Tomcat

#### Method 1: Manual Deployment (Recommended for Development)

1. **Locate your Tomcat installation**:
   - Example: `C:\Program Files\Apache Software Foundation\Tomcat 9.0`

2. **Copy WAR file to Tomcat**:
   ```bash
   # Copy the WAR file to Tomcat's webapps folder
   copy target\library-management-system.war "C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\"
   ```

3. **Start Tomcat**:
   ```bash
   # Navigate to Tomcat bin folder
   cd "C:\Program Files\Apache Software Foundation\Tomcat 9.0\bin"
   
   # Start Tomcat
   startup.bat
   ```

4. **Wait for deployment**:
   - Tomcat will automatically extract the WAR file
   - Watch the console for deployment messages
   - Look for: "Deployment of web application archive ... has finished"

#### Method 2: Using Tomcat Manager (If Configured)

1. **Access Tomcat Manager**:
   - URL: http://localhost:8080/manager/html
   - Login with Tomcat admin credentials

2. **Deploy WAR file**:
   - Scroll to "WAR file to deploy"
   - Click "Choose File" and select `target\library-management-system.war`
   - Click "Deploy"

#### Method 3: Using Maven Tomcat Plugin

1. **Add to `pom.xml`** (if not already present):
   ```xml
   <build>
       <plugins>
           <plugin>
               <groupId>org.apache.tomcat.maven</groupId>
               <artifactId>tomcat7-maven-plugin</artifactId>
               <version>2.2</version>
               <configuration>
                   <url>http://localhost:8080/manager/text</url>
                   <server>TomcatServer</server>
                   <path>/library-management-system</path>
               </configuration>
           </plugin>
       </plugins>
   </build>
   ```

2. **Deploy using Maven**:
   ```bash
   mvn tomcat7:deploy
   # or to redeploy
   mvn tomcat7:redeploy
   ```

### Step 5: Access the Application

1. **Open your web browser**

2. **Navigate to**:
   ```
   http://localhost:8080/library-management-system/
   ```

3. **Login with default credentials**:
   
   **Admin Account:**
   - Email: `admin@library.com`
   - Password: `password123`
   
   **Librarian Account:**
   - Email: `john.doe@library.com`
   - Password: `password123`

## Quick Start Commands (All-in-One)

```bash
# 1. Navigate to project
cd c:\Users\krtng\OneDrive\Desktop\actual_project\Library1

# 2. Build project
mvn clean package

# 3. Copy to Tomcat (adjust path to your Tomcat installation)
copy target\library-management-system.war "C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\"

# 4. Start Tomcat (if not running)
cd "C:\Program Files\Apache Software Foundation\Tomcat 9.0\bin"
startup.bat

# 5. Access application
# Open browser: http://localhost:8080/library-management-system/
```

## Troubleshooting

### Issue: Port 8080 already in use

**Solution:**
```bash
# Option 1: Stop the process using port 8080
netstat -ano | findstr :8080
taskkill /PID <process_id> /F

# Option 2: Change Tomcat port
# Edit: TOMCAT_HOME/conf/server.xml
# Change: <Connector port="8080" to <Connector port="8081"
```

### Issue: Application not deploying

**Check:**
1. **Tomcat logs**:
   - Location: `TOMCAT_HOME\logs\catalina.out` or `catalina.YYYY-MM-DD.log`
   - Look for error messages

2. **Application logs**:
   - Check console output for exceptions

3. **WAR file**:
   - Ensure `target\library-management-system.war` exists
   - Check file size (should be several MB)

### Issue: Database connection error

**Check:**
1. PostgreSQL is running
2. Database `library_db_1` exists
3. Credentials in `config.properties` are correct
4. PostgreSQL is accepting connections on port 5432

**Test connection:**
```bash
psql -U postgres -d library_db_1
```

### Issue: 404 Not Found

**Solutions:**
1. **Wait for deployment**: Tomcat may still be deploying
2. **Check URL**: Ensure you're using the correct context path
3. **Check deployment**: Look in `TOMCAT_HOME\webapps\` for the extracted folder
4. **Restart Tomcat**: Sometimes a restart helps

### Issue: Servlets not found (404 on servlet URLs)

**Note:** The servlet classes haven't been created yet. You'll see 404 errors when accessing servlet URLs until you implement them. For now, you can only access the JSP pages directly:
- http://localhost:8080/library-management-system/login.jsp
- http://localhost:8080/library-management-system/index.jsp

## Development Workflow

### Making Changes and Redeploying

1. **Make your code changes**

2. **Rebuild the project**:
   ```bash
   mvn clean package
   ```

3. **Stop Tomcat**:
   ```bash
   cd "C:\Program Files\Apache Software Foundation\Tomcat 9.0\bin"
   shutdown.bat
   ```

4. **Delete old deployment**:
   ```bash
   # Delete the WAR and extracted folder
   del "C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\library-management-system.war"
   rmdir /s /q "C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\library-management-system"
   ```

5. **Copy new WAR file**:
   ```bash
   copy target\library-management-system.war "C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\"
   ```

6. **Start Tomcat**:
   ```bash
   startup.bat
   ```

### Hot Reload (Development Mode)

For faster development, use Maven with Tomcat plugin:

```bash
# Run in development mode
mvn tomcat7:run

# Access at: http://localhost:8080/library-management-system/
```

## Viewing Logs

### Tomcat Logs
```bash
# Navigate to logs folder
cd "C:\Program Files\Apache Software Foundation\Tomcat 9.0\logs"

# View latest log
type catalina.YYYY-MM-DD.log
```

### Application Logs
- Check Tomcat console window for System.out.println() messages
- Check `catalina.out` for application errors

## Stopping the Application

```bash
# Navigate to Tomcat bin
cd "C:\Program Files\Apache Software Foundation\Tomcat 9.0\bin"

# Stop Tomcat
shutdown.bat
```

## Next Steps

After successful deployment:

1. ✅ Test the JSP pages directly
2. ⏳ Implement DAO classes for database operations
3. ⏳ Implement Servlet controllers
4. ⏳ Implement Security filters
5. ✅ Test complete functionality

## Important Notes

⚠️ **Current Limitations:**
- Servlet classes are not yet implemented
- Security filters are not yet implemented
- You can only access JSP pages directly for now
- Full functionality requires implementing the controller layer

✅ **What Works Now:**
- JSP pages render correctly
- Database schema is ready
- Model classes are complete
- Utility classes are ready
- UI/UX is fully functional

## Need Help?

If you encounter issues:
1. Check Tomcat logs in `TOMCAT_HOME\logs\`
2. Verify database connection
3. Ensure all prerequisites are installed
4. Check that ports 8080 and 5432 are not blocked

---

**Ready to proceed with implementing the servlets and DAO classes?**
