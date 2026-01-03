<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <title>Reader Signup</title>

    <style>
    body{

          a {
                text-decoration: none;
                display: block;
                text-align: center;
                margin-top: 10px;
                color: #4b5320;
            }

      margin: 0;
                font-family: Arial, sans-serif;
                background-color: #f5f5dc;
                background-image: url("./images/bg.jpg");
                background-size: cover;
                background-position: center;
                background-attachment: fixed;
    }
       .card{
            font-family: Arial;
            background-color: #f5f5dc;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 90vh;
        }

        .signup-box #signup {
            padding-right: 20px;
        }

        .signup-box {
            background: white;
            padding: 30px;
            width: 320px;
            box-shadow: 0 0 10px gray;
        }

        .signup-box h2 {
            text-align: center;
            color: #4b5320;
        }

        .signup-box input, .signup-box textarea {
            width: 100%;
            padding: 8px;
            margin: 10px 0;
        }

        .signup-box textarea {
            resize: none;
            height: 60px;
        }

        .signup-box button {
            width: 100%;
            padding: 10px;
            background: #4b5320;
            color: white;
            border: none;
            cursor: pointer;
            margin-left: 10px;
        }

        .signup-box a {
            text-decoration: none;
            display: block;
            text-align: center;
            margin-top: 10px;
            color: #4b5320;
        }

                #home{
                position:absolute;
                bottom:50px;
                left:50px;
                }


    </style>
</head>

<body>
<div class="card">
<div class="signup-box">
    <h2>Librarian Signup</h2>

    <form id="signup" action="user?action=signup" method="POST">
        <label>FirstName</label>
        <input type="text" name="firstname" required>

         <label>LastName</label>
         <input type="text" name="lastname" required>

        <label>Email</label>
        <input type="email" name="email" required>

        <label>Phone</label>
        <input type="text" name="phone">

        <label>Address</label>
        <input type="text" name="address">

        <label>Password</label>
        <input type="password" name="password" required minlength="6">

        <button type="submit">Create Account</button>
    </form>

    <p style="color:red">${error}</p>
    <p style="color:green">${success}</p>

    <!-- Switch between signup pages -->
     <!-- <a href="signup.jsp">Signup as Admin</a>  -->
    <a href="login.jsp">Already registered? Login</a>
</div>
</div>

<a id="home" href="index.jsp">&#11013; Back to Home</a>
</body>
</html>




