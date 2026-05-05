<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>

    <style>
        
        .sidebar {
            height: 100vh;
            width: 250px;
            position: fixed;
            top: 0;
            left: 0;
            background-color: #4b5320;
            
            color: #fff;
            padding-top: 20px;
            transition: all 0.3s;
            z-index: 1000;
            overflow-y: auto;
            
        }

        .sidebar a {
            padding: 15px 25px;
            text-decoration: none;
            font-size: 1.1rem;
            color: #d1d1d1;
            display: block;
            transition: 0.3s;
        }

        .sidebar a:hover {
            color: #fff;
            background-color: rgba(255, 255, 255, 0.1);
        }

        .sidebar .active {
            color: #fff;
            background-color: rgba(255, 255, 255, 0.2);
            border-left: 5px solid #fff;
        }

        .sidebar i {
            margin-right: 10px;
        }

        .brand-section {
            text-align: center;
            margin-bottom: 30px;
        }

        .brand-section h3 {
            font-weight: bold;
            letter-spacing: 1px;
            color: #fff;
            
        }

        
        body {
            padding-left: 250px;
            
            background-color: #f4f6f9;
            
            min-height: 100vh;
        }

        
    </style>

    
    <div class="sidebar">
        <div class="brand-section">
            <h3>LIBRARY</h3>
        </div>
        <a href="librarian_dashboard.jsp"
            class="${pageContext.request.requestURI.endsWith('librarian_dashboard.jsp') ? 'active' : ''}"><i
                class="bi bi-speedometer2"></i> Dashboard</a>
        <a href="book?action=view" class="${pageContext.request.requestURI.endsWith('books.jsp') ? 'active' : ''}"><i
                class="bi bi-book"></i> Books</a>
        <a href="book?action=reader"
            class="${pageContext.request.requestURI.endsWith('members.jsp') ? 'active' : ''}"><i
                class="bi bi-people"></i> Members</a>
        <a href="book?action=issue" class="${pageContext.request.requestURI.contains('issue') ? 'active' : ''}"><i
                class="bi bi-arrow-left-right"></i> Issue / Return</a>
        <a href="reservation?action=view"
            class="${pageContext.request.requestURI.contains('reservation') ? 'active' : ''}"><i
                class="bi bi-bookmark-check"></i> Reservations</a>
        <a href="report?action=view" class="${pageContext.request.requestURI.endsWith('report.jsp') ? 'active' : ''}"><i
                class="bi bi-file-earmark-text"></i> Reports</a>
        <a href="user?action=profile"
            class="${pageContext.request.requestURI.endsWith('profile.jsp') ? 'active' : ''}"><i
                class="bi bi-person-circle"></i> Profile</a>
        <a href="user?action=logout"><i class="bi bi-box-arrow-right"></i> Logout</a>
    </div>