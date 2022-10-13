/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salgen.Model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Abdulrahman
 */
public class Invoice_Table_Model extends AbstractTableModel{
    
    private ArrayList<Invoice> invoices ; 
    private String [] invoiceTableColumns = {"Invoice Number" , "Invoice Date" , "Cilent Name" , "Total Of Invoice" } ;
    
    
   /************************************************ Constructors ************************************************/
    public Invoice_Table_Model(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }

    /********************************* Getter of invoice of table Model ***********************************************/
    public ArrayList<Invoice> getInvoices() {
        return invoices;
    }
    
    /*****************************************************************************************************************/
    

    @Override
    public int getRowCount() {
        return invoices.size();
    }

    @Override
    public int getColumnCount() {
        return invoiceTableColumns.length ;
    }
    
    @Override
    public String getColumnName(int column) {
        return invoiceTableColumns[column];
    }

    @Override
    public Object getValueAt(int row, int column) {
        Invoice invoice = invoices.get(row);
        
        switch(column)
        {
            case 0 : 
                return invoice.getInvoiceNumber();
            case 1 : 
                return invoice.getInvoiceDate();
            case 2 : 
                return invoice.getClient();
            case 3 : 
                return invoice.getTotalInvoice();
            default :
                return null ;
        }
    }
    
}
