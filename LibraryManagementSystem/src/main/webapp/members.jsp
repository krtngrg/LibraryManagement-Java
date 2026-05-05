<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
    <%@ page import="java.util.List" %>
        <%@ page import="dto.UserDto" %>
            <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

                <!DOCTYPE html>
                <html>

                <head>
                    <title>${pageTitle != null ? pageTitle : 'Members'}</title>
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
                        <div class="d-flex justify-content-between align-items-center mb-4">
                            <h2>${pageTitle != null ? pageTitle : 'Members'}</h2>
                            <div class="d-flex align-items-center">
                                <form
                                    action="${pageContext.request.contextPath}/${searchUrl != null ? searchUrl : 'book'}"
                                    method="get" class="d-flex me-2">
                                    <input type="hidden" name="action"
                                        value="${searchAction != null ? searchAction : 'reader'}">
                                    <input type="text" name="query" class="form-control me-2"
                                        placeholder="Search members..." value="${searchQuery}">
                                    <button type="submit" class="btn btn-primary">
                                        <i class="bi bi-search"></i> Search
                                    </button>
                                </form>
                                <button type="button" class="btn btn-primary me-2" data-bs-toggle="modal"
                                    data-bs-target="#addUserModal">
                                    Add New Member
                                </button>
                                <c:choose>
                                    <c:when test="${sessionScope.role == 'ADMIN'}">
                                        <a href="admin?action=dashboard" class="btn btn-secondary">Dashboard</a>
                                    </c:when>
                                    <c:when test="${sessionScope.role == 'LIBRARIAN'}">
                                        <a href="librarian?action=dashboard" class="btn btn-secondary">Dashboard</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="librarian?action=dashboard" class="btn btn-secondary">Dashboard</a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <div class="table-container">
                            <table class="table table-striped table-hover">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Email</th>
                                        <th>Phone</th>
                                        <th>Role</th>
                                        <th>Status</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="u" items="${users}">
                                        <tr>
                                            <td>${u.id}</td>
                                            <td>${u.firstname} ${u.lastname}</td>
                                            <td>${u.email}</td>
                                            <td>${u.phone}</td>
                                            <td><span
                                                    class="badge bg-${u.role == 'ADMIN' ? 'danger' : (u.role == 'LIBRARIAN' ? 'info' : 'secondary')}">${u.role}</span>
                                            </td>
                                            <td><span
                                                    class="badge bg-${u.status == 'ACTIVE' || u.status == 'VERIFIED' ? 'success' : 'warning'}">${u.status}</span>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${sessionScope.role == 'ADMIN' || sessionScope.role == 'LIBRARIAN'}">
                                                        <button class="btn btn-sm btn-primary me-2"
                                                            onclick="editUser('${u.id}', '${u.firstname}', '${u.lastname}', '${u.email}', '${u.phone}', '${u.address}', '${u.role}')">
                                                            Edit
                                                        </button>
                                                        <form action="admin" method="post" style="display:inline;">
                                                            <input type="hidden" name="action" value="deleteUser">
                                                            <input type="hidden" name="userId" value="${u.id}">
                                                            <button type="submit" class="btn btn-sm btn-danger"
                                                                onclick="return confirm('Are you sure you want to delete this user?')">Delete</button>
                                                        </form>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="text-muted">No Actions</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty users}">
                                        <tr>
                                            <td colspan="7" class="text-center">No users found.</td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    
                    <div class="modal fade" id="addUserModal" tabindex="-1" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <form action="${addUrl != null ? addUrl : '#'}" method="post">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Add New Member</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <input type="hidden" name="action" value="${addActionValue}">
                                        <div class="mb-3">
                                            <label class="form-label">First Name</label>
                                            <input type="text" name="firstname" class="form-control" required>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Last Name</label>
                                            <input type="text" name="lastname" class="form-control" required>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Email</label>
                                            <input type="email" name="email" class="form-control" required>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Phone</label>
                                            <input type="text" name="phone" class="form-control" required>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Address</label>
                                            <input type="text" name="address" class="form-control" required>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Password</label>
                                            <input type="password" name="password" class="form-control" required>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary"
                                            data-bs-dismiss="modal">Close</button>
                                        <button type="submit" class="btn btn-primary">Save Member</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    
                    <div class="modal fade" id="editUserModal" tabindex="-1" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <form action="${updateUrl != null ? updateUrl : 'admin'}" method="post">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Edit Member</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <input type="hidden" name="action" value="updateUser">
                                        <input type="hidden" name="userId" id="editUserId">
                                        <input type="hidden" name="role" id="editUserRole">

                                        <div class="mb-3">
                                            <label class="form-label">First Name</label>
                                            <input type="text" name="firstname" id="editFirstname" class="form-control"
                                                required>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Last Name</label>
                                            <input type="text" name="lastname" id="editLastname" class="form-control"
                                                required>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Email</label>
                                            <input type="email" name="email" id="editEmail" class="form-control"
                                                required>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Phone</label>
                                            <input type="text" name="phone" id="editPhone" class="form-control"
                                                required>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Address</label>
                                            <input type="text" name="address" id="editAddress" class="form-control"
                                                required>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary"
                                            data-bs-dismiss="modal">Close</button>
                                        <button type="submit" class="btn btn-primary">Update Member</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
                    <script>
                        function editUser(id, firstname, lastname, email, phone, address, role) {
                            document.getElementById('editUserId').value = id;
                            document.getElementById('editFirstname').value = firstname;
                            document.getElementById('editLastname').value = lastname;
                            document.getElementById('editEmail').value = email;
                            document.getElementById('editPhone').value = phone;
                            document.getElementById('editAddress').value = address;
                            document.getElementById('editUserRole').value = role;

                            var editModal = new bootstrap.Modal(document.getElementById('editUserModal'));
                            editModal.show();
                        }
                    </script>
                </body>

                </html>