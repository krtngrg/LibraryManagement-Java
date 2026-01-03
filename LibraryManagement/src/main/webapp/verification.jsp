<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <title>Verification</title>
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
            margin-top: 10px;
        }

        button[formaction="user?action=resendOtp"]{
            width: fit-content;
            color: black;
        }

        .resend-btn {
            background: #777;
        }

        a {
            text-decoration: none;
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

<div class="login-box">

    <!-- VERIFY OTP FORM -->
    <form method="POST">
        <h2>Verification</h2>
        <input type="text" name="email" placeholder="Email" required>
        <input type="text" name="otp" placeholder="OTP" >

          Didn't receive OTP?
          <button type="submit"
                formaction="user?action=resendOtp"
                style="background:none;border:none;color:blue;cursor:pointer;padding:0;margin-bottom:10px;">
                Send OTP again
            </button>
            <p style="color:green">${otpsuccess}</p>
            <p style="color:red">${otperror}</p>
        <button type="submit" formaction="user?action=verify">Verify</button>


    </form>
    <br>
        <p style="color:red">${error}</p>
    <br>

    <a href="user?action=signup">Not registered? Click here to sign up</a>



</div>
<a id="home" href="index.jsp">&#11013; Back to Home</a>
</body>
</html>
