<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:set var="pageTitle" value="Sign Up - Library Management System" scope="request" />
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>${pageTitle}</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        </head>

        <body class="bg-light">
            <div class="container">
                <div class="row justify-content-center align-items-center min-vh-100">
                    <div class="col-md-6">
                        <div class="card shadow-lg">
                            <div class="card-body p-5">
                                <div class="text-center mb-4">
                                    <i class="fas fa-user-plus fa-4x text-primary mb-3"></i>
                                    <h2 class="fw-bold">Librarian Sign Up</h2>
                                    <p class="text-muted">Create your librarian account</p>
                                </div>

                                <c:if test="${not empty error}">
                                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                        <i class="fas fa-exclamation-circle"></i> ${error}
                                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                    </div>
                                </c:if>

                                <form action="${pageContext.request.contextPath}/signup" method="post">
                                    <div class="row">
                                        <div class="col-md-12 mb-3">
                                            <label for="fullName" class="form-label">
                                                <i class="fas fa-user"></i> Full Name *
                                            </label>
                                            <input type="text" class="form-control" id="fullName" name="fullName"
                                                placeholder="Enter your full name" required>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label for="email" class="form-label">
                                                <i class="fas fa-envelope"></i> Email Address *
                                            </label>
                                            <input type="email" class="form-control" id="email" name="email"
                                                placeholder="your.email@example.com" required>
                                        </div>

                                        <div class="col-md-6 mb-3">
                                            <label for="phone" class="form-label">
                                                <i class="fas fa-phone"></i> Phone Number
                                            </label>
                                            <input type="tel" class="form-control" id="phone" name="phone"
                                                placeholder="9876543210">
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label for="password" class="form-label">
                                                <i class="fas fa-lock"></i> Password *
                                            </label>
                                            <input type="password" class="form-control" id="password" name="password"
                                                placeholder="Minimum 6 characters" required minlength="6">
                                        </div>

                                        <div class="col-md-6 mb-3">
                                            <label for="confirmPassword" class="form-label">
                                                <i class="fas fa-lock"></i> Confirm Password *
                                            </label>
                                            <input type="password" class="form-control" id="confirmPassword"
                                                name="confirmPassword" placeholder="Re-enter password" required>
                                        </div>
                                    </div>

                                    <div class="mb-3 form-check">
                                        <input type="checkbox" class="form-check-input" id="agreeTerms" required>
                                        <label class="form-check-label" for="agreeTerms">
                                            I agree to the terms and conditions
                                        </label>
                                    </div>

                                    <div class="d-grid">
                                        <button type="submit" class="btn btn-primary btn-lg">
                                            <i class="fas fa-paper-plane"></i> Sign Up & Send OTP
                                        </button>
                                    </div>
                                </form>

                                <hr class="my-4">

                                <div class="text-center">
                                    <p class="mb-0">Already have an account?</p>
                                    <a href="${pageContext.request.contextPath}/login"
                                        class="btn btn-outline-primary mt-2">
                                        <i class="fas fa-sign-in-alt"></i> Sign In
                                    </a>
                                </div>

                                <div class="alert alert-info mt-4 mb-0" role="alert">
                                    <small>
                                        <i class="fas fa-info-circle"></i>
                                        After signup, you'll receive an OTP on your email for verification.
                                    </small>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
            <script>
                // Password confirmation validation
                document.querySelector('form').addEventListener('submit', function (e) {
                    const password = document.getElementById('password').value;
                    const confirmPassword = document.getElementById('confirmPassword').value;

                    if (password !== confirmPassword) {
                        e.preventDefault();
                        alert('Passwords do not match!');
                        return false;
                    }
                });
            </script>
        </body>

        </html>