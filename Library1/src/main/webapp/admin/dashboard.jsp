<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:set var="pageTitle" value="Admin Dashboard" scope="request" />
        <jsp:include page="../includes/header.jsp" />

        <div class="row">
            <div class="col-12">
                <h2 class="mb-4"><i class="fas fa-tachometer-alt"></i> Admin Dashboard</h2>
            </div>
        </div>

        <!-- Statistics Cards -->
        <div class="row mb-4">
            <div class="col-md-3">
                <div class="card bg-primary text-white shadow">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h6 class="text-uppercase mb-1">Total Users</h6>
                                <h2 class="mb-0">${totalUsers != null ? totalUsers : 0}</h2>
                            </div>
                            <div>
                                <i class="fas fa-users fa-3x opacity-50"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-3">
                <div class="card bg-success text-white shadow">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h6 class="text-uppercase mb-1">Total Books</h6>
                                <h2 class="mb-0">${totalBooks != null ? totalBooks : 0}</h2>
                            </div>
                            <div>
                                <i class="fas fa-book fa-3x opacity-50"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-3">
                <div class="card bg-info text-white shadow">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h6 class="text-uppercase mb-1">Total Students</h6>
                                <h2 class="mb-0">${totalStudents != null ? totalStudents : 0}</h2>
                            </div>
                            <div>
                                <i class="fas fa-user-graduate fa-3x opacity-50"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-3">
                <div class="card bg-warning text-white shadow">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h6 class="text-uppercase mb-1">Active Issues</h6>
                                <h2 class="mb-0">${activeTransactions != null ? activeTransactions : 0}</h2>
                            </div>
                            <div>
                                <i class="fas fa-exchange-alt fa-3x opacity-50"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Quick Actions -->
        <div class="row mb-4">
            <div class="col-12">
                <div class="card shadow">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0"><i class="fas fa-bolt"></i> Quick Actions</h5>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-4 mb-3">
                                <a href="${pageContext.request.contextPath}/admin/manage-users?action=add"
                                    class="btn btn-outline-primary btn-lg w-100">
                                    <i class="fas fa-user-plus fa-2x d-block mb-2"></i>
                                    Add New Librarian
                                </a>
                            </div>
                            <div class="col-md-4 mb-3">
                                <a href="${pageContext.request.contextPath}/admin/manage-users"
                                    class="btn btn-outline-success btn-lg w-100">
                                    <i class="fas fa-users-cog fa-2x d-block mb-2"></i>
                                    Manage Users
                                </a>
                            </div>
                            <div class="col-md-4 mb-3">
                                <a href="${pageContext.request.contextPath}/admin/reports"
                                    class="btn btn-outline-info btn-lg w-100">
                                    <i class="fas fa-chart-line fa-2x d-block mb-2"></i>
                                    View Reports
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Recent Activity -->
        <div class="row">
            <div class="col-md-6">
                <div class="card shadow mb-4">
                    <div class="card-header bg-info text-white">
                        <h5 class="mb-0"><i class="fas fa-clock"></i> Recent Librarians</h5>
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${not empty recentUsers}">
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                            <tr>
                                                <th>Name</th>
                                                <th>Email</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${recentUsers}" var="user">
                                                <tr>
                                                    <td>${user.fullName}</td>
                                                    <td>${user.email}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${user.active}">
                                                                <span class="badge bg-success">Active</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="badge bg-danger">Inactive</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <p class="text-muted text-center">No recent users</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>

            <div class="col-md-6">
                <div class="card shadow mb-4">
                    <div class="card-header bg-warning text-white">
                        <h5 class="mb-0"><i class="fas fa-lightbulb"></i> Pending Recommendations</h5>
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${not empty pendingRecommendations}">
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                            <tr>
                                                <th>Book Title</th>
                                                <th>Recommended By</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${pendingRecommendations}" var="rec">
                                                <tr>
                                                    <td>${rec.bookTitle}</td>
                                                    <td>${rec.recommendedByName}</td>
                                                    <td>
                                                        <a href="${pageContext.request.contextPath}/admin/recommendations?id=${rec.recommendationId}"
                                                            class="btn btn-sm btn-primary">Review</a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <p class="text-muted text-center">No pending recommendations</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="../includes/footer.jsp" />