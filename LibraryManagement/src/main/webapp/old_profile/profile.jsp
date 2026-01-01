<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ page import="dto.UserDto" %>


<!DOCTYPE html>
<html>
<head>
<title>Profile | Library System</title>

<style>
    body { margin:0; font-family:Arial; background:#f5f6dc; }
    .navbar { background:#4f5b1a; color:white; padding:16px 30px;
              display:flex; justify-content:space-between; }
    .navbar a { color:white; text-decoration:none; margin-left:20px; }
    .container { display:flex; justify-content:center; margin-top:50px; }
    .card { background:white; width:420px; padding:30px;
            border-radius:10px; box-shadow:0 4px 10px rgba(0,0,0,.15); }
    h2 { text-align:center; color:#4f5b1a; }
    label { display:block; margin-top:15px; font-weight:bold; }
    input { width:100%; padding:10px; margin-top:5px; border-radius:6px;
            border:1px solid #ccc; }
    input[readonly] { background:#eee; }
    button { width:100%; margin-top:25px; padding:12px;
             background:#4f5b1a; color:white; border:none;
             border-radius:6px; cursor:pointer; }
    footer { position:fixed; bottom:0; width:100%;
             background:#4f5b1a; color:white; text-align:center;
             padding:12px 0; }
</style>
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
<form class="card" method="post" action="profile">
    <h2>My Profile</h2>
<% UserDto presentUser = (UserDto)request.getAttribute("userDto");%>
    <label>First Name</label>
    <input type="text" name="name" value="<%=presentUser.getFirstname()%>" readonly>

    <label>Last Name</label>
    <input type="text" name="name" value="<%=presentUser.getLastname()%>" readonly>

    <label>Email</label>
    <input type="email" value="<%=presentUser.getEmail()%>" readonly>

    <hr>

    <label>Current Password</label>
    <input type="password" name="currentPassword">

    <label>New Password</label>
    <input type="password" name="newPassword">

    <label>Confirm New Password</label>
    <input type="password" name="confirmPassword">

    <button type="submit">Update Profile</button>
</form>
</div>

<footer>
    © 2025 Library Management System
</footer>

</body>
</html>
