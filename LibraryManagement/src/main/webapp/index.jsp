<!DOCTYPE html>
<html lang="en">
<head>
    <style>
        .hero {
            text-align: center;
            padding: 100px 20px;
        }

        .hero h2 {
            font-size: 42px;
            color: #333;
        }

        .hero p {
            font-size: 18px;
            color: #555;
        }

        .bgimg {
            height: 400px;
           background-image: url("./images/books.jpg");
        }
    </style>
</head>

<jsp:include page="header.jsp" />

    <div class="hero">
        <h2>Welcome to Library Management System</h2>
        <p>Organize books, manage members, and simplify library operations</p>
    </div>

    <div class="bgimg"></div>
<jsp:include page="footer.jsp" />
