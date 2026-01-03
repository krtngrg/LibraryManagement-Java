<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <style>
        body {
            font-family: Arial;
            background-color: #f5f5dc;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .login-box {
            background: white;
            padding: 30px;
            width: 320px;
            box-shadow: 0 0 10px gray;
        }

        form{
          padding-right: 20px;
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

        a {
            text-decoration: none;
            display: block;
            text-align: center;
            margin-top: 10px;
            color: #4b5320;
        }

        /* Simple popup */
        .popup {
            position: fixed;
            top: 0; left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0,0,0,0.5);
            display: none;
            justify-content: center;
            align-items: center;
        }

        .popup-content {
            background: white;
            padding: 20px;
            text-align: center;
            width: 300px;
            box-shadow: 0 0 10px gray;
        }

        #home{
        position:absolute;
        bottom:50px;
        left:50px;
        }
    </style>
</head>

<body>



<div class="login-box">
    <form action="user?action=login" method="POST">
        <h2>Login</h2>
      <input type="text" name="email" placeholder="Email">
          <input type="password" name="password" placeholder="Password">
          <br>
          <br>
       <button>Login</button>
           <a href="user_signup.jsp">Not registered? Click here to sign up</a>
    </form>

    <p style="color:red">${error}</p>
</div>

<!-- NOT VERIFIED POPUP -->

<div class="popup" id="notVerifiedPopup">
    <div class="popup-content">
        <h3>Email Not Verified</h3>
        <p>Please verify your email before logging in.</p>
        <a href="user?action=verify">Go to Verification</a>
        <br><br>
        <button onclick="closePopup()">Close</button>
    </div>
</div>




<!-- SCRIPT -->
<script>
    function closePopup() {
        document.getElementById("notVerifiedPopup").style.display = "none";
    }

    // Show popup if backend sets notVerified=true
    <% if (request.getAttribute("notVerified") != null) { %>
        document.getElementById("notVerifiedPopup").style.display = "flex";
    <% } %>
</script>

<a id="home" href="index.jsp">&#11013; Back to Home</a>
</body>
</html>
