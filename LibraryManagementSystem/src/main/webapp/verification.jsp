<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>

    <!DOCTYPE html>
    <html>

    <head>
        <title>Verify Account</title>
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

            .card {
                background: white;
                padding: 40px;
                border-radius: 10px;
                box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
                width: 320px;
                text-align: center;
            }

            h2 {
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
                text-align: center;
                letter-spacing: 2px;
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

            .resend-btn {
                background: none;
                color: #2980b9;
                width: auto;
                padding: 0;
                margin: 10px 0;
                font-size: 14px;
                text-decoration: underline;
            }

            .resend-btn:hover {
                background: none;
                color: #3498db;
            }

            .message {
                font-size: 14px;
                margin: 10px 0;
            }

            .success {
                color: #2ecc71;
            }

            .error {
                color: #e74c3c;
            }

            a {
                color: #7f8c8d;
                text-decoration: none;
                font-size: 12px;
                display: block;
                margin-top: 20px;
            }
        </style>
    </head>

    <body>

        <div class="card">
            <h2>Verify Your Email</h2>
            <p style="color: #666; font-size: 14px;">Enter the OTP sent to your email.</p>

            <form method="POST">
                <input type="email" name="email" placeholder="Enter Email Address" required>
                <input type="text" name="otp" placeholder="Enter OTP Code" required>

                <button type="submit" formaction="user?action=verify">Verify Now</button>

                <div style="margin-top: 15px;">
                    <span style="font-size: 14px; color: #555;">Didn't receive code?</span>
                    <button type="submit" formaction="user?action=resendOtp" class="resend-btn">Resend OTP</button>
                </div>
            </form>

            <p class="message success">${otpsuccess}</p>
            <p class="message error">${otperror}</p>
            <p class="message error">${error}</p>

            <a href="index.jsp">Back to Home</a>
        </div>

    </body>

    </html>