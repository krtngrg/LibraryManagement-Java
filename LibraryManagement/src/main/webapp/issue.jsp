<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ page import="java.util.*" %>
<%@ page import="dto.Reader" %>
<%@ page import="dto.IssuedBookView" %>
<%@ page import="controller.Book" %>

<!DOCTYPE html>
<html>
<head>
    <title>Issue Books</title>
    <style>
   body{background-color: #f5f5dc;}
        table { border-collapse: collapse; width: 90%; }
        th, td { border: 1px solid #ccc; padding: 8px; }
        th { background: #f2f2f2; }
    </style>
</head>
<body>

<h2>Issue a Book</h2>

<form action="issue" method="post">
    Reader:
    <select name="userId" required>
        <option value="">Select Reader</option>
        <%
            for (Object r : (List<?>)request.getAttribute("readers")) {
                Reader reader = (Reader) r;
        %>
        <option value="<%= reader.id %>"><%= reader.name %></option>
        <% } %>
    </select>

    Book:
    <select name="bookId" required>
        <option value="">Select Book</option>
        <%
            for (Object b : (List<?>)request.getAttribute("books")) {
                Book book = (Book) b;
        %>
        <option value="<%= book.getBookId() %>"><%= book.getTitle() %></option>
        <% } %>
    </select>

    Due Date:
    <input type="date" name="dueDate" required>

    <button type="submit">Issue Book</button>
</form>

<hr>

<h2>Issued Books</h2>

<table>
    <tr>
        <th>Reader</th>
        <th>Book</th>
        <th>Issue Date</th>
        <th>Due Date</th>
        <th>Status</th>
    </tr>

    <%
        List<?> issued = (List<?>) request.getAttribute("issuedBooks");
        for (Object o : issued) {
            IssuedBookView ib = (IssuedBookView) o;
    %>
    <tr>
        <td><%= ib.readerName %></td>
        <td><%= ib.bookTitle %></td>
        <td><%= ib.issueDate %></td>
        <td><%= ib.dueDate %></td>
        <td><%= ib.status %></td>
    </tr>
    <% } %>
</table>

</body>
</html>
