<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login</title>

 <style>
         .card {
             font-family: Arial;
             background-color: #f5f5dc;
             display: flex;
             justify-content: center;
             align-items: center;
             height: 80vh;
         }

         .card #login{
                  padding-right: 20px;
                  }

         .card  .login-box {
                      background: white;
                      padding: 30px;
                      width: 300px;
                      box-shadow: 0 0 10px gray;
                  }
         .card  h2 { text-align: center; color: #4b5320; }
                  input {
                      width: 100%;
                      padding: 8px;
                      margin: 10px 0;
                  }
         .card  button {
                      width: 100%;
                      padding: 10px;
                      background: #4b5320;
                      color: white;
                      border: none;
                      cursor: pointer;
                      margin-left: 10px;
                  }
         .card   a {text-decoration: none; display: block; text-align: center; margin-top: 10px; color: #4b5320; }
                    }
 </style>
</head>

<jsp:include page="header.jsp" />

<div class="card">
<div class="login-box">
    <form action="user?action=login" id="login" method="POST">
         <h2>Login</h2>
    <input type="text" name="email" placeholder="Email">
    <input type="password" name="password" placeholder="Password">
    <button>Login</button>
    <a href="user_signup.jsp">Not registered? Click here to sign up</a>
    </form>
    <p style="color:red">${error}</p>
</div>
</div>

<jsp:include page="footer.jsp" />
