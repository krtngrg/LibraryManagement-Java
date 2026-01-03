<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="controller.Reader" %>

<!DOCTYPE html>
<html>
<head>
    <title>Library Members</title>
    <style>
    body{background-color: #f5f5dc;}
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            padding: 8px;
            border: 1px solid #ccc;
        }
        form {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<h2>Add New Reader</h2>

<form action="book?action=reader" method="post">
    <input type="hidden" name="edit" value="add">
    Name: <input type="text" name="name" required><br><br>
    Email: <input type="email" name="email" required><br><br>
    Phone: <input type="text" name="phone"><br><br>
    Address: <input type="text" name="address"><br><br>
    Password: <input type="password" name="password" required><br><br>

    <button type="submit">Add Reader</button>
</form>

<hr>

<h2>Existing Members</h2>

<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Phone</th>
        <th>Address</th>
        <th>Created At</th>
        <th>Action</th>
    </tr>

    <%
        List<Reader> readers = (List<Reader>) request.getAttribute("readers");
        if (readers != null) {
            for (Reader r : readers) {
    %>
    <tr>
        <td><%= r.getUserId() %></td>
        <td><%= r.getName() %></td>
        <td><%= r.getEmail() %></td>
        <td><%= r.getPhone() %></td>
        <td><%= r.getAddress() %></td>
        <td><%= r.getCreatedAt() %></td>
        <td>
            <form action="book?action=reader" method="post" style="display:inline;">
                <input type="hidden" name="edit" value="delete">
                <input type="hidden" name="user_id" value="<%= r.getUserId() %>">
                <button type="submit" onclick="return confirm('Delete this member?')">Delete</button>
            </form>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>

</body>
</html>
