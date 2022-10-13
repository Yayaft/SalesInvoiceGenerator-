/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salgen.Model;

import java.util.ArrayList;

/**
 *
 * @author Abdulrahman
 */
public class Invoice {
    
    private int invoiceNumber ;
    private String invoiceDate ;
    private String Client ;
    private ArrayList <Line> invoiceLines ;
   

    
    /****************************************** Constructors *************************************************************/
    public Invoice() {
    }

    public Invoice(int number, String Date , String Customer) {
        this.invoiceNumber = number;
        this.invoiceDate = Date;
        this.Client = Customer; 
        
    }


    
    


    /*****************************************Getters and Setters *******************************************************/
    
    public int getInvoiceNumber() {
        return invoiceNumber;
    }
        
    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getClient() {
        return Client;
    }

    public void setClient(String Client) {
        this.Client = Client;
    }

    public ArrayList<Line> getInvoiceLines() {
        if(invoiceLines == null)
        {
            invoiceLines = new ArrayList<>();
        }
        return invoiceLines;
    }

    public void setInvoiceLines(ArrayList<Line> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }

    
   /*******************************************************************************************************************/


    // Function to calculate the total price of lines of invoice 
    public double getTotalInvoice()
    {
        double sum = 0 ;
        
        if(invoiceLines == null)
        {
            return sum ;
        }
       
        else{
        for(Line line : invoiceLines)
        {
            sum += line.getTotalLine();
        }
        return sum ;
        }

    }
    
    
    // Function to return invoice into one string to save it to the file 
    public String getAsInvoiceCSV ()
    {
        return invoiceNumber + "," + invoiceDate + "," + Client ;  
    }
    
    
    @Override
    public String toString() {
        return "\nInvoice{" + "number=" + invoiceNumber + ", Customer=" + Client + ", Date=" + invoiceDate + ", Lines=" + invoiceLines + '}';
    }
}
