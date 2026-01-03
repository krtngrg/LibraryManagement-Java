<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>

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

