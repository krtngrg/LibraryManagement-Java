<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ page import="dto.UserDto" %>

<!DOCTYPE html>
<html>
<head>
<title>Profile | Library System</title>

<style>
    body { margin:0; font-family:Arial; background:#f5f6dc; }

    .navbar {
        background:#4f5b1a;
        color:white;
        padding:16px 30px;
        display:flex;
        justify-content:space-between;
    }

    .navbar a { color:white; text-decoration:none; margin-left:20px; }

    .container {
        display:flex;
        justify-content:center;
        margin-top:50px;
    }

    .card {
        background:white;
        width:420px;
        padding:30px;
        border-radius:10px;
        box-shadow:0 4px 10px rgba(0,0,0,.15);
    }

    h2 { text-align:center; color:#4f5b1a; }

    .info {
        margin:15px 0;
    }

    .info strong {
        display:block;
        color:#4f5b1a;
        margin-bottom:5px;
    }

    input {
        width:100%;
        padding:10px;
        margin-top:8px;
        border-radius:6px;
        border:1px solid #ccc;
    }

    button {
        width:100%;
        margin-top:20px;
        padding:12px;
        background:#4f5b1a;
        color:white;
        border:none;
        border-radius:6px;
        cursor:pointer;
    }

    .link {
        text-align:center;
        margin-top:15px;
        color:#4f5b1a;
        cursor:pointer;
        font-weight:bold;
    }

    footer {
        position:fixed;
        bottom:0;
        width:100%;
        background:#4f5b1a;
        color:white;
        text-align:center;
        padding:12px 0;
    }

    .hidden {
        display:none;
    }
</style>

<script>
    function showOtpSection() {
        document.getElementById("otpSection").classList.remove("hidden");
    }
</script>
</head>

<body>

<div class="navbar">
    <strong>Library System</strong>
    <div>
        <a href="Dashboard.jsp">Dashboard</a>
        <a href="profile?id=${id}">Profile</a>
        <a href="logout">Logout</a>
    </div>
</div>

<div class="container">
    <div class="card">

        <h2>My Profile</h2>

        <% UserDto presentUser = (UserDto)request.getAttribute("userDto"); %>

        <!-- Profile Info Card -->
        <div class="info">
            <strong>First Name</strong>
            <span><%= presentUser.getFirstname() %></span>
        </div>

        <div class="info">
            <strong>Last Name</strong>
            <span><%= presentUser.getLastname() %></span>
        </div>

        <div class="info">
            <strong>Email</strong>
            <span><%= presentUser.getEmail() %></span>
        </div>

        <div class="link" onclick="showOtpSection()">
            Forgot Password?
        </div>

        <!-- OTP Section -->
        <form action="forgot-password" method="post" id="otpSection" class="hidden">
            <hr>

            <label>Enter OTP</label>
            <input type="text" name="otp" placeholder="Enter OTP">

            <label>New Password</label>
            <input type="password" name="newPassword">

            <label>Confirm Password</label>
            <input type="password" name="confirmPassword">

            <button type="submit">Reset Password</button>
        </form>

    </div>
</div>

<footer>
    © 2025 Library Management System
</footer>

</body>
</html>
