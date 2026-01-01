import model.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/return")
public class ReturnServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.getRequestDispatcher("return.jsp").forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        int issueId = Integer.parseInt(req.getParameter("issueId"));
        int daysLate = Integer.parseInt(req.getParameter("days"));

        int fine = daysLate > 5 ? (daysLate - 5) * 10 : 0;

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE issued_books SET return_date = CURRENT_DATE WHERE id=?");
            ps.setInt(1, issueId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("result", "Returned. Fine: ₹" + fine);
        req.getRequestDispatcher("return.jsp").forward(req, res);
    }
}

