<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:set var="pageTitle" value="Manage Books" scope="request" />
        <jsp:include page="../includes/header.jsp" />

        <div class="row">
            <div class="col-12">
                <h2 class="mb-4"><i class="fas fa-book"></i> Manage Books</h2>
            </div>
        </div>

        <!-- Search and Filter -->
        <div class="card shadow mb-4">
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/librarian/manage-books" method="get" class="row g-3">
                    <div class="col-md-4">
                        <input type="text" class="form-control" name="search"
                            placeholder="Search by title, author, or ISBN" value="${param.search}">
                    </div>
                    <div class="col-md-3">
                        <select class="form-select" name="category">
                            <option value="">All Categories</option>
                            <option value="Programming">Programming</option>
                            <option value="Database">Database</option>
                            <option value="Web Development">Web Development</option>
                            <option value="Software Engineering">Software Engineering</option>
                            <option value="Computer Science">Computer Science</option>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <select class="form-select" name="availability">
                            <option value="">All Books</option>
                            <option value="available">Available Only</option>
                            <option value="issued">Issued Only</option>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <button type="submit" class="btn btn-primary me-2">
                            <i class="fas fa-search"></i> Search
                        </button>
                        <a href="${pageContext.request.contextPath}/librarian/manage-books?action=add"
                            class="btn btn-success">
                            <i class="fas fa-plus"></i> Add Book
                        </a>
                    </div>
                </form>
            </div>
        </div>

        <!-- Books Table -->
        <div class="card shadow">
            <div class="card-header bg-primary text-white">
                <h5 class="mb-0"><i class="fas fa-list"></i> Books List</h5>
            </div>
            <div class="card-body">
                <c:choose>
                    <c:when test="${not empty books}">
                        <div class="table-responsive">
                            <table class="table table-hover" id="booksTable">
                                <thead>
                                    <tr>
                                        <th>ISBN</th>
                                        <th>Title</th>
                                        <th>Author</th>
                                        <th>Category</th>
                                        <th>Publisher</th>
                                        <th>Total Qty</th>
                                        <th>Available</th>
                                        <th>Status</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${books}" var="book">
                                        <tr>
                                            <td>${book.isbn}</td>
                                            <td><strong>${book.title}</strong></td>
                                            <td>${book.author}</td>
                                            <td><span class="badge bg-info">${book.category}</span></td>
                                            <td>${book.publisher}</td>
                                            <td>${book.totalQuantity}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${book.availableQuantity > 0}">
                                                        <span class="badge bg-success">${book.availableQuantity}</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-danger">0</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${book.availableQuantity > 0}">
                                                        <span class="badge bg-success">Available</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-warning">All Issued</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <div class="btn-group" role="group">
                                                    <a href="${pageContext.request.contextPath}/librarian/manage-books?action=view&id=${book.bookId}"
                                                        class="btn btn-sm btn-info" title="View Details">
                                                        <i class="fas fa-eye"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/librarian/manage-books?action=edit&id=${book.bookId}"
                                                        class="btn btn-sm btn-primary" title="Edit">
                                                        <i class="fas fa-edit"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/librarian/manage-books?action=delete&id=${book.bookId}"
                                                        class="btn btn-sm btn-danger delete-btn" title="Delete">
                                                        <i class="fas fa-trash"></i>
                                                    </a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <!-- Pagination -->
                        <c:if test="${totalPages > 1}">
                            <nav aria-label="Page navigation">
                                <ul class="pagination justify-content-center">
                                    <c:forEach begin="1" end="${totalPages}" var="i">
                                        <li class="page-item ${currentPage == i ? 'active' : ''}">
                                            <a class="page-link" href="?page=${i}&search=${param.search}">${i}</a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </nav>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <div class="text-center py-5">
                            <i class="fas fa-book fa-4x text-muted mb-3"></i>
                            <p class="text-muted">No books found. Add your first book to get started!</p>
                            <a href="${pageContext.request.contextPath}/librarian/manage-books?action=add"
                                class="btn btn-primary">
                                <i class="fas fa-plus"></i> Add Book
                            </a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <!-- Add/Edit Book Modal -->
        <c:if test="${param.action == 'add' || param.action == 'edit'}">
            <div class="modal fade show d-block" id="bookModal" tabindex="-1" style="background: rgba(0,0,0,0.5);">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header bg-primary text-white">
                            <h5 class="modal-title">
                                <i class="fas ${param.action == 'add' ? 'fa-plus' : 'fa-edit'}"></i>
                                ${param.action == 'add' ? 'Add New Book' : 'Edit Book'}
                            </h5>
                            <a href="${pageContext.request.contextPath}/librarian/manage-books"
                                class="btn-close btn-close-white"></a>
                        </div>
                        <form action="${pageContext.request.contextPath}/librarian/manage-books" method="post">
                            <input type="hidden" name="action" value="${param.action}">
                            <c:if test="${param.action == 'edit'}">
                                <input type="hidden" name="bookId" value="${book.bookId}">
                            </c:if>

                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="isbn" class="form-label">ISBN *</label>
                                        <input type="text" class="form-control" id="isbn" name="isbn"
                                            value="${book.isbn}" required>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="title" class="form-label">Title *</label>
                                        <input type="text" class="form-control" id="title" name="title"
                                            value="${book.title}" required>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="author" class="form-label">Author *</label>
                                        <input type="text" class="form-control" id="author" name="author"
                                            value="${book.author}" required>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="publisher" class="form-label">Publisher</label>
                                        <input type="text" class="form-control" id="publisher" name="publisher"
                                            value="${book.publisher}">
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-4 mb-3">
                                        <label for="category" class="form-label">Category</label>
                                        <select class="form-select" id="category" name="category">
                                            <option value="">Select Category</option>
                                            <option value="Programming">Programming</option>
                                            <option value="Database">Database</option>
                                            <option value="Web Development">Web Development</option>
                                            <option value="Software Engineering">Software Engineering</option>
                                            <option value="Computer Science">Computer Science</option>
                                        </select>
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <label for="publicationYear" class="form-label">Publication Year</label>
                                        <input type="number" class="form-control" id="publicationYear"
                                            name="publicationYear" value="${book.publicationYear}" min="1900"
                                            max="2026">
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <label for="totalQuantity" class="form-label">Total Quantity *</label>
                                        <input type="number" class="form-control" id="totalQuantity"
                                            name="totalQuantity" value="${book.totalQuantity}" min="1" required>
                                    </div>
                                </div>

                                <div class="mb-3">
                                    <label for="description" class="form-label">Description</label>
                                    <textarea class="form-control" id="description" name="description"
                                        rows="3">${book.description}</textarea>
                                </div>
                            </div>

                            <div class="modal-footer">
                                <a href="${pageContext.request.contextPath}/librarian/manage-books"
                                    class="btn btn-secondary">Cancel</a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save"></i> ${param.action == 'add' ? 'Add Book' : 'Update Book'}
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </c:if>

        <jsp:include page="../includes/footer.jsp" />