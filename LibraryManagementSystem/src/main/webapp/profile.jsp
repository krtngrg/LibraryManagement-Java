<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ page import="dto.*" %>

            <!DOCTYPE html>
            <html>

            <head>
                <title>Profile | Library System</title>

                <style>
                    body {
                        margin: 0;
                        font-family: Arial;
                        background: #f5f6dc;
                        background-size: cover;
                    }

                    .navbar {
                        background: #4f5b1a;
                        color: white;
                        padding: 16px 30px;
                        display: flex;
                        justify-content: space-between;
                    }

                    .navbar a {
                        color: white;
                        text-decoration: none;
                        margin-left: 20px;
                    }

                    .container {
                        padding-top: 80px;
                        display: flex;
                        justify-content: center;
                        margin-top: 50px;
                    }

                    .card {
                        background: white;
                        width: 420px;
                        padding: 30px;
                        border-radius: 10px;
                        box-shadow: 0 4px 10px rgba(0, 0, 0, .15);
                    }

                    h2 {
                        text-align: center;
                        color: #4f5b1a;
                    }

                    .info {
                        margin: 15px 0;
                    }

                    .info strong {
                        display: block;
                        color: #4f5b1a;
                        margin-bottom: 5px;
                    }

                    input {
                        width: 100%;
                        padding: 10px;
                        margin-top: 8px;
                        border-radius: 6px;
                        border: 1px solid #ccc;
                    }

                    button {
                        width: 100%;
                        margin-top: 20px;
                        padding: 12px;
                        background: #4f5b1a;
                        color: white;
                        border: none;
                        border-radius: 6px;
                        cursor: pointer;
                    }

                    .link {
                        text-align: center;
                        margin-top: 15px;
                        color: #4f5b1a;
                        cursor: pointer;
                        font-weight: bold;
                    }

                    footer {
                        position: fixed;
                        bottom: 0;
                        width: 100%;
                        background: #4f5b1a;
                        color: white;
                        text-align: center;
                        padding: 12px 0;
                    }

                    .hidden {
                        display: none !important;
                    }

                    
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

                    
                    .modal-overlay {
                        position: fixed;
                        top: 0;
                        left: 0;
                        width: 100%;
                        height: 100%;
                        background: rgba(0, 0, 0, 0.5);
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        z-index: 1000;
                    }

                    .modal-content-custom {
                        background: white;
                        border-radius: 15px;
                        padding: 30px;
                        width: 100%;
                        max-width: 400px;
                        box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
                        position: relative;
                        animation: slideDown 0.3s ease-out;
                    }

                    @keyframes slideDown {
                        from {
                            transform: translateY(-50px);
                            opacity: 0;
                        }

                        to {
                            transform: translateY(0);
                            opacity: 1;
                        }
                    }

                    .modal-close {
                        position: absolute;
                        top: 10px;
                        right: 10px;
                        font-size: 28px;
                        font-weight: bold;
                        color: #666;
                        cursor: pointer;
                        border: none;
                        background: none;
                        padding: 10px;
                        line-height: 1;
                        transition: color 0.2s;
                        z-index: 1010;
                    }

                    .modal-close:hover {
                        color: #000;
                    }

                    #profilePicInput {
                        display: none;
                    }
                </style>

            </head>

            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">

            <c:choose>
                <c:when test="${sessionScope.role == 'ADMIN'}">
                    <jsp:include page="dash_header.jsp" />
                </c:when>
                <c:otherwise>
                    <jsp:include page="sidebar.jsp" />
                </c:otherwise>
            </c:choose>

            <c:if test="${sessionScope.role == 'ADMIN'}">
                <style>
                    body {
                        background: #f5f5dc url("bg.jpg") fixed;
                        background-size: cover;
                    }
                </style>
            </c:if>

            <div class="container">
                <div class="card">

                    <% UserDto presentUser=(UserDto) request.getAttribute("userDto"); if (presentUser==null)
                        presentUser=(UserDto) session.getAttribute("user"); if (presentUser==null) {
                        response.sendRedirect("login.jsp"); return; } String pPath=presentUser.getProfilePicPath();
                        String pDisplay=(pPath !=null) ? pPath : "./images/profile_icon.jpg" ; %>
                        <h2>My Profile</h2>
                        <form action="user?action=profilepic" method="post" enctype="multipart/form-data">
                            <div class="profile-pic-container"
                                onclick="document.getElementById('profilePicInput').click();">
                                <img id="profilePicPreview" src="<%= pDisplay %>" alt="Profile Picture">
                                <span class="plus-icon">+</span>
                            </div>

                            <input type="file" name="profilePic" id="profilePicInput" accept="image/*"
                                onchange="previewProfilePic(event)">


                            
                            <div class="info">
                                <strong>First Name</strong>
                                <span>
                                    <%= presentUser.getFirstname() %>
                                </span>
                            </div>

                            <div class="info">
                                <strong>Last Name</strong>
                                <span>
                                    <%= presentUser.getLastname() %>
                                </span>
                            </div>

                            <div class="info">
                                <strong>Email</strong>
                                <span>
                                    <%= presentUser.getEmail() %>
                                </span>
                            </div>

                            <div class="link" onclick="startChangePassword()">
                                Change Password
                            </div>

                            <button>Edit</button>

                        </form>

                        
                        <div id="passwordResetModal" class="modal-overlay hidden" style="display: none !important;">
                            <div class="modal-content-custom">
                                <button type="button" class="modal-close" onclick="closePasswordModal()"
                                    aria-label="Close">&times;</button>
                                <h3 class="mb-3 text-center">Change Password</h3>
                                <hr>

                                
                                <div id="step1-otp">
                                    <p class="text-muted small">Verification code will be sent to your email.</p>
                                    <button type="button" class="btn btn-sm btn-outline-secondary mb-3 w-100"
                                        onclick="sendOTP()">Send OTP</button>

                                    <div id="otp-input-container" class="form-group mb-3">
                                        <label class="form-label">Enter OTP</label>
                                        <input type="text" id="resetOtpInput" class="form-control"
                                            placeholder="6-digit code">
                                    </div>
                                    <button type="button" class="btn btn-primary w-100" onclick="verifyOTP()">Verify
                                        OTP</button>
                                </div>

                                
                                <div id="step2-password" class="hidden">
                                    <div class="form-group mb-3">
                                        <label class="form-label">New Password</label>
                                        <input type="password" id="newPassword" class="form-control">
                                    </div>

                                    <div class="form-group mb-3">
                                        <label class="form-label">Confirm Password</label>
                                        <input type="password" id="confirmPassword" class="form-control">
                                    </div>

                                    <button type="button" class="btn btn-success w-100"
                                        onclick="changePassword()">Update Password</button>
                                </div>

                                <p id="resetMessage" class="mt-3 small text-center"></p>
                            </div>
                        </div>

                </div>
            </div>

            <% String safeEmail=(presentUser !=null && presentUser.getEmail() !=null) ? presentUser.getEmail() : "" ; %>
                <script>
                    const userEmail = "<%= safeEmail %>";
                    console.log("JS Initialization: userEmail defined as [" + userEmail + "]");

                    function startChangePassword() {
                        const modal = document.getElementById("passwordResetModal");
                        modal.style.setProperty('display', 'flex', 'important');
                        modal.classList.remove("hidden");
                    }

                    function closePasswordModal() {
                        const modal = document.getElementById("passwordResetModal");
                        modal.style.setProperty('display', 'none', 'important');
                        modal.classList.add("hidden");
                        
                        document.getElementById("step1-otp").classList.remove("hidden");
                        document.getElementById("step2-password").classList.add("hidden");
                        document.getElementById("resetMessage").innerText = "";
                        document.getElementById("resetOtpInput").value = "";
                        document.getElementById("newPassword").value = "";
                        document.getElementById("confirmPassword").value = "";
                    }

                    
                    window.onclick = function (event) {
                        const modal = document.getElementById("passwordResetModal");
                        if (event.target == modal) {
                            closePasswordModal();
                        }
                    }

                    async function sendOTP() {
                        const email = userEmail;
                        const msg = document.getElementById("resetMessage");
                        msg.innerText = "Sending...";
                        msg.className = "mt-2 small text-center text-primary";

                        try {
                            const params = new URLSearchParams();
                            params.append('action', 'sendResetOtp');
                            params.append('email', email);

                            const res = await fetch('user', {
                                method: 'POST',
                                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                                body: params
                            });
                            const text = await res.text();
                            msg.innerText = text;
                            msg.className = text.includes("Successfully") ? "mt-2 small text-center text-success" : "mt-2 small text-center text-danger";
                        } catch (e) {
                            msg.innerText = "Error connecting to server";
                            msg.className = "mt-2 small text-center text-danger";
                        }
                    }

                    async function verifyOTP() {
                        const email = userEmail;
                        const otp = document.getElementById("resetOtpInput").value.trim();
                        const msg = document.getElementById("resetMessage");

                        if (!otp) {
                            msg.innerText = "Please enter OTP";
                            return;
                        }

                        msg.innerText = "Verifying...";
                        msg.className = "mt-2 small text-center text-primary";

                        try {
                            const params = new URLSearchParams();
                            params.append('action', 'verifyResetOtp');
                            params.append('email', email);
                            params.append('otp', otp);

                            const res = await fetch('user', {
                                method: 'POST',
                                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                                body: params
                            });
                            const text = (await res.text()).trim();

                            if (text === "Verified") {
                                document.getElementById("step1-otp").classList.add("hidden");
                                document.getElementById("step2-password").classList.remove("hidden");
                                msg.innerText = "Identity Verified. Set your new password.";
                                msg.className = "mt-2 small text-center text-success";
                            } else {
                                msg.innerText = text;
                                msg.className = "mt-2 small text-center text-danger";
                            }
                        } catch (e) {
                            msg.innerText = "Error verifying OTP";
                        }
                    }

                    async function changePassword() {
                        const email = userEmail;
                        const newPass = document.getElementById("newPassword").value;
                        const confirmPass = document.getElementById("confirmPassword").value;
                        const msg = document.getElementById("resetMessage");

                        if (!newPass) {
                            msg.innerText = "Enter new password";
                            msg.className = "mt-2 small text-center text-danger";
                            return;
                        }

                        if (newPass !== confirmPass) {
                            msg.innerText = "Passwords do not match";
                            msg.className = "mt-2 small text-center text-danger";
                            return;
                        }

                        try {
                            const params = new URLSearchParams();
                            params.append('action', 'changePassword');
                            params.append('email', email);
                            params.append('newPassword', newPass);

                            const res = await fetch('user', {
                                method: 'POST',
                                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                                body: params
                            });
                            const text = await res.text();
                            msg.innerText = text;
                            if (text.includes("Successfully")) {
                                msg.className = "mt-2 small text-center text-success";
                                setTimeout(() => { location.reload(); }, 2000);
                            } else {
                                msg.className = "mt-2 small text-center text-danger";
                            }
                        } catch (e) {
                            msg.innerText = "Error changing password";
                        }
                    }

                    function previewProfilePic(event) {
                        const reader = new FileReader();
                        reader.onload = function () {
                            const output = document.getElementById('profilePicPreview');
                            output.src = reader.result;
                            
                            const plusIcon = document.querySelector('.profile-pic-container .plus-icon');
                            plusIcon.style.display = 'none';
                        };
                        reader.readAsDataURL(event.target.files[0]);
                    }
                </script>