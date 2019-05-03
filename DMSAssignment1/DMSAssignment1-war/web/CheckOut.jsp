<%-- 
    Document   : CheckOut
    Created on : 2019-5-3, 20:03:05
    Author     : jh94l
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.ShoppingBean"%>
<%@page import="javax.servlet.http.HttpSession"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Check Out</title>
    </head>
    <body>
        <center>
            <h1>Check out successful!</h1> 
            <a href="ShoppingPage">Continue shopping</a>
        </center>               
    </body>
    <% HttpSession cartSession=request.getSession(); 
        ShoppingBean shoppingBean=(ShoppingBean)cartSession.getAttribute("ShoppingBean");
        shoppingBean.clearCart();
        cartSession.setAttribute("ShoppingBean",shoppingBean);%>
        
    
</html>
