/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bean.ShoppingBean;
import bean.Book;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;


/**
 *
 * @author jh94l
 */
@WebServlet(name = "ShoppingPage", urlPatterns = {"/ShoppingPage"})
public class ShoppingPage extends HttpServlet {

    
    private final char QUOTE = '"';
    private Book book;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         HttpSession session = request.getSession(true);
        book = new Book();
        ShoppingBean shoppingBean = (ShoppingBean) session.getAttribute("ShoppingBean");
        ArrayList<Book> shopBookList = (ArrayList<Book>) session.getAttribute("ShopList");
        
        if (shoppingBean == null) {
            shoppingBean = new ShoppingBean();
            book.createTable();
            session.setAttribute("ShoppingBean", shoppingBean);
        }
        if (shopBookList == null) {
            shopBookList = new ArrayList<>();
            shopBookList = book.getBook();
            session.setAttribute("ShopList", shopBookList);
        }
        

        response.setContentType("text/html");

        String fullHTML = "";
        fullHTML += "<!DOCTYPE html>\n" + "<html>\n" + "<body>\n"
                + "<title>Shopping Page</title>\n" + "<h2>Books:</h2>\n"
                + "<form><table border=\"5\">\n"
                + "<tr><th>Book Name</th><th>Price</th></tr>\n";
        for (int i = 0; i < shopBookList.size(); i++) {
            fullHTML += "<tr><td>" + shopBookList.get(i).name + "</td><td>$" + String.format("%.2f", shopBookList.get(i).price) + "</td>";
            fullHTML += "<td><input type=" + QUOTE + "submit" + QUOTE + " name=" + QUOTE + i + QUOTE + " value=" + QUOTE + "Add to Cart" + QUOTE + "></td><tr>";
        }

        fullHTML += "</table></form>\n";

        for (int i = 0; i < shopBookList.size(); i++) {
            if (request.getParameter(Integer.toString(i)) != null) {
                fullHTML += "<p>"+shopBookList.get(i).name+" has been added to the cart! Please check it.</p>";
                shoppingBean.addToCart(shopBookList.get(i));
                session.setAttribute("ShoppingBean", shoppingBean);
            }
        }
        
        request.setAttribute("shoppingBean", shoppingBean);
        fullHTML += "<a href=\"ShoppingCart\">view your cart</a>\n";
        fullHTML += "</body></html>";

        PrintWriter pw = response.getWriter();
        pw.println(fullHTML);

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
