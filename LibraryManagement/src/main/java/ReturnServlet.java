import model.BookModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/return")
public class ReturnServlet extends HttpServlet {

    private final BookModel bookModel = new BookModel();

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.getRequestDispatcher("return.jsp").forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        int issueId = Integer.parseInt(req.getParameter("issueId"));
        int daysLate = Integer.parseInt(req.getParameter("days"));
        int fine = daysLate > 5 ? (daysLate - 5) * 10 : 0;

        boolean success = bookModel.returnBook(issueId);

        if (success) {
            req.setAttribute("result", "Returned. Fine: ₹" + fine);
        } else {
            req.setAttribute("result", "Error Returning Book.");
        }

        req.getRequestDispatcher("return.jsp").forward(req, res);
    }
}
