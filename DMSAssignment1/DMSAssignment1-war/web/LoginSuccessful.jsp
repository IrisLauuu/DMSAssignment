<%-- 
    Document   : LoginSuccessful
    Created on : 2019-5-3, 19:47:29
    Author     : Iris-
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book Store</title>
    </head>
    <body>
        <center>
        <%
            String username = (String)request.getAttribute("username");
            %>
            <h1>Hi <%= username %> welcome back to bookstore</h1>
            <h1>Let's go shopping!</h1>
        <form name="goShopping" action="ShoppingPage" method="post">
            <button>Go Shopping</button>
        </form>
    </center>
    </body>
</html>
