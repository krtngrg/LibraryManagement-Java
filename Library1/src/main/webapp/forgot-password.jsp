<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:set var="pageTitle" value="Forgot Password - Library Management System" scope="request" />
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
                    <div class="col-md-5">
                        <div class="card shadow-lg">
                            <div class="card-body p-5">
                                <div class="text-center mb-4">
                                    <i class="fas fa-key fa-4x text-primary mb-3"></i>
                                    <h2 class="fw-bold">Forgot Password?</h2>
                                    <p class="text-muted">Enter your email to reset your password</p>
                                </div>

                                <c:if test="${not empty error}">
                                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                        <i class="fas fa-exclamation-circle"></i> ${error}
                                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                    </div>
                                </c:if>

                                <c:if test="${not empty success}">
                                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                                        <i class="fas fa-check-circle"></i> ${success}
                                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                    </div>
                                </c:if>

                                <c:choose>
                                    <c:when test="${step == 'reset'}">
                                        <!-- Step 2: Reset Password Form -->
                                        <form action="${pageContext.request.contextPath}/forgot-password" method="post">
                                            <input type="hidden" name="step" value="reset">
                                            <input type="hidden" name="email" value="${email}">

                                            <div class="mb-3">
                                                <label for="newPassword" class="form-label">
                                                    <i class="fas fa-lock"></i> New Password
                                                </label>
                                                <input type="password" class="form-control" id="newPassword"
                                                    name="newPassword" placeholder="Enter new password" required
                                                    minlength="6">
                                            </div>

                                            <div class="mb-3">
                                                <label for="confirmPassword" class="form-label">
                                                    <i class="fas fa-lock"></i> Confirm Password
                                                </label>
                                                <input type="password" class="form-control" id="confirmPassword"
                                                    name="confirmPassword" placeholder="Re-enter password" required>
                                            </div>

                                            <div class="d-grid">
                                                <button type="submit" class="btn btn-primary btn-lg">
                                                    <i class="fas fa-check"></i> Reset Password
                                                </button>
                                            </div>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <!-- Step 1: Request OTP Form -->
                                        <form action="${pageContext.request.contextPath}/forgot-password" method="post">
                                            <input type="hidden" name="step" value="request">

                                            <div class="mb-4">
                                                <label for="email" class="form-label">
                                                    <i class="fas fa-envelope"></i> Email Address
                                                </label>
                                                <input type="email" class="form-control" id="email" name="email"
                                                    placeholder="Enter your registered email" required autofocus>
                                            </div>

                                            <div class="d-grid">
                                                <button type="submit" class="btn btn-primary btn-lg">
                                                    <i class="fas fa-paper-plane"></i> Send Reset OTP
                                                </button>
                                            </div>
                                        </form>
                                    </c:otherwise>
                                </c:choose>

                                <hr class="my-4">

                                <div class="text-center">
                                    <a href="${pageContext.request.contextPath}/login" class="text-decoration-none">
                                        <i class="fas fa-arrow-left"></i> Back to Login
                                    </a>
                                </div>

                                <div class="alert alert-info mt-4 mb-0" role="alert">
                                    <small>
                                        <i class="fas fa-info-circle"></i>
                                        You'll receive an OTP on your registered email to reset your password.
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
                const form = document.querySelector('form');
                if (form && form.querySelector('#confirmPassword')) {
                    form.addEventListener('submit', function (e) {
                        const password = document.getElementById('newPassword').value;
                        const confirmPassword = document.getElementById('confirmPassword').value;

                        if (password !== confirmPassword) {
                            e.preventDefault();
                            alert('Passwords do not match!');
                            return false;
                        }
                    });
                }
            </script>
        </body>

        </html>