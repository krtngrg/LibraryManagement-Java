<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
    <%@ page import="java.util.List" %>
        <%@ page import="dto.Book" %>
            <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

                <!DOCTYPE html>
                <html>

                <head>
                    <title>Manage Books</title>
                    <link rel="stylesheet"
                        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
                    <link rel="stylesheet"
                        href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
                    <style>
                        .container {
                            padding-top: 80px;
                        }

                        .table-container {
                            background: white;
                            padding: 20px;
                            border-radius: 8px;
                            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
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
                        <div class="row mb-4">
                            <div class="col-md-6">
                                <h2>Book Inventory</h2>
                            </div>
                            <div class="col-md-4">
                                <form
                                    action="${pageContext.request.contextPath}/${searchUrl != null ? searchUrl : 'book'}"
                                    method="get" class="d-flex">
                                    <input type="hidden" name="action"
                                        value="${searchAction != null ? searchAction : 'view'}">
                                    <input type="text" name="query" class="form-control me-2"
                                        placeholder="Search books..." value="${searchQuery}">
                                    <button type="submit" class="btn btn-primary">
                                        <i class="bi bi-search"></i> Search
                                    </button>
                                </form>
                            </div>
                            <div class="col-md-2 text-end">
                                <button class="btn btn-success" data-bs-toggle="modal"
                                    data-bs-target="#addBookModal">Add New Book</button>
                            </div>
                        </div>

                        <div class="table-container">
                            <table class="table table-striped table-hover">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Title</th>
                                        <th>Author</th>
                                        <th>ISBN</th>
                                        <th>Category</th>
                                        <th>Total Qty</th>
                                        <th>Available</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="b" items="${books}">
                                        <tr>
                                            <td>${b.bookId}</td>
                                            <td>${b.title}</td>
                                            <td>${b.author}</td>
                                            <td>${not empty b.isbn ? b.isbn : '-'}</td>
                                            <td>${b.category}</td>
                                            <td>${b.quantity}</td>
                                            <td>
                                                <span
                                                    class="badge bg-${b.availableQuantity > 0 ? 'success' : 'danger'}">
                                                    ${b.availableQuantity}
                                                </span>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${sessionScope.role == 'ADMIN' || sessionScope.role == 'LIBRARIAN'}">
                                                        <button class="btn btn-sm btn-primary me-2"
                                                            onclick="editBook('${b.bookId}', '${b.title}', '${b.author}', '${b.isbn}', '${b.category}', '${b.quantity}')">
                                                            Edit
                                                        </button>
                                                        <a href="book?action=delete&id=${b.bookId}"
                                                            class="btn btn-sm btn-danger"
                                                            onclick="return confirm('Delete this book?')">Delete</a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="text-muted">No Actions</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty books}">
                                        <tr>
                                            <td colspan="8" class="text-center">No books found.</td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    
                    <div class="modal fade" id="addBookModal" tabindex="-1">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <form action="book?action=add" method="post">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Add New Book</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="mb-3"><label>Title</label><input type="text" name="title"
                                                class="form-control" required></div>
                                        <div class="mb-3"><label>Author</label><input type="text" name="author"
                                                class="form-control" required></div>
                                        <div class="mb-3"><label>ISBN</label><input type="text" name="isbn"
                                                class="form-control" placeholder="Optional"></div>
                                        <div class="mb-3"><label>Category</label><input type="text" name="category"
                                                class="form-control"></div>
                                        <div class="mb-3"><label>Quantity</label><input type="number" name="quantity"
                                                class="form-control" min="1" value="1" required></div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" class="btn btn-primary">Save Book</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    
                    <div class="modal fade" id="editBookModal" tabindex="-1">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <form action="book?action=update" method="post">
                                    <input type="hidden" name="bookId" id="editBookId">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Edit Book</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="mb-3"><label>Title</label><input type="text" name="title"
                                                id="editTitle" class="form-control" required></div>
                                        <div class="mb-3"><label>Author</label><input type="text" name="author"
                                                id="editAuthor" class="form-control" required></div>
                                        <div class="mb-3"><label>ISBN</label><input type="text" name="isbn"
                                                id="editIsbn" class="form-control" placeholder="Optional"></div>
                                        <div class="mb-3"><label>Category</label><input type="text" name="category"
                                                id="editCategory" class="form-control"></div>
                                        <div class="mb-3"><label>Total Quantity</label><input type="number"
                                                name="quantity" id="editQuantity" class="form-control" min="1" required>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" class="btn btn-primary">Update Book</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
                    <script>
                        function editBook(id, title, author, isbn, category, quantity) {
                            document.getElementById('editBookId').value = id;
                            document.getElementById('editTitle').value = title;
                            document.getElementById('editAuthor').value = author;
                            document.getElementById('editIsbn').value = isbn;
                            document.getElementById('editCategory').value = category;
                            document.getElementById('editQuantity').value = quantity;

                            var editModal = new bootstrap.Modal(document.getElementById('editBookModal'));
                            editModal.show();
                        }
                    </script>
                </body>

                </html>