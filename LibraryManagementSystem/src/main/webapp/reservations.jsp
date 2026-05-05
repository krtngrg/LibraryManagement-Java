<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Manage Reservations</title>
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
            <style>
                .container {
                    padding-top: 80px;
                }

                .form-container,
                .table-container {
                    background: white;
                    padding: 20px;
                    border-radius: 8px;
                    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                    margin-bottom: 20px;
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
                <h2>Reservation Management</h2>

                <div class="form-container">
                    <h4>Add Reservation</h4>
                    <form action="reservation" method="post" class="row g-3">
                        <input type="hidden" name="action" value="add">
                        <div class="col-md-4">
                            <label class="form-label">Search Reader</label>
                            <input type="text" id="readerSearch" class="form-control mb-2"
                                placeholder="Search reader...">
                            <select name="userId" id="userId" class="form-select" required size="5">
                                <c:forEach var="u" items="${readers}">
                                    <option value="${u.userId}">${u.name} (${u.email})</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Search Book</label>
                            <input type="text" id="bookSearch" class="form-control mb-2" placeholder="Search book...">
                            <select name="bookId" id="bookId" class="form-select" required size="5">
                                <c:forEach var="b" items="${books}">
                                    <option value="${b.bookId}">${b.title} (ISBN: ${b.isbn})</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-2 align-self-end">
                            <button type="submit" class="btn btn-primary w-100">Reserve</button>
                        </div>
                    </form>
                    <c:if test="${not empty param.error}">
                        <div class="alert alert-danger mt-3">${param.error}</div>
                    </c:if>
                    <c:if test="${not empty param.success}">
                        <div class="alert alert-success mt-3">${param.success}</div>
                    </c:if>
                </div>

                <div class="table-container">
                    <h4>Active Reservations</h4>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Reader</th>
                                <th>Book</th>
                                <th>Date</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="r" items="${reservations}">
                                <tr>
                                    <td>${r.readerName}</td>
                                    <td>${r.bookTitle}</td>
                                    <td>${r.reservationDate}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${r.status == 'RESERVED'}">
                                                <span class="badge bg-info">RESERVED</span>
                                            </c:when>
                                            <c:when test="${r.status == 'HOLDED'}">
                                                <span class="badge bg-warning text-dark">HOLDED</span>
                                            </c:when>
                                            <c:when test="${r.status == 'RELEASED'}">
                                                <span class="badge bg-secondary">RELEASED</span>
                                            </c:when>
                                            <c:when test="${r.status == 'COMPLETED'}">
                                                <span class="badge bg-success">COMPLETED</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-light text-dark">${r.status}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:if test="${r.status == 'RESERVED' || r.status == 'HOLDED'}">
                                            <form action="reservation" method="post" style="display:inline;">
                                                <input type="hidden" name="action" value="release">
                                                <input type="hidden" name="id" value="${r.resId}">
                                                <button type="submit"
                                                    class="btn btn-sm btn-outline-danger">Release</button>
                                            </form>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
            <script>
                function filterSelect(inputId, selectId) {
                    const input = document.getElementById(inputId);
                    const select = document.getElementById(selectId);
                    const options = select.options;

                    input.addEventListener('input', function () {
                        const filter = input.value.toLowerCase();
                        for (let i = 0; i < options.length; i++) {
                            const text = options[i].text.toLowerCase();
                            if (text.includes(filter)) {
                                options[i].style.display = "";
                            } else {
                                options[i].style.display = "none";
                            }
                        }
                    });
                }
                filterSelect('readerSearch', 'userId');
                filterSelect('bookSearch', 'bookId');
            </script>
        </body>

        </html>