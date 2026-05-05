<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
    <%@ page import="java.util.*" %>
        <%@ page import="dto.UserDto" %>
            <%@ page import="dto.IssuedBookView" %>
                <%@ page import="dto.Book" %>
                    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

                        <!DOCTYPE html>
                        <html>

                        <head>
                            <title>Issue Books</title>
                            <link rel="stylesheet"
                                href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
                            <link rel="stylesheet"
                                href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
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
                                <h2>Issue Desk</h2>

                                <div class="form-container">
                                    <h4>Issue a Book</h4>
                                    <form action="book?action=issue" method="post" class="row g-3">
                                        <div class="col-md-4">
                                            <label class="form-label">Reader</label>
                                            <input type="text" id="readerSearch" class="form-control mb-2"
                                                placeholder="Search reader...">
                                            <select name="userId" id="userId" class="form-select" required size="5">
                                                <option value="">Select Reader</option>
                                                <c:forEach var="u" items="${readers}">
                                                    <option value="${u.id}">${u.firstname} ${u.lastname} (${u.email})
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-md-4">
                                            <label class="form-label">Book</label>
                                            <input type="text" id="bookSearch" class="form-control mb-2"
                                                placeholder="Search book...">
                                            <select name="bookId" id="bookId" class="form-select" required size="5">
                                                <option value="">Select Book</option>
                                                <c:forEach var="b" items="${books}">
                                                    
                                                    <option value="${b.bookId}" ${b.availableQuantity <=0 ? 'disabled'
                                                        : '' }>
                                                        ${b.title} (ISBN: ${b.isbn}) (${b.availableQuantity > 0 ?
                                                        'Available' : 'Out of Stock'})
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-md-2">
                                            <label class="form-label">Due Date</label>
                                            <input type="date" name="dueDate" class="form-control" required>
                                        </div>
                                        <div class="col-md-2 align-self-end">
                                            <button type="submit" class="btn btn-primary w-100">Issue Book</button>
                                        </div>
                                    </form>
                                    <c:if test="${not empty error}">
                                        <div class="alert alert-danger mt-3">${error}</div>
                                    </c:if>
                                </div>

                                <div class="table-container">
                                    <div class="d-flex justify-content-between align-items-center mb-3">
                                        <h4>Issued Books</h4>
                                        <form action="book" method="get" class="d-flex" style="max-width: 300px;">
                                            <input type="hidden" name="action" value="issue">
                                            <input type="text" name="searchIssued"
                                                class="form-control form-control-sm me-2"
                                                placeholder="Search issued books..." value="${searchIssuedQuery}">
                                            <button type="submit" class="btn btn-sm btn-primary">
                                                <i class="bi bi-search"></i>
                                            </button>
                                        </form>
                                    </div>
                                    <table class="table table-striped">
                                        <thead>
                                            <tr>
                                                <th>Reader</th>
                                                <th>Book</th>
                                                <th>ISBN</th>
                                                <th>Issue Date</th>
                                                <th>Due Date</th>
                                                <th>Status</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <jsp:useBean id="now" class="java.util.Date" />
                                            <c:forEach var="ib" items="${issuedBooks}">
                                                <tr>
                                                    <td>
                                                        ${ib.readerName}
                                                        <br><small class="text-muted">${ib.email}</small>
                                                    </td>
                                                    <td>${ib.bookTitle}</td>
                                                    <td>${not empty ib.bookIsbn ? ib.bookIsbn : '-'}</td>
                                                    <td>${ib.issueDate}</td>
                                                    <td>${ib.dueDate}</td>
                                                    <td>
                                                        <c:set var="isOverdue"
                                                            value="${ib.status == 'ISSUED' && ib.dueDate.before(now)}" />
                                                        <c:choose>
                                                            <c:when test="${isOverdue}">
                                                                <span class="badge bg-danger">OVERDUE</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span
                                                                    class="badge bg-${ib.status == 'ISSUED' ? 'warning' : (ib.status == 'RETURNED' ? 'success' : 'secondary')}">
                                                                    ${ib.status}
                                                                </span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <c:if test="${ib.status == 'ISSUED'}">
                                                            <a href="book?action=return&id=${ib.id}"
                                                                class="btn btn-sm btn-outline-success">Return</a>
                                                        </c:if>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>

                            <script
                                src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
                            <script>
                                function filterSelect(inputId, selectId) {
                                    const input = document.getElementById(inputId);
                                    const select = document.getElementById(selectId);
                                    const options = select.options;

                                    input.addEventListener('input', function () {
                                        const filter = input.value.toLowerCase();
                                        for (let i = 0; i < options.length; i++) {
                                            const text = options[i].text.toLowerCase();
                                            if (text.includes(filter) || options[i].value === "") {
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