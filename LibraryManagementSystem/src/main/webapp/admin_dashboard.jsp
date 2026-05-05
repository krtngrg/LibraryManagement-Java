<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <html>

    <head>
      <title>Admin Dashboard</title>
      
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
      <style>
        .container {
          padding-top: 20px;
        }

        .dashboard-card {
          margin-top: 20px;
          padding: 20px;
          border: 1px solid #ddd;
          border-radius: 8px;
        }
      </style>
    </head>

    <body>
      <jsp:include page="dash_header.jsp" /> 

      <div class="container">
        <h1>Admin Dashboard</h1>

        <div class="row">
          <div class="col-md-3">
            <div class="dashboard-card bg-light">
              <h3>Total Books</h3>
              <p class="display-4">${bookCount}</p>
            </div>
          </div>
          <div class="col-md-3">
            <div class="dashboard-card bg-light">
              <h3>Librarians</h3>
              <p class="display-4">${librarianCount}</p>
            </div>
          </div>
          <div class="col-md-3">
            <div class="dashboard-card bg-light">
              <h3>Students</h3>
              <p class="display-4">${studentCount}</p>
            </div>
          </div>
          <div class="col-md-3">
            <div class="dashboard-card bg-light">
              <h3>Issued Books</h3>
              <p class="display-4">${issuedCount}</p>
              <form action="admin" method="post" style="display:inline;">
                <input type="hidden" name="action" value="forceNotifyOverdue">
                <button type="submit" class="btn btn-sm btn-outline-warning">Notify Overdue</button>
              </form>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-md-3">
            <div class="dashboard-card">
              <h3>Book Inventory</h3>
              <p>Manage Books.</p>
              <a href="book?action=view" class="btn btn-primary">View Books</a>
            </div>
          </div>
          <div class="col-md-3">
            <div class="dashboard-card">
              <h3>Manage Librarians</h3>
              <p>Add or Remove Librarians.</p>
              <a href="admin?action=viewLibrarians" class="btn btn-primary">View Librarians</a>

            </div>
          </div>
          <div class="col-md-3">
            <div class="dashboard-card">
              <h3>User Management</h3>
              <p>View all registered students.</p>
              <a href="admin?action=viewAllUsers" class="btn btn-warning">View Students</a>
            </div>
          </div>
          <div class="col-md-3">
            <div class="dashboard-card">
              <h3>Reports</h3>
              <p>View System Reports.</p>
              <a href="report?action=view" class="btn btn-info">View Reports</a>
            </div>
          </div>
        </div>
      </div>



      <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>

    </html>