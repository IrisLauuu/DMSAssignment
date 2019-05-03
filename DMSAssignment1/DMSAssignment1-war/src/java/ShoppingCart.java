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
import javax.servlet.http.HttpSession;


/**
 *
 * @author jh94l
 */
@WebServlet(name = "ShoppingCart", urlPatterns = {"/ShoppingCart"})
public class ShoppingCart extends HttpServlet {

   private final char QUOTE = '"';
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ShoppingBean shoppingBean = (ShoppingBean) session.getAttribute("ShoppingBean");

        int removeIndex = -1;
        boolean removeOne = false;
        boolean removeAll = false;
        if (request.getParameter("clear") != null) {
            shoppingBean.clearCart();
        } else {
            for (int i = 0; i < shoppingBean.getCartList().size(); i++) {
                if (request.getParameter(Integer.toString(i) + "one") != null) {
                    removeIndex = i;
                    removeOne = true;
                } else if (request.getParameter(Integer.toString(i) + "all") != null) {
                    removeIndex = i;
                    removeAll = true;
                }
            }

            if (removeOne == true) {
                shoppingBean.removeOneItem(shoppingBean.getCartList().get(removeIndex).name);
            }
            if (removeAll == true) {
                shoppingBean.removeAll(shoppingBean.getCartList().get(removeIndex).name);
            }
        }

        session.setAttribute("ShoppingBean", shoppingBean);

        response.setContentType("text/html");
        String fullHTML = "";
        fullHTML += "<!DOCTYPE html>\n" + "<html>\n" + "<body>\n"
                + "<title>Shopping Cart</title>\n";
        if (shoppingBean.getCartList().isEmpty()) {
            fullHTML += "<h2>Your cart is empty!</h2>";
        } else {
            fullHTML += "<h2>Your shopping cart:</h2>\n"
                    + "<form><table border=\"5\">\n"
                    + "<tr><th>Book Name</th><th>Price</th><th>Amount</th><th>Total Price</th></tr>\n";
            for (int j = 0; j < shoppingBean.getCartList().size(); j++) {
                fullHTML += "<tr><td>" + shoppingBean.getCartList().get(j).name + "</td><td>$" + String.format("%.2f", shoppingBean.getCartList().get(j).price) + "</td>"
                        + "<td>" + shoppingBean.getCartList().get(j).amount + "</td><td>$" + String.format("%.2f", shoppingBean.getCartList().get(j).totalPrice) + "</td>";
                fullHTML += "<td><input type=" + QUOTE + "submit" + QUOTE + " name=" + QUOTE + j + "one" + QUOTE + " value=" + QUOTE + "Remove one" + QUOTE + "></td>";
                fullHTML += "<td><input type=" + QUOTE + "submit" + QUOTE + " name=" + QUOTE + j + "all" + QUOTE + " value=" + QUOTE + "Remove all" + QUOTE + "></td></tr>";
            }
            fullHTML += "<tr><td><input type=" + QUOTE + "submit" + QUOTE + " name=" + QUOTE + "clear" + QUOTE + " value=" + QUOTE + "Clear cart" + QUOTE + "></td></tr>";
            fullHTML += "</table></form>\n";
            fullHTML += "<p>Total price: $" + String.format("%.2f", shoppingBean.total()) + "</p>";

        }
        
        if(!(shoppingBean.getCartList().isEmpty()))
            fullHTML+="<a href=\"CheckOut.jsp \">Check out!</a>\n";

        fullHTML += "<a href=\"ShoppingPage\">return to shop</a>\n";
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
