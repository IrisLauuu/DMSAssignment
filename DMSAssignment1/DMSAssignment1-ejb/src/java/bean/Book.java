/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
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

    public Book(){}
    
    public Book(String name, double price){
        this.name=name;
        this.amount=1;
        this.price=price;
        this.refreshPrice();
    }

    public void refreshPrice(){
        this.totalPrice=price*amount;
    }

    
    
}
