<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.Book" %>

<!DOCTYPE html>
<html>
<head>
    <title>Books</title>
    <style>
    body{background-color: #f5f5dc;}
        table { border-collapse: collapse; width: 80%; }
        th, td { border: 1px solid #ccc; padding: 8px; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>

<h2>Add Book</h2>
<form action="book?action=add" method="post">
    Title: <input type="text" name="title" required><br><br>
    Author: <input type="text" name="author" required><br><br>
    Category: <input type="text" name="category"><br><br>
    <button type="submit">Add Book</button>
</form>

<hr>

<h2>Book List</h2>
<table>
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Author</th>
        <th>Category</th>
    </tr>

    <%
        List<Book> books = (List<Book>) request.getAttribute("books");
        if (books != null) {
            for (Book b : books) {
    %>
    <tr>
        <td><%= b.getBookId() %></td>
        <td><%= b.getTitle() %></td>
        <td><%= b.getAuthor() %></td>
        <td><%= b.getCategory() %></td>
    </tr>
    <%
            }
        }
    %>
</table>

</body>
</html>
