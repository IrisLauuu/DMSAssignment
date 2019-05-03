/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import bean.Customer;
import bean.CustomerSession;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Iris-
 */
public class LoginServlet extends HttpServlet {

    private Customer customer;
    private CustomerSession session;
    
    @Override
    protected void doGet(HttpServletRequest request, 
            HttpServletResponse response) 
            throws ServletException, IOException {
        
	session = new CustomerSession();
//        HttpSession session = request.getSession(true); 
        String username = (String)request.getParameter("username");
        String password = (String)request.getParameter("password");

        PrintWriter pw = response.getWriter();   
        if(request.getParameter("loginButton")!=null){
            
            if(session.login(username, password)){
                request.setAttribute("username", username);
                request.getRequestDispatcher("LoginSuccessful.jsp").forward(request, response);
            }else{
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/LoginAgain.jsp");
                dispatcher.forward(request, response);
            }
        }else if(request.getParameter("registerButton")!=null){
            session.createTable();
            session.register(username, password);
            request.setAttribute("username", username);
            request.getRequestDispatcher("RegisterSuccessful.jsp").forward(request, response);
        }
        
    }
    
    @Override
    public void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException{
        doGet(request, response);                       
    }



}
