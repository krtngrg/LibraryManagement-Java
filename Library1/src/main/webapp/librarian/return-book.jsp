<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:set var="pageTitle" value="Return Book" scope="request" />
        <jsp:include page="../includes/header.jsp" />

        <div class="row">
            <div class="col-12">
                <h2 class="mb-4"><i class="fas fa-undo"></i> Return Book</h2>
            </div>
        </div>

        <div class="row">
            <div class="col-md-8 offset-md-2">
                <div class="card shadow">
                    <div class="card-header bg-success text-white">
                        <h5 class="mb-0"><i class="fas fa-book-reader"></i> Book Return Form</h5>
                    </div>
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/librarian/return-book" method="post">
                            <!-- Transaction Selection -->
                            <div class="mb-4">
                                <label for="transactionId" class="form-label">
                                    <i class="fas fa-search"></i> Select Active Transaction *
                                </label>
                                <select class="form-select" id="transactionId" name="transactionId" required
                                    onchange="loadTransactionDetails(this.value)">
                                    <option value="">-- Select Transaction --</option>
                                    <c:forEach items="${activeTransactions}" var="trans">
                                        <option value="${trans.transactionId}">
                                            ${trans.studentName} - ${trans.bookTitle} (Issued: ${trans.issueDate})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <!-- Transaction Details (Loaded via AJAX or page refresh) -->
                            <c:if test="${not empty selectedTransaction}">
                                <div class="card bg-light mb-4">
                                    <div class="card-body">
                                        <h6 class="card-title"><i class="fas fa-info-circle"></i> Transaction Details
                                        </h6>
                                        <div class="row">
                                            <div class="col-md-6">
                                                <p><strong>Student:</strong> ${selectedTransaction.studentName}</p>
                                                <p><strong>Book:</strong> ${selectedTransaction.bookTitle}</p>
                                                <p><strong>Issue Date:</strong> ${selectedTransaction.issueDate}</p>
                                            </div>
                                            <div class="col-md-6">
                                                <p><strong>Due Date:</strong> ${selectedTransaction.dueDate}</p>
                                                <p><strong>Days Issued:</strong> ${daysIssued}</p>
                                                <p class="${isOverdue ? 'text-danger' : 'text-success'}">
                                                    <strong>Status:</strong> ${isOverdue ? 'OVERDUE' : 'On Time'}
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>

                            <!-- Return Date -->
                            <div class="mb-4">
                                <label for="returnDate" class="form-label">
                                    <i class="fas fa-calendar"></i> Return Date *
                                </label>
                                <input type="date" class="form-control" id="returnDate" name="returnDate"
                                    value="${currentDate}" required readonly>
                            </div>

                            <!-- Penalty Calculation -->
                            <c:if test="${penaltyAmount > 0}">
                                <div class="alert alert-warning">
                                    <h6><i class="fas fa-exclamation-triangle"></i> Penalty Calculation</h6>
                                    <p class="mb-0">
                                        <strong>Days Late:</strong> ${daysLate} days<br>
                                        <strong>Penalty Rate:</strong> ₹5 per day<br>
                                        <strong>Total Penalty:</strong> <span
                                            class="fs-5 text-danger">₹${penaltyAmount}</span>
                                    </p>
                                </div>
                                <input type="hidden" name="penaltyAmount" value="${penaltyAmount}">
                            </c:if>

                            <c:if test="${penaltyAmount == 0}">
                                <div class="alert alert-success">
                                    <i class="fas fa-check-circle"></i> No penalty - Book returned on time!
                                </div>
                            </c:if>

                            <!-- Remarks -->
                            <div class="mb-4">
                                <label for="remarks" class="form-label">
                                    <i class="fas fa-comment"></i> Remarks (Optional)
                                </label>
                                <textarea class="form-control" id="remarks" name="remarks" rows="3"
                                    placeholder="Enter any notes about the book condition..."></textarea>
                            </div>

                            <!-- Submit Buttons -->
                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <a href="${pageContext.request.contextPath}/librarian/dashboard"
                                    class="btn btn-secondary">
                                    <i class="fas fa-times"></i> Cancel
                                </a>
                                <button type="submit" class="btn btn-success" ${empty selectedTransaction ? 'disabled'
                                    : '' }>
                                    <i class="fas fa-check"></i> Return Book
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Overdue Books List -->
        <div class="row mt-4">
            <div class="col-12">
                <div class="card shadow">
                    <div class="card-header bg-danger text-white">
                        <h5 class="mb-0"><i class="fas fa-exclamation-triangle"></i> Overdue Books</h5>
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${not empty overdueBooks}">
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                            <tr>
                                                <th>Student</th>
                                                <th>Book</th>
                                                <th>Issue Date</th>
                                                <th>Due Date</th>
                                                <th>Days Late</th>
                                                <th>Penalty</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${overdueBooks}" var="book">
                                                <tr>
                                                    <td>${book.studentName}</td>
                                                    <td>${book.bookTitle}</td>
                                                    <td>${book.issueDate}</td>
                                                    <td>${book.dueDate}</td>
                                                    <td><span class="badge bg-danger">${book.daysLate} days</span></td>
                                                    <td><strong>₹${book.calculatedPenalty}</strong></td>
                                                    <td>
                                                        <a href="?transactionId=${book.transactionId}"
                                                            class="btn btn-sm btn-warning">
                                                            <i class="fas fa-undo"></i> Return
                                                        </a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <p class="text-success text-center">
                                    <i class="fas fa-check-circle fa-2x mb-2 d-block"></i>
                                    No overdue books!
                                </p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>

        <script>
            function loadTransactionDetails(transactionId) {
                if (transactionId) {
                    // Reload page with selected transaction
                    window.location.href = '?transactionId=' + transactionId;
                }
            }
        </script>

        <jsp:include page="../includes/footer.jsp" />