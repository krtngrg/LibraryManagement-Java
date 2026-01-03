<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page isErrorPage="true" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Welcome - Library Management System</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        </head>

        <body class="bg-light">
            <div class="container">
                <div class="row justify-content-center align-items-center min-vh-100">
                    <div class="col-md-8 text-center">
                        <i class="fas fa-book-reader fa-5x text-primary mb-4"></i>
                        <h1 class="display-4 mb-4">Welcome to Library Management System</h1>
                        <p class="lead mb-4">A complete solution for managing your library efficiently</p>
                        <div class="d-grid gap-2 d-md-block">
                            <a href="${pageContext.request.contextPath}/login" class="btn btn-primary btn-lg me-md-2">
                                <i class="fas fa-sign-in-alt"></i> Login
                            </a>
                            <a href="${pageContext.request.contextPath}/signup" class="btn btn-outline-primary btn-lg">
                                <i class="fas fa-user-plus"></i> Sign Up
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </body>

        </html>