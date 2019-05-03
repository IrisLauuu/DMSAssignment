/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;

/**
 *
 * @author jh94l
 */
@Stateful
@LocalBean
public class ShoppingBean {

    private ArrayList<Book> cartList;
    
    
    public ShoppingBean(){
        this.cartList=new ArrayList<>();
        
    }
    
    public ArrayList<Book> getCartList(){
        return this.cartList;
    }
    
    public void addToCart(Book book){
        boolean foundBook=false;
        for(Book b:cartList){
            if(b.name.equals(book.name)){
                b.amount++;
                b.refreshPrice(); //Calculate the new total price
                foundBook=true;
            }
        }
        if(foundBook==false){
            cartList.add(book);
        }
    }

    
    public void removeOneItem(String item){
        for(Book b:cartList)
        {
            if(b.name.equals(item))
            {
                if(b.amount==1)
                    cartList.remove(b);
                else{
                    b.amount--;
                    b.refreshPrice();//Calculate the new total price
                }
            }
        }
    }

    
   public void removeAll(String item){
       Book bookToBeRemoved=new Book();
       for(Book b:cartList)
       {
           if(b.name.equals(item))
           {
               bookToBeRemoved=b;
           }
       }
       cartList.remove(bookToBeRemoved);
   }
   
   public void clearCart(){
       while(!(cartList.isEmpty()))
           cartList.remove(0);
   }
   
   public double total(){
       double total=0;
       for(Book b:cartList)
           total+=b.totalPrice;
       return total;
   }
   
   
}
