package controller;

import dto.IssuedBookView;
import dao.TransactionDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.Date;

@WebServlet("/report")
public class ReportServlet extends HttpServlet {

    private final TransactionDao transactionDao = new TransactionDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null)
            action = "view";

        if (action.equalsIgnoreCase("view")) {
            List<IssuedBookView> transactions = transactionDao.getAllIssuedBooks();

            
            String startDateStr = req.getParameter("startDate");
            String endDateStr = req.getParameter("endDate");

            if (startDateStr != null && !startDateStr.isEmpty() && endDateStr != null && !endDateStr.isEmpty()) {
                Date startDate = Date.valueOf(startDateStr);
                Date endDate = Date.valueOf(endDateStr);

                transactions = transactions.stream()
                        .filter(t -> {
                            Date d = t.getIssueDate();
                            return (d.equals(startDate) || d.after(startDate)) &&
                                    (d.equals(endDate) || d.before(endDate));
                        })
                        .collect(Collectors.toList());
            }

            req.setAttribute("transactions", transactions);
            req.getRequestDispatcher("report.jsp").forward(req, res);
        }
    }
}
