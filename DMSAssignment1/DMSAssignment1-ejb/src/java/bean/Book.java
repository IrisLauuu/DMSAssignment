/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.Id;
/**
 *
 * @author jh94l
 */
@Entity
public class Book implements Serializable{
    
    @Id
    public String name;
    public int amount;
    public double price;
    public double totalPrice;
    String sqlQuery;
    ResultSet resultSet;
    Connection connection;
    Statement statement;
    String dbURL = "jdbc:derby://localhost:1527/DMSDB;" + 
                "create=true;user=dms;password=dms2018";
    ArrayList<Book> cartList;

    public Book(){
        cartList = new ArrayList<>();
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            connection = DriverManager.getConnection(dbURL);
            statement = connection.createStatement();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ShoppingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Book(String name, double price){
        this.name=name;
        this.amount=1;
        this.price=price;
        this.refreshPrice();
    }

    public void refreshPrice(){
        this.totalPrice=price*amount;
    }

    public void createTable() {
        try {
            checkTableExisting("BOOK");
            sqlQuery = "CREATE TABLE BOOK " + "(BOOKNAME VARCHAR(50) PRIMARY KEY," +
                    " PRICE FLOAT)";
            statement.executeUpdate(sqlQuery);
        } catch (SQLException ex) {
            Logger.getLogger(ShoppingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        insertBook("Harry Potter and the Sorcerer s Stone", 19.99);
        insertBook("Journey to the West", 22.99);
        insertBook("Merrian-Webster Dictionary", 66.99);
        insertBook("Skyward", 29.99);
    }

    public void insertBook(String bookname, double price){
                
        try {
            sqlQuery = "INSERT INTO BOOK VALUES" +
                "('"+bookname+"', "+price+")";
            statement.executeUpdate(sqlQuery);
        } catch (SQLException ex) {
            Logger.getLogger(ShoppingBean.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
        }
    }
    
    public ArrayList<Book> getBook(){
        
        sqlQuery = "SELECT * FROM BOOK";
        try {
            resultSet = statement.executeQuery(sqlQuery);
            while(resultSet.next()){
                Book b = new Book(resultSet.getString(1),resultSet.getFloat(2));
                cartList.add(b);
            }		 

        } catch (SQLException ex) {
            Logger.getLogger(ShoppingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        	  		         
        return cartList;
    }
    
    private void checkTableExisting(String newTableName) {
        try {
            System.out.println("check existing tables.... ");
            String[] types = {"TABLE"};
            DatabaseMetaData dbmd = connection.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);//types);
            Statement dropStatement = null;

            while (rsDBMeta.next()) {
                String tableName = rsDBMeta.getString("TABLE_NAME");
                System.out.println("found: " + tableName);
                if (tableName.compareToIgnoreCase(newTableName) == 0) {
                    System.out.println(tableName + "  needs to be deleted");
                    String sqlDropTable = "DROP TABLE " + newTableName;
                    dropStatement = connection.createStatement();
                    dropStatement.executeUpdate(sqlDropTable);
                    System.out.println("table deleted");
                }
            }
            if (rsDBMeta != null) {
                rsDBMeta.close();
            }
            if (dropStatement != null) {
                dropStatement.close();
            }

        } catch (SQLException ex) {
        }

    }
    
    
}
