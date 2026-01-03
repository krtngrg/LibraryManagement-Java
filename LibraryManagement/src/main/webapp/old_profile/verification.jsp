<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
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

         #login{
         padding-right: 20px;
         }

         .login-box {
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

<div class="login-box">
    <form action="user?action=verify" id="login" method="POST">
         <h2>Verification</h2>
    <input type="text" name="email" placeholder="Email">
    <input type="text" name="otp" placeholder="OTP">
    <button>Verify</button>
    <a href="user?action=signup">Not registered? Click here to sign up</a>
    </form>
    <p style="color:red">${error}</p>
</div>

</body>
</html>
