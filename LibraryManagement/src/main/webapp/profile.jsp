<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ page import="dto.*" %>

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

      /* Profile picture upload container */
        .profile-pic-container {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            background: #eee;
            position: relative;
            margin: 0 auto 20px auto;
            cursor: pointer;
            display: flex;
            justify-content: center;
            align-items: center;
            overflow: hidden;
        }

        .profile-pic-container img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            display: block;
        }

        .profile-pic-container .plus-icon {
            font-size: 40px;
            color: #4f5b1a;
            position: absolute;
        }

        /* Hide the real file input */
        #profilePicInput {
            display: none;
        }
</style>

</head>

<jsp:include page="dash_header.jsp" />

<div class="container">
    <div class="card">

        <% UserDto presentUser = (UserDto)request.getAttribute("userDto");
        String defaultimg="./images/ben.jpg"; %>
        <h2>My Profile</h2>
<form action="user?action=profilepic" method="post" enctype="multipart/form-data">

        <div class="profile-pic-container" onclick="document.getElementById('profilePicInput').click();">
            <img id="profilePicPreview" src="<%=presentUser.getProfilePicPath() != null ? presentUser.getProfilePicPath() : "./images/profile_icon.jpg"%>" alt="Profile Picture">
            <span class="plus-icon">+</span>
        </div>

        <input type="file" name="profilePic" id="profilePicInput" accept="image/*" onchange="previewProfilePic(event)">


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

        <button>Save</button>

          </form>

        <!-- OTP Section -->
        <form action="forgot-password" method="post" id="otpSection" class="hidden" >
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

<jsp:include page="footer.jsp" />

<script>

  function showOtpSection() {
        document.getElementById("otpSection").classList.remove("hidden");
    }

function previewProfilePic(event) {
    const reader = new FileReader();
    reader.onload = function(){
        const output = document.getElementById('profilePicPreview');
        output.src = reader.result;
        // hide the plus icon once image is selected
        const plusIcon = document.querySelector('.profile-pic-container .plus-icon');
        plusIcon.style.display = 'none';
    };
    reader.readAsDataURL(event.target.files[0]);
}
</script>