<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:set var="pageTitle" value="Verify OTP - Library Management System" scope="request" />
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
                                    <i class="fas fa-envelope-open-text fa-4x text-primary mb-3"></i>
                                    <h2 class="fw-bold">Verify OTP</h2>
                                    <p class="text-muted">Enter the 6-digit code sent to your email</p>
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

                                <form action="${pageContext.request.contextPath}/verify-otp" method="post">
                                    <input type="hidden" name="email" value="${email}">
                                    <input type="hidden" name="purpose" value="${purpose}">

                                    <div class="mb-4">
                                        <label for="otp" class="form-label text-center d-block">
                                            <i class="fas fa-key"></i> Enter OTP Code
                                        </label>
                                        <input type="text" class="form-control form-control-lg text-center" id="otp"
                                            name="otp" placeholder="000000" maxlength="6" pattern="[0-9]{6}" required
                                            autofocus style="letter-spacing: 10px; font-size: 24px; font-weight: bold;">
                                        <small class="form-text text-muted">
                                            OTP sent to: <strong>${email}</strong>
                                        </small>
                                    </div>

                                    <div class="d-grid">
                                        <button type="submit" class="btn btn-primary btn-lg">
                                            <i class="fas fa-check"></i> Verify OTP
                                        </button>
                                    </div>
                                </form>

                                <div class="text-center mt-4">
                                    <p class="mb-2">Didn't receive the code?</p>
                                    <a href="${pageContext.request.contextPath}/resend-otp?email=${email}&purpose=${purpose}"
                                        class="btn btn-outline-secondary">
                                        <i class="fas fa-redo"></i> Resend OTP
                                    </a>
                                </div>

                                <hr class="my-4">

                                <div class="text-center">
                                    <a href="${pageContext.request.contextPath}/login" class="text-decoration-none">
                                        <i class="fas fa-arrow-left"></i> Back to Login
                                    </a>
                                </div>

                                <div class="alert alert-warning mt-4 mb-0" role="alert">
                                    <small>
                                        <i class="fas fa-clock"></i>
                                        <strong>Note:</strong> OTP is valid for 10 minutes only.
                                    </small>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
            <script>
                // Auto-submit when 6 digits are entered
                document.getElementById('otp').addEventListener('input', function (e) {
                    if (this.value.length === 6) {
                        // Optional: auto-submit form
                        // this.form.submit();
                    }
                });

                // Only allow numbers
                document.getElementById('otp').addEventListener('keypress', function (e) {
                    if (e.which < 48 || e.which > 57) {
                        e.preventDefault();
                    }
                });
            </script>
        </body>

        </html>