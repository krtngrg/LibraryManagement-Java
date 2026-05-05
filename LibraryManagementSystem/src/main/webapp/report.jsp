<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <html>

        <head>
            <title>Transaction Report</title>
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
            <style>
                .container {
                    padding-top: 80px;
                }
            </style>
        </head>

        <body>
            <c:choose>
                <c:when test="${sessionScope.role == 'ADMIN'}">
                    <jsp:include page="dash_header.jsp" />
                </c:when>
                <c:otherwise>
                    <jsp:include page="sidebar.jsp" />
                </c:otherwise>
            </c:choose>

            <div class="container">
                <h2 class="mb-4">Transaction Report</h2>

                
                <form action="report" method="get" class="row g-3 mb-4">
                    <input type="hidden" name="action" value="view">
                    <div class="col-auto">
                        <label class="col-form-label">Start Date</label>
                    </div>
                    <div class="col-auto">
                        <input type="date" name="startDate" class="form-control">
                    </div>
                    <div class="col-auto">
                        <label class="col-form-label">End Date</label>
                    </div>
                    <div class="col-auto">
                        <input type="date" name="endDate" class="form-control">
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="btn btn-primary">Filter</button>
                    </div>
                </form>

                <table class="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th>Student Name</th>
                            <th>Book Title</th>
                            <th>Issue Date</th>
                            <th>Due Date</th>
                            <th>Return Date</th>
                            <th>Status</th>
                            <th>Penalty</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="t" items="${transactions}">
                            <tr>
                                <td>${t.readerName}</td>
                                <td>${t.bookTitle}</td>
                                <td>${t.issueDate}</td>
                                <td>${t.dueDate}</td>
                                <td>${t.returnDate != null ? t.returnDate : '-'}</td>
                                <td>${t.status}</td>
                                <td>${t.penalty > 0 ? t.penalty : '0.00'}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <c:choose>
                    <c:when test="${sessionScope.role == 'ADMIN'}">
                        <a href="admin?action=dashboard" class="btn btn-secondary mt-3">Dashboard</a>
                    </c:when>
                    <c:when test="${sessionScope.role == 'LIBRARIAN'}">
                        <a href="librarian?action=dashboard" class="btn btn-secondary mt-3">Dashboard</a>
                    </c:when>
                    <c:otherwise>
                        <a href="librarian?action=dashboard" class="btn btn-secondary mt-3">Dashboard</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </body>

        </html>