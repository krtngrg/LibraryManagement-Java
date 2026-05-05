<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
    <%@ page
        import="dao.BookDao, dao.UserDao, dao.TransactionDao, dto.IssuedBookView, dto.Book, java.time.LocalDate, java.util.List, dto.UserDto"
        %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

            <%  UserDto user=(UserDto) session.getAttribute("user"); if (user==null ||
                (!"LIBRARIAN".equalsIgnoreCase(user.getRole()) && !"ADMIN".equalsIgnoreCase(user.getRole()))) {
                response.sendRedirect("login.jsp"); return; }  if (request.getAttribute("totalBooks")==null) { BookDao bookDao=new BookDao(); UserDao userDao=new
                UserDao(); TransactionDao transactionDao=new TransactionDao(); int totalBooks=0; int totalCopies=0; int
                availableCopies=0; try { List<Book> books = bookDao.getAllBooks();
                if (books != null) {
                totalBooks = books.size();
                for (Book b : books) {
                totalCopies += b.getQuantity();
                availableCopies += b.getAvailableQuantity();
                }
                }
                } catch (Exception e) {}

                int totalLent = totalCopies - availableCopies;
                int booksTaken = 0;
                int pastDue = 0;
                List<IssuedBookView> allTransactions = null;
                    try {
                    allTransactions = transactionDao.getAllIssuedBooks();
                    if (allTransactions != null) {
                    LocalDate today = LocalDate.now();
                    for (IssuedBookView ib : allTransactions) {
                    if ("ISSUED".equalsIgnoreCase(ib.getStatus())) {
                    booksTaken++;
                    if (ib.getDueDate() != null && ib.getDueDate().toLocalDate().isBefore(today)) {
                    pastDue++;
                    }
                    }
                    }
                    }
                    } catch (Exception e) {}

                    request.setAttribute("totalBooks", totalBooks);
                    request.setAttribute("totalCopies", totalCopies);
                    request.setAttribute("totalLent", totalLent);
                    request.setAttribute("totalMembers", userDao.getUsers("STUDENT").size());
                    request.setAttribute("booksTaken", booksTaken);
                    request.setAttribute("pastDue", pastDue);
                    request.setAttribute("circulationRate", (totalCopies > 0) ? (int)Math.round((totalLent * 100.0) /
                    totalCopies) : 0);
                    request.setAttribute("allTransactions", allTransactions);
                    }
                    %>

                    <!DOCTYPE html>
                    <html lang="en">

                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Librarian Dashboard | Premium Edition</title>

                        
                        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
                            rel="stylesheet">
                        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css"
                            rel="stylesheet">
                        <link
                            href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap"
                            rel="stylesheet">

                        <style>
                            :root {
                                --glass-bg: rgba(255, 255, 255, 0.7);
                                --glass-border: rgba(255, 255, 255, 0.2);
                                --primary-accent: #4b5320;
                                --soft-blue: #ecf0f1;
                            }

                            body {
                                font-family: 'Plus Jakarta Sans', sans-serif;
                                background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
                                min-height: 100vh;
                                margin: 0;
                                padding:0;
                            }

                            .main-content {
                                margin-left: 250px;
                                padding: 2.5rem;
                                min-height: 100vh;
                                transition: all 0.3s ease;
                            }

                            
                            .glass-card {
                                background: var(--glass-bg);
                                backdrop-filter: blur(10px);
                                -webkit-backdrop-filter: blur(10px);
                                border: 1px solid var(--glass-border);
                                border-radius: 20px;
                                box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.07);
                                padding: 1.5rem;
                                transition: transform 0.3s cubic-bezier(0.175, 0.885, 0.32, 1.275);
                            }

                            .glass-card:hover {
                                transform: translateY(-8px);
                            }

                            
                            .dashboard-header {
                                display: flex;
                                justify-content: space-between;
                                align-items: center;
                                margin-bottom: 2rem;
                            }

                            .welcome-text h2 {
                                font-weight: 700;
                                color: #2c3e50;
                                margin-bottom: 0.25rem;
                            }

                            
                            .stat-value {
                                font-size: 2.5rem;
                                font-weight: 800;
                                line-height: 1;
                                margin-bottom: 0.5rem;
                            }

                            .stat-label {
                                font-size: 0.85rem;
                                font-weight: 600;
                                text-transform: uppercase;
                                letter-spacing: 1px;
                                color: #7f8c8d;
                            }

                            .icon-box {
                                width: 60px;
                                height: 60px;
                                border-radius: 15px;
                                display: flex;
                                align-items: center;
                                justify-content: center;
                                font-size: 1.5rem;
                                margin-bottom: 1rem;
                            }

                            
                            .bg-gradient-blue {
                                background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
                                color: white;
                            }

                            .bg-gradient-green {
                                background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
                                color: white;
                            }

                            .bg-gradient-orange {
                                background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
                                color: white;
                            }

                            .bg-gradient-red {
                                background: linear-gradient(135deg, #ff0844 0%, #ffb199 100%);
                                color: white;
                            }

                            
                            .chart-container {
                                height: 350px;
                                width: 100%;
                            }

                            
                            .activity-table {
                                border-collapse: separate;
                                border-spacing: 0 10px;
                            }

                            .activity-table tr {
                                background-color: white;
                                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.02);
                                border-radius: 10px;
                            }

                            .activity-table td {
                                background-color: white;
                                border-top: none;
                                padding: 1.25rem 1rem;
                            }

                            .activity-table td:first-child {
                                border-radius: 10px 0 0 10px;
                            }

                            .activity-table td:last-child {
                                border-radius: 0 10px 10px 0;
                            }

                            .badge-issue {
                                background-color: #e3f2fd;
                                color: #1976d2;
                            }

                            .badge-return {
                                background-color: #e8f5e9;
                                color: #2e7d32;
                            }

                            @media (max-width: 991.98px) {
                                .main-content {
                                    margin-left: 0;
                                    padding: 1.5rem;
                                }
                            }
                        </style>
                    </head>

                    <body>

                        <jsp:include page="sidebar.jsp" />
      <style>body{ padding:40px; }   </style>
                        <main class="main-content">
                            
                            <div class="dashboard-header">
                                <div class="welcome-text">
                                    <h2>Welcome,
                                        <c:out value="${sessionScope.user.firstname}" default="Librarian" /> 👋
                                    </h2>
                                    <p class="text-muted">Here is what's happening in your library today.</p>
                                </div>
                                <div class="header-actions">
                                    <button class="btn btn-white glass-card py-2 px-4 fw-bold">
                                        <i class="bi bi-calendar3 me-2"></i> ${LocalDate.now()}
                                    </button>
                                </div>
                            </div>

                            <!-- Quick Metrics -->
                            <div class="row g-4 mb-4">
                                <div class="col-md-3">
                                    <div class="glass-card">
                                        <div class="icon-box bg-gradient-blue">
                                            <i class="bi bi-journals"></i>
                                        </div>
                                        <div class="stat-value">${totalBooks}</div>
                                        <div class="stat-label">Total Book</div>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="glass-card">
                                        <div class="icon-box bg-gradient-green">
                                            <i class="bi bi-people-fill"></i>
                                        </div>
                                        <div class="stat-value">${totalMembers}</div>
                                        <div class="stat-label">Total Members</div>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="glass-card">
                                        <div class="icon-box bg-gradient-orange">
                                            <i class="bi bi-arrow-up-right-circle"></i>
                                        </div>
                                        <div class="stat-value">${booksTaken}</div>
                                        <div class="stat-label">Books Lent</div>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="glass-card">
                                        <div class="icon-box bg-gradient-red">
                                            <i class="bi bi-exclamation-triangle"></i>
                                        </div>
                                        <div class="stat-value">${pastDue}</div>
                                        <div class="stat-label">Overdue Book</div>
                                    </div>
                                </div>
                            </div>

                            <!-- Analytical Section -->
                            <div class="row g-4 mb-4">
                                <!-- Main Chart -->
                                <div class="col-lg-8">
                                    <div class="glass-card h-100">
                                        <h5 class="fw-bold mb-4">Inventory Analysis</h5>
                                        <div class="chart-container">
                                            <canvas id="inventoryAnalysisChart"></canvas>
                                        </div>
                                    </div>
                                </div>
                                <!-- Progress Metrics -->
                                <div class="col-lg-4">
                                    <div class="glass-card h-100">
                                        <h5 class="fw-bold mb-4">Quick Insights</h5>

                                        <div class="mb-5">

                                            <div class="d-flex justify-content-between mb-2">
                                               <!-- <span class="fw-medium">Circulation Rate</span>
                                                <span class="fw-bold text-primary">${circulationRate}%</span>
                                            </div>
                                            <div class="progress" style="height: 10px; border-radius: 5px;">
                                                <div class="progress-bar"
                                                    style="width: ${circulationRate}%; background: linear-gradient(90deg, #6a11cb, #2575fc);">
                                                </div>
                                            </div>
                                            -->
                                        </div>

                                        <div class="mb-4">
                                            <div class="d-flex justify-content-between mb-2">
                                                <span class="fw-medium">Total Inventory Volume</span>
                                                <span class="fw-bold text-success">${totalCopies} Items</span>
                                            </div>
                                            <div class="progress" style="height: 10px; border-radius: 5px;">
                                                <div class="progress-bar bg-success" style="width: 100%;"></div>
                                            </div>
                                        </div>

                                        <div class="mt-auto pt-4">
                                            <!--  <div class="alert alert-info border-0 rounded-4 py-3"
                                                style="background: rgba(187, 222, 251, 0.3);">
                                                <i class="bi bi-info-circle-fill me-2"></i>
                                                <strong>Tip:</strong> Keep the circulation above 60% for optimal
                                                performance.
                                            </div>
                                            -->
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Recent Transactions -->
                            <div class="glass-card">
                                <div class="d-flex justify-content-between align-items-center mb-4">
                                    <h5 class="fw-bold mb-0">Recent Library Activity</h5>
                                    <a href="report?action=view"
                                        class="btn btn-sm btn-dark rounded-pill px-4">View Full Logs</a>
                                </div>
                                <div class="table-responsive">
                                    <table class="table activity-table">
                                        <thead>
                                            <tr class="text-muted">
                                                <th class="ps-3 border-0">Member Name</th>
                                                <th class="border-0">Book Information</th>
                                                <th class="border-0">Processed Date</th>
                                                <th class="border-0">Current Status</th>
                                                <th class="border-0 text-end pe-3">Action Type</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="item" items="${allTransactions}" varStatus="status">
                                                <c:if test="${status.index < 5}">
                                                    <tr>
                                                        <td class="ps-3 fw-bold">${item.readerName}</td>
                                                        <td>
                                                            <div class="fw-medium">${item.bookTitle}</div>
                                                            <small class="text-muted">Due: ${item.dueDate}</small>
                                                        </td>
                                                        <td>${item.issueDate}</td>
                                                        <td>
                                                            <span class="badge rounded-pill fw-bold" style="background: ${item.status == 'ISSUED' ? 'rgba(255, 193, 7, 0.1)' : 'rgba(40, 167, 69, 0.1)'}; 
                                                     color: ${item.status == 'ISSUED' ? '#856404' : '#155724'};">
                                                                <i class="bi bi-circle-fill me-1"
                                                                    style="font-size: 6px;"></i>
                                                                ${item.status}
                                                            </span>
                                                        </td>
                                                        <td class="text-end pe-3">
                                                            <span
                                                                class="badge font-weight-bold ${item.status == 'ISSUED' ? 'badge-issue' : 'badge-return'}">
                                                                ${item.status == 'ISSUED' ? 'Outgoing' : 'Incoming'}
                                                            </span>
                                                        </td>
                                                    </tr>
                                                </c:if>
                                            </c:forEach>
                                            <c:if test="${empty allTransactions}">
                                                <tr>
                                                    <td colspan="5" class="text-center py-5 text-muted">No activities
                                                        recorded today.</td>
                                                </tr>
                                            </c:if>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </main>

                        <!-- Essential Scripts -->
                        <script
                            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
                        <script src="https://cdn.jsdelivr.net/npm/chart.js@3.9.1/dist/chart.min.js"></script>

                        <script>
                            (function () {
                                function initAnalytics() {
                                    const ctx = document.getElementById('inventoryAnalysisChart');
                                    if (!ctx) return;

                                    const data = {
                                        labels: ['Total Units', 'Units Lent', 'Active Readers'],
                                        datasets: [{
                                            label: 'Library Metrics',
                                            data: [${ totalCopies }, ${ totalLent }, ${ totalMembers }],
                                            backgroundColor: [
                                                'rgba(106, 17, 203, 0.7)',
                                                'rgba(240, 147, 251, 0.7)',
                                                'rgba(17, 153, 142, 0.7)'
                                            ],
                                            borderColor: '#ffffff',
                                            borderWidth: 2,
                                            borderRadius: 12,
                                            barThickness: 35
                                        }]
                                    };

                                    new Chart(ctx, {
                                        type: 'bar',
                                        data: data,
                                        options: {
                                            indexAxis: 'y',
                                            responsive: true,
                                            maintainAspectRatio: false,
                                            plugins: {
                                                legend: { display: false },
                                                tooltip: {
                                                    backgroundColor: 'rgba(0,0,0,0.8)',
                                                    padding: 12,
                                                    titleFont: { size: 14, weight: 'bold' }
                                                }
                                            },
                                            scales: {
                                                x: {
                                                    beginAtZero: true,
                                                    grid: { color: 'rgba(0,0,0,0.05)' },
                                                    ticks: { font: { weight: '500' } }
                                                },
                                                y: {
                                                    grid: { display: false },
                                                    ticks: { font: { weight: '600', size: 13 } }
                                                }
                                            }
                                        }
                                    });
                                }

                                if (document.readyState === 'loading') {
                                    document.addEventListener('DOMContentLoaded', initAnalytics);
                                } else {
                                    initAnalytics();
                                }
                            })();
                        </script>
                    </body>

                    </html>