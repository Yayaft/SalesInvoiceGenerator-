/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salgen.Model;

/**
 *
 * @author Abdulrahman
 */
public class Line {
    
    private String lineItemName ;
    private double lineItemPrice ;
    private int lineItemCount ;
    private Invoice invoiceOfLine ;
    
    /****************************************** Constructors *************************************************************/
    public Line()
    {
        
    }
    
    public Line( String item, double Price, int count) {
        this.lineItemName = item;
        this.lineItemPrice = Price;
        this.lineItemCount = count;
    }

    public Line( String item, double Price, int count, Invoice invoice) {
        this.lineItemName = item;
        this.lineItemPrice = Price;
        this.lineItemCount = count;
        this.invoiceOfLine = invoice;
    }
    
    /*****************************************Getters and Setters *******************************************************/


    public String getLineItemName() {
        return lineItemName;
    }

    public void setLineItemName(String lineItemName) {
        this.lineItemName = lineItemName;
    }

    public double getLineItemPrice() {
        return lineItemPrice;
    }

    public void setLineItemPrice(double lineItemPrice) {
        this.lineItemPrice = lineItemPrice;
    }

    public int getLineItemCount() {
        return lineItemCount;
    }

    public void setLineItemCount(int lineItemCount) {
        this.lineItemCount = lineItemCount;
    }

    public Invoice getInvoiceOfLine() {
        return invoiceOfLine;
    }

    public void setInvoiceOfLine(Invoice invoiceOfLine) {
        this.invoiceOfLine = invoiceOfLine;
    }

/**********************************************************************************************************************/
    
    // Function to return the total price of the line 
    public double getTotalLine()
    {
        return getLineItemCount()*getLineItemPrice();
    }

    // Function to return line into one string to save it to the file 
    public String getAsLineCSV ()
    {
        return invoiceOfLine.getInvoiceNumber() + "," + lineItemName + "," + lineItemPrice + "," + lineItemCount ;  
    }
    
    
    @Override
    public String toString() {
        return "\nLine{" + "Num=" + invoiceOfLine.getInvoiceNumber() + ", item=" + lineItemName + ", Price=" + lineItemPrice + ", count=" + lineItemCount + '}';
    }
    
}
