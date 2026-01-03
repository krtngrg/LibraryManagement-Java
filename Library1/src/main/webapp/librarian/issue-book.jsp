<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:set var="pageTitle" value="Issue Book" scope="request" />
        <jsp:include page="../includes/header.jsp" />

        <div class="row">
            <div class="col-12">
                <h2 class="mb-4"><i class="fas fa-hand-holding"></i> Issue Book</h2>
            </div>
        </div>

        <div class="row">
            <div class="col-md-8 offset-md-2">
                <div class="card shadow">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0"><i class="fas fa-book-reader"></i> Book Issue Form</h5>
                    </div>
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/librarian/issue-book" method="post">
                            <!-- Student Selection -->
                            <div class="mb-4">
                                <label for="studentId" class="form-label">
                                    <i class="fas fa-user-graduate"></i> Select Student *
                                </label>
                                <select class="form-select" id="studentId" name="studentId" required
                                    onchange="loadStudentDetails(this.value)">
                                    <option value="">-- Select Student --</option>
                                    <c:forEach items="${students}" var="student">
                                        <option value="${student.studentId}">${student.studentCode} -
                                            ${student.fullName}</option>
                                    </c:forEach>
                                </select>
                                <div id="studentDetails" class="mt-2"></div>
                            </div>

                            <!-- Book Selection -->
                            <div class="mb-4">
                                <label for="bookId" class="form-label">
                                    <i class="fas fa-book"></i> Select Book *
                                </label>
                                <select class="form-select" id="bookId" name="bookId" required
                                    onchange="loadBookDetails(this.value)">
                                    <option value="">-- Select Book --</option>
                                    <c:forEach items="${availableBooks}" var="book">
                                        <option value="${book.bookId}">${book.isbn} - ${book.title} (Available:
                                            ${book.availableQuantity})</option>
                                    </c:forEach>
                                </select>
                                <div id="bookDetails" class="mt-2"></div>
                            </div>

                            <!-- Issue Details -->
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="issueDate" class="form-label">
                                        <i class="fas fa-calendar"></i> Issue Date *
                                    </label>
                                    <input type="date" class="form-control" id="issueDate" name="issueDate"
                                        value="${currentDate}" required readonly>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="dueDate" class="form-label">
                                        <i class="fas fa-calendar-check"></i> Due Date *
                                    </label>
                                    <input type="date" class="form-control" id="dueDate" name="dueDate"
                                        value="${dueDate}" required readonly>
                                    <small class="text-muted">Due date is 14 days from issue date</small>
                                </div>
                            </div>

                            <!-- Remarks -->
                            <div class="mb-4">
                                <label for="remarks" class="form-label">
                                    <i class="fas fa-comment"></i> Remarks (Optional)
                                </label>
                                <textarea class="form-control" id="remarks" name="remarks" rows="3"
                                    placeholder="Enter any additional notes..."></textarea>
                            </div>

                            <!-- Info Alert -->
                            <div class="alert alert-info">
                                <i class="fas fa-info-circle"></i>
                                <strong>Note:</strong> The book will be issued for 14 days. Late returns will incur a
                                penalty of ₹5 per day.
                            </div>

                            <!-- Submit Buttons -->
                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <a href="${pageContext.request.contextPath}/librarian/dashboard"
                                    class="btn btn-secondary">
                                    <i class="fas fa-times"></i> Cancel
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-check"></i> Issue Book
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Recent Issues -->
        <div class="row mt-4">
            <div class="col-12">
                <div class="card shadow">
                    <div class="card-header bg-info text-white">
                        <h5 class="mb-0"><i class="fas fa-history"></i> Recent Issues</h5>
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${not empty recentIssues}">
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                            <tr>
                                                <th>Student</th>
                                                <th>Book</th>
                                                <th>Issue Date</th>
                                                <th>Due Date</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${recentIssues}" var="issue">
                                                <tr>
                                                    <td>${issue.studentName}</td>
                                                    <td>${issue.bookTitle}</td>
                                                    <td>${issue.issueDate}</td>
                                                    <td>${issue.dueDate}</td>
                                                    <td><span class="badge bg-warning">Issued</span></td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <p class="text-muted text-center">No recent issues</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>

        <script>
            function loadStudentDetails(studentId) {
                if (studentId) {
                    // AJAX call to load student details
                    // This would be implemented with actual servlet endpoint
                    $('#studentDetails').html('<div class="alert alert-success"><small>Student selected</small></div>');
                } else {
                    $('#studentDetails').html('');
                }
            }

            function loadBookDetails(bookId) {
                if (bookId) {
                    // AJAX call to load book details
                    $('#bookDetails').html('<div class="alert alert-success"><small>Book selected</small></div>');
                } else {
                    $('#bookDetails').html('');
                }
            }
        </script>

        <jsp:include page="../includes/footer.jsp" />