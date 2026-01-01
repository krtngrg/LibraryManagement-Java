<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <title>signup</title>
<link rel="stylesheet" href="./form.css">
<style>
        body {
            font-family: Arial;
            background-color: #f5f5dc;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        #signup{
        padding-right: 20px;
        }

        .signup-box {
            background: white;
            padding: 30px;
            width: 300px;
            box-shadow: 0 0 10px gray;
        }
        h2 { text-align: center; color: #4b5320; }
        input {
            width: 100%;
            padding: 8px;
            margin: 10px 0;
        }
        button {
            width: 100%;
            padding: 10px;
            background: #4b5320;
            color: white;
            border: none;
            cursor: pointer;
            margin-left: 10px;
        }
        a {text-decoration: none; display: block; text-align: center; margin-top: 10px; color: #4b5320; }
</style>
</head>
<body>

<div class="signup-box">
    <h2>signup</h2>
   <form id="signup" action="signup" method="POST">
        <label for="username">Username</label>
        <input type="text" id="name" name="name" required>

        <label for="email">Email</label>
        <input type="email" id="email" name="email" required>

        <label for="password">Password</label>
        <input type="password" id="password" name="password" required minlength="6">

        <button type="submit">Create Account</button>
    </form>
    <p style="color:red">${error}</p>
    <p style="color:green">${success}</p>
    <a href="login.jsp">Already registered? Click here to login</a>

</div>

</body>
</html>
