<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>

    <!DOCTYPE html>
    <html>

    <head>
        <title>Reader Signup</title>

        <style>
            a {
                text-decoration: none;
                display: block;
                text-align: center;
                margin-top: 10px;
                color: #4b5320;
            }

            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                margin: 0;
                background-color: #f5f5dc;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            .card {
                background: white;
                padding: 40px;
                border-radius: 10px;
                box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
                width: 350px;
            }

            h2 {
                text-align: center;
                color: #333;
                margin-bottom: 20px;
            }

            input {
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
                background: #2c3e50;
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

            .success {
                color: #2ecc71;
                font-size: 14px;
                text-align: center;
            }
        </style>
    </head>

    <body>

        <div class="card">
            <h2>Student Signup</h2>
            <form action="user?action=signup" method="POST">
                <input type="text" name="firstname" placeholder="First Name" required>
                <input type="text" name="lastname" placeholder="Last Name" required>
                <input type="email" name="email" placeholder="Email Address" required>
                <input type="text" name="phone" placeholder="Phone Number">
                <input type="text" name="address" placeholder="Address">
                <input type="password" name="password" id="myInput" placeholder="Password (min 6 chars)" required minlength="6">
                <label style="display: flex; align-items: center; font-size: 14px; color: #555;">
                    <input type="checkbox" onclick="myFunction()" style="width: auto; margin: 0 5px 0 0;">Show Password
                </label>

                <button type="submit">Sign Up</button>
            </form>

            <p class="error">${error}</p>
            <p class="success">${success}</p>

            <a href="login.jsp">Already have an account? Login</a>
            <a href="index.jsp" style="font-size: 12px; color: #7f8c8d;">Back to Home</a>
        </div>

    </body>

    <script>
        function myFunction() {
            var x = document.getElementById("myInput");
            if (x.type === "password") {
                x.type = "text";
            } else {
                x.type = "password";
            }
        } 
    </script>

    </html>