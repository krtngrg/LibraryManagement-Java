<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>

<h2><%= session.getAttribute("SessionWelcome")%></h2>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dashboard | Library Management System</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f5f5dc;
            background-image: url('bg.jpg');
            background-size: cover;
            background-position: center;
            background-attachment: fixed;
        }

        .overlay {
            background-color: rgba(245, 245, 220, 0.9);
            min-height: 100vh;
        }

        /* Navbar */
        .navbar {
            background-color: #4b5320;
            padding: 15px 40px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .navbar h1 {
            color: white;
            margin: 0;
        }

        .navbar a {
            color: white;
            text-decoration: none;
            margin-left: 20px;
            font-size: 16px;
        }

        .navbar a:hover {
            text-decoration: underline;
        }

        /* Dashboard Content */
        .dashboard {
            padding: 60px 40px;
        }

        .dashboard h2 {
            text-align: center;
            color: #333;
            margin-bottom: 40px;
        }

        .cards {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
            gap: 30px;
        }

        .card {
            background-color: white;
            border-radius: 8px;
            padding: 30px;
            text-align: center;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            transition: transform 0.2s;
        }

        .card:hover {
            transform: translateY(-5px);
        }

        .card h3 {
            color: #4b5320;
            margin-bottom: 10px;
        }

        .card p {
            color: #555;
            font-size: 14px;
        }

        .card a {
            display: inline-block;
            margin-top: 15px;
            padding: 10px 18px;
            background-color: #4b5320;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            font-size: 14px;
        }

        .card a:hover {
            background-color: #3a4118;
        }

        footer {
            background-color: #4b5320;
            color: white;
            text-align: center;
            padding: 15px;
            position: fixed;
            bottom: 0;
            width: 100%;
        }
    </style>
</head>
<body>

<div class="overlay">

    <!-- Navbar -->
    <div class="navbar">
        <h1>Library System</h1>
        <div>
            <a href="Dashboard.jsp">Dashboard</a>
            <a href="user?action=profile">Profile</a>
            <a href="user?action=logout">Logout</a>
        </div>
    </div>

    <!-- Dashboard Section -->
    <div class="dashboard">
        <div class="cards">

            <div class="card">
                <h3>Manage Books</h3>
                <p>Add, update, or remove books from the library.</p>
                <a href="books">Go</a>
            </div>

            <div class="card">
                <h3>Members</h3>
                <p>View and manage registered library members.</p>
                <a href="members">Go</a>
            </div>

            <div class="card">
                <h3>Issue Books</h3>
                <p>Issue books and track borrowed records.</p>
                <a href="issue">Go</a>
            </div>

            <div class="card">
                <h3>Return Books</h3>
                <p>Manage returned books and fines.</p>
                <a href="return">Go</a>
            </div>

            <div class="card">
                <h3>Reports</h3>
                <p>View library statistics and activity reports.</p>
                <a href="reports">Go</a>
            </div>

        </div>
    </div>

    <!-- Footer -->
    <footer>
        &copy; 2025 Library Management System
    </footer>

</div>

</body>
</html>

