import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import controller.Reader;
import model.DBConnection;


@WebServlet("/members")
public class MemberServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        List<Reader> readers = new ArrayList<>();

        String sql = " SELECT user_id, name, email, phone, address, created_at FROM readers";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Reader r = new Reader();
                r.setUserId(rs.getInt("user_id"));
                r.setName(rs.getString("name"));
                r.setEmail(rs.getString("email"));
                r.setPhone(rs.getString("phone"));
                r.setAddress(rs.getString("address"));
                r.setCreatedAt(rs.getTimestamp("created_at"));

                readers.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("readers", readers);
        req.getRequestDispatcher("members.jsp").forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        String action = req.getParameter("action");

        try (Connection con = DBConnection.getConnection()) {
            if ("add".equals(action)) {

                String sql = "INSERT INTO readers (name, email, phone, address, password, created_at) " +
                        "VALUES (?, ?, ?, ?, ?, NOW())";

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, req.getParameter("name"));
                ps.setString(2, req.getParameter("email"));
                ps.setString(3, req.getParameter("phone"));
                ps.setString(4, req.getParameter("address"));
                ps.setString(5, req.getParameter("password"));
                ps.executeUpdate();

            } else if ("delete".equals(action)) {

                String sql = "DELETE FROM readers WHERE user_id = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(req.getParameter("user_id")));
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        res.sendRedirect("members");
    }
}
