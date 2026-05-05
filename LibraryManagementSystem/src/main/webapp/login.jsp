<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
    <%@ page import="javax.servlet.http.Cookie" %>

        <!DOCTYPE html>
        <html>

        <head>
            <title>Login</title>
            <style>
                body {
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    margin: 0;
                    background-color: #f5f5dc;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    height: 100vh;
                }

                .login-box {
                    background: white;
                    padding: 40px;
                    border-radius: 10px;
                    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
                    width: 320px;
                }

                h2 {
                    text-align: center;
                    color: #333;
                    margin-bottom: 20px;
                }

                input[type="text"],
                input[type="password"] {
                    width: 100%;
                    padding: 10px;
                    margin: 10px 0;
                    border: 1px solid #ddd;
                    border-radius: 5px;
                    box-sizing: border-box;
                }

                button {
                    width: 100%;
                    padding: 12px;
                    background: #34495e;
                    color: white;
                    border: none;
                    border-radius: 5px;
                    cursor: pointer;
                    font-size: 16px;
                    margin-top: 10px;
                }

                button:hover {
                    background: #4b5320;
                }

                a {
                    display: block;
                    text-align: center;
                    margin-top: 15px;
                    color: #2c3e50;
                    text-decoration: none;
                    font-size: 14px;
                }

                a:hover {
                    text-decoration: underline;
                }

                .error {
                    color: #e74c3c;
                    font-size: 14px;
                    text-align: center;
                }

                .popup {
                    position: fixed;
                    top: 0;
                    left: 0;
                    width: 100%;
                    height: 100%;
                    background: rgba(0, 0, 0, 0.5);
                    display: none;
                    justify-content: center;
                    align-items: center;
                }

                .popup-content {
                    background: white;
                    padding: 30px;
                    border-radius: 8px;
                    text-align: center;
                    width: 300px;
                }

                .popup button {
                    background: #e74c3c;
                    width: auto;
                    padding: 8px 20px;
                }
            </style>
        </head>

        <body>

            <div class="login-box">
                <h2>Staff Login</h2>
                <form action="user?action=login" method="POST">
                    <input type="text" name="email" placeholder="Email Address" value="<%
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().equals(" email")) { out.print(c.getValue()); } } } %>" required/>
                    <input type="password" name="password" placeholder="Password" required>
                    <div style="margin: 10px 0; font-size: 14px; color: #555;">
                        <input type="checkbox" name="remember" style="width: auto; margin-right: 5px;" /> Remember Me
                    </div>
                    <button type="submit">Login</button>
                </form>

                <p class="error">${error}</p>
                <a href="signup.jsp">Don't have an account? Sign Up</a>
                <a href="index.jsp" style="font-size: 12px; color: #7f8c8d;">Back to Home</a>
            </div>

            
            <div class="popup" id="notVerifiedPopup">
                <div class="popup-content">
                    <h3 style="color: #e74c3c; margin-top: 0;">Email Not Verified</h3>
                    <p>Please verify your email before logging in.</p>
                    <a href="user?action=verify" style="color: #27ae60; font-weight: bold;">Go to Verification</a>
                    <br>
                    <button onclick="closePopup()">Close</button>
                </div>
            </div>

            <script>
                function closePopup() {
                    document.getElementById("notVerifiedPopup").style.display = "none";
                }
    <% if (request.getAttribute("notVerified") != null) { %>
                    document.getElementById("notVerifiedPopup").style.display = "flex";
    <% } %>
            </script>

        </body>

        </html>