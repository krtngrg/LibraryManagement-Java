<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:set var="pageTitle" value="Login - Library Management System" scope="request" />
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>${pageTitle}</title>

            <!-- Bootstrap CSS -->
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

            <!-- Font Awesome Icons -->
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

            <!-- Custom CSS -->
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        </head>

        <body class="bg-light">
            <div class="container">
                <div class="row justify-content-center align-items-center min-vh-100">
                    <div class="col-md-5">
                        <div class="card shadow-lg">
                            <div class="card-body p-5">
                                <!-- Logo and Title -->
                                <div class="text-center mb-4">
                                    <i class="fas fa-book-reader fa-4x text-primary mb-3"></i>
                                    <h2 class="fw-bold">Library Management System</h2>
                                    <p class="text-muted">Sign in to continue</p>
                                </div>

                                <!-- Error Message -->
                                <c:if test="${not empty error}">
                                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                        <i class="fas fa-exclamation-circle"></i> ${error}
                                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                    </div>
                                </c:if>

                                <!-- Success Message -->
                                <c:if test="${not empty success}">
                                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                                        <i class="fas fa-check-circle"></i> ${success}
                                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                    </div>
                                </c:if>

                                <!-- Login Form -->
                                <form action="${pageContext.request.contextPath}/login" method="post">
                                    <div class="mb-3">
                                        <label for="email" class="form-label">
                                            <i class="fas fa-envelope"></i> Email Address
                                        </label>
                                        <input type="email" class="form-control" id="email" name="email"
                                            placeholder="Enter your email" required autofocus>
                                    </div>

                                    <div class="mb-3">
                                        <label for="password" class="form-label">
                                            <i class="fas fa-lock"></i> Password
                                        </label>
                                        <input type="password" class="form-control" id="password" name="password"
                                            placeholder="Enter your password" required>
                                    </div>

                                    <div class="mb-3 form-check">
                                        <input type="checkbox" class="form-check-input" id="rememberMe"
                                            name="rememberMe">
                                        <label class="form-check-label" for="rememberMe">
                                            Remember me
                                        </label>
                                    </div>

                                    <div class="d-grid">
                                        <button type="submit" class="btn btn-primary btn-lg">
                                            <i class="fas fa-sign-in-alt"></i> Sign In
                                        </button>
                                    </div>
                                </form>

                                <!-- Additional Links -->
                                <div class="text-center mt-4">
                                    <a href="${pageContext.request.contextPath}/forgot-password"
                                        class="text-decoration-none">
                                        <i class="fas fa-key"></i> Forgot Password?
                                    </a>
                                </div>

                                <hr class="my-4">

                                <div class="text-center">
                                    <p class="mb-0">Don't have an account?</p>
                                    <a href="${pageContext.request.contextPath}/signup"
                                        class="btn btn-outline-primary mt-2">
                                        <i class="fas fa-user-plus"></i> Sign Up as Librarian
                                    </a>
                                </div>

                                <!-- Demo Credentials -->
                                <div class="alert alert-info mt-4 mb-0" role="alert">
                                    <strong><i class="fas fa-info-circle"></i> Demo Credentials:</strong><br>
                                    <small>
                                        <strong>Admin:</strong> admin@library.com / password123<br>
                                        <strong>Librarian:</strong> john.doe@library.com / password123
                                    </small>
                                </div>
                            </div>
                        </div>

                        <!-- Footer -->
                        <div class="text-center mt-3 text-muted">
                            <small>&copy; 2026 Library Management System. All rights reserved.</small>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Bootstrap JS Bundle -->
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>