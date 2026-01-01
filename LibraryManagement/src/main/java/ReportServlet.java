import model.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/reports")
public class ReportServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try (Connection con = DBConnection.getConnection()) {

            ResultSet rs1 = con.createStatement()
                    .executeQuery("SELECT COUNT(*) FROM books");
            rs1.next();
            req.setAttribute("books", rs1.getInt(1));

            ResultSet rs2 = con.createStatement()
                    .executeQuery("SELECT COUNT(*) FROM members");
            rs2.next();
            req.setAttribute("members", rs2.getInt(1));

        } catch (Exception e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("report.jsp").forward(req, res);
    }
}

