<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:set var="pageTitle" value="Librarian Dashboard" scope="request" />
        <jsp:include page="../includes/header.jsp" />

        <div class="row">
            <div class="col-12">
                <h2 class="mb-4"><i class="fas fa-tachometer-alt"></i> Librarian Dashboard</h2>
                <p class="lead">Welcome back, ${sessionScope.user.fullName}!</p>
            </div>
        </div>

        <!-- Statistics Cards -->
        <div class="row mb-4">
            <div class="col-md-3">
                <div class="card bg-primary text-white shadow">
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
                <div class="card bg-success text-white shadow">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h6 class="text-uppercase mb-1">Available Books</h6>
                                <h2 class="mb-0">${availableBooks != null ? availableBooks : 0}</h2>
                            </div>
                            <div>
                                <i class="fas fa-check-circle fa-3x opacity-50"></i>
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
                                <h6 class="text-uppercase mb-1">Issued Books</h6>
                                <h2 class="mb-0">${issuedBooks != null ? issuedBooks : 0}</h2>
                            </div>
                            <div>
                                <i class="fas fa-hand-holding fa-3x opacity-50"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-3">
                <div class="card bg-danger text-white shadow">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h6 class="text-uppercase mb-1">Overdue Books</h6>
                                <h2 class="mb-0">${overdueBooks != null ? overdueBooks : 0}</h2>
                            </div>
                            <div>
                                <i class="fas fa-exclamation-triangle fa-3x opacity-50"></i>
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
                            <div class="col-md-3 mb-3">
                                <a href="${pageContext.request.contextPath}/librarian/issue-book"
                                    class="btn btn-outline-primary btn-lg w-100">
                                    <i class="fas fa-hand-holding fa-2x d-block mb-2"></i>
                                    Issue Book
                                </a>
                            </div>
                            <div class="col-md-3 mb-3">
                                <a href="${pageContext.request.contextPath}/librarian/return-book"
                                    class="btn btn-outline-success btn-lg w-100">
                                    <i class="fas fa-undo fa-2x d-block mb-2"></i>
                                    Return Book
                                </a>
                            </div>
                            <div class="col-md-3 mb-3">
                                <a href="${pageContext.request.contextPath}/librarian/manage-books?action=add"
                                    class="btn btn-outline-info btn-lg w-100">
                                    <i class="fas fa-plus-circle fa-2x d-block mb-2"></i>
                                    Add New Book
                                </a>
                            </div>
                            <div class="col-md-3 mb-3">
                                <a href="${pageContext.request.contextPath}/librarian/manage-students?action=add"
                                    class="btn btn-outline-warning btn-lg w-100">
                                    <i class="fas fa-user-plus fa-2x d-block mb-2"></i>
                                    Add Student
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
                        <h5 class="mb-0"><i class="fas fa-clock"></i> Recent Transactions</h5>
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${not empty recentTransactions}">
                                <div class="table-responsive">
                                    <table class="table table-sm table-hover">
                                        <thead>
                                            <tr>
                                                <th>Student</th>
                                                <th>Book</th>
                                                <th>Status</th>
                                                <th>Date</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${recentTransactions}" var="trans">
                                                <tr>
                                                    <td>${trans.studentName}</td>
                                                    <td>${trans.bookTitle}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${trans.status == 'ISSUED'}">
                                                                <span class="badge bg-warning">Issued</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="badge bg-success">Returned</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>${trans.issueDate}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <a href="${pageContext.request.contextPath}/librarian/reports"
                                    class="btn btn-sm btn-primary">View All</a>
                            </c:when>
                            <c:otherwise>
                                <p class="text-muted text-center">No recent transactions</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>

            <div class="col-md-6">
                <div class="card shadow mb-4">
                    <div class="card-header bg-danger text-white">
                        <h5 class="mb-0"><i class="fas fa-exclamation-triangle"></i> Overdue Books</h5>
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${not empty overdueTransactions}">
                                <div class="table-responsive">
                                    <table class="table table-sm table-hover">
                                        <thead>
                                            <tr>
                                                <th>Student</th>
                                                <th>Book</th>
                                                <th>Due Date</th>
                                                <th>Days Late</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${overdueTransactions}" var="trans">
                                                <tr>
                                                    <td>${trans.studentName}</td>
                                                    <td>${trans.bookTitle}</td>
                                                    <td>${trans.dueDate}</td>
                                                    <td><span class="badge bg-danger">${trans.daysLate} days</span></td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <p class="text-success text-center"><i class="fas fa-check-circle"></i> No overdue
                                    books!</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="../includes/footer.jsp" />