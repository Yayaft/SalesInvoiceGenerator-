/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salgen.Conttroller;

import com.salgen.Model.Invoice;
import com.salgen.Model.Invoice_Table_Model;
import com.salgen.Model.Line;
import com.salgen.Model.Line_Table_Model;
import com.salgen.View.New_Invoice_Dialog;
import com.salgen.View.New_Line_Dialog;
import com.salgen.View.Sales_Invoice_Gen_Frame;
import java.awt.Frame;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Abdulrahman
 */
public class Sales_Invoice_Controller implements ActionListener , ListSelectionListener{
    
    /* To Synchronize between the frame and its controller 
    - Create an object of the frame in each controller 
    - Constructor of the controller to add its frame while initializing it
    */
    private Sales_Invoice_Gen_Frame frame ;
    private New_Line_Dialog new_Line_Dialog ;
    private New_Invoice_Dialog new_Invoice_Dialog ;
    
    // to save the last selected invoice in case of deletion or creation of line
    private int selectedInvoice  ;
    /*
    - If user didnt select a new invoice , then it is the previous invoice (True)
    - If user select a new invoice , then the selected invoice would be changed from the previous one (False)
    */
    private boolean Is_The_Same_Invoice = false ;


    public Sales_Invoice_Controller(Sales_Invoice_Gen_Frame frame) {
        this.frame = frame;
    }
    
    /***************************************************************************************************************/
    
    /* When Select a specific invoice from invoices table this function is performed automatically */
    @Override
    public void valueChanged(ListSelectionEvent lse) {
        
        // get the row number of the selected invoice 
        int selsctedInvoice = frame.getInvoicesTable().getSelectedRow();
        int selectedLine = frame.getLinesTable().getSelectedRow();
        
//        if(selsctedInvoice != -1 && selectedLine != -1)
//        {
//           
//        }
        
 
        /* check if user selected an invoice
        - selectedInvoice == -1 -> user has not selected an invoice from the table
        - any positive number -> the user has selected an invoice
        */ 
        if(selsctedInvoice != -1)
        {
            Is_The_Same_Invoice = false ;
            // get the selected invoice from the array of invoices
            Invoice currentInvoice = frame.getInvoices().get(selsctedInvoice);
            
            // put the data of the selected invoices at labels
            frame.getInvoiceNumberJlabel().setText("" + currentInvoice.getInvoiceNumber());
            frame.getInvoiceDateJlabel().setText("" + currentInvoice.getInvoiceDate());
            frame.getClientNameJlabel().setText("" + currentInvoice.getClient());
            frame.getInvoiceTotalJlable().setText("" + currentInvoice.getTotalInvoice());
            
            /*
            - Create a new line table model with the lines of the selected invoice
            - update the model of the line table with the new created model
            - inform the table that the data has been changed by calling fireTableDataChanged() method
            */
            Line_Table_Model line_Table_Model = new Line_Table_Model(currentInvoice.getInvoiceLines());
            frame.setLine_Table_Model(line_Table_Model);
            frame.getLinesTable().setModel(line_Table_Model);
            frame.getLine_Table_Model().fireTableDataChanged();
        }
    }
    
    

    @Override
    public void actionPerformed(ActionEvent ae) {
        
        // Check the action command to know the pressed button or menuItem
        // then head to the function of the pressed button to perform the specific order of it
        switch(ae.getActionCommand())
        {
            case "Load Invoices" : 
                loadInvoice() ;
                break;
            case "Save Invoices" :
                saveInvoices() ;
                break;
            case "Add New Invoice" : 
                addNewInvoice() ;
                break;
            case "Delete Invoice" :
                deleteSelectedInvoice() ;
                break;
            case "Create Invoice OK" : 
                createInvoiceOk();
                break;
            case "Create Invoice Cancel" :
                createInvoiceCancel();
                break;
            case "Add New Line" : 
                addNewLine() ;
                break;
            case "Delete Line" :
                deleteSelectedLine() ;
                break;
            case "Create Line OK" :
                createLineOk();
                break;
            case "Create Line Cancel" :
                createLineCancel();
                break;              
        }
    }
    
    /***********************************************************************************************************************/
    /************************ Method of Loading the files into the frame tables (invoice & line) *****************/

    private void loadInvoice() {
        
        
        // Create File chooser to choose files
        JFileChooser fc = new JFileChooser(); 

        try {
            // Open show dialog to show files so user can select the file of invoices from the system
            int result = fc.showOpenDialog(frame);

            // If the user chose a file and pressed "ok" button
            if (result == JFileChooser.APPROVE_OPTION) 
            {
                // get the selected file by the user
                File invoiceFile = fc.getSelectedFile();
                // get the path of the file in the system
                Path invoiceFilePath = Paths.get(invoiceFile.getAbsolutePath());
                // read the file contents and save every invoice in list of strings
                java.util.List<String> invoiceFileLines = Files.readAllLines(invoiceFilePath);

                // loop through the stored invoices in the list to get the requird data from each line
                for (String invoiceString : invoiceFileLines) {
                    // split each invoice into array of strings
                    String[] invoiceStringParts = invoiceString.split(",");
                    // Get the invoice number as it is the first variable in the array
                    int invoiceNumber = Integer.parseInt(invoiceStringParts[0]);
                    // Get the invoice date from the array
                    String invoiceDate = invoiceStringParts[1];
                    // get the client name from the array
                    String clientName = invoiceStringParts[2];

                    // Create a new invoice with the captured data from the line of the file
                    Invoice newInvoice = new Invoice(invoiceNumber, invoiceDate ,clientName);
                    // Add the invoice to the array of invoices of the frame
                    frame.getInvoices().add(newInvoice);
                }
                
               // Open show dialog to show files so user can select the file of lines from the system
                result = fc.showOpenDialog(frame);

                // If the user chose a file and pressed "ok" button
                if (result == JFileChooser.APPROVE_OPTION) 
                {
                    // get the selected file by the user
                    File lineFile = fc.getSelectedFile();
                    // get the path of the file in the system
                    Path lineFilePath = Paths.get(lineFile.getAbsolutePath());
                    // read the file contents and save every line in list of strings
                    java.util.List<String> lineFileLines = Files.readAllLines(lineFilePath);

                    // loop through the stored lines in the list to get the requird data from each line
                    for (String lineString : lineFileLines) 
                    {
                        // split each line into array of strings
                        String[] lineStringParts = lineString.split(",");
                        // Get the line number as it is the first variable in the array
                        int lineNumber = Integer.parseInt(lineStringParts[0]);
                        // Get the line item name from the array
                        String itemName = lineStringParts[1];
                        // Get the line item price from the array
                        double itemPrice = Double.parseDouble(lineStringParts[2]);
                        // Get the line item count from the array
                        int itemCount = Integer.parseInt(lineStringParts[3]);

                        // Loop through the array of invoices to get the respective invoice of the created line
                        for (Invoice invoice : frame.getInvoices()) 
                        {
                            // If the number of invoice equals to the number that captured from the file 
                            // Then this line belongs to this invoice
                            if (invoice.getInvoiceNumber()== lineNumber) 
                            {
                                // Create a new line with the captured data and the respective invoice
                                Line newLine = new Line(itemName, itemPrice, itemCount, invoice);
                                // Add the line to the array of lines of the respective invoice
                                invoice.getInvoiceLines().add(newLine);
                                break;
                            }
                        }
                    }

                }
                
                // Create a new invoice table model with the loaded invoices
                Invoice_Table_Model invoice_Table_Model = new Invoice_Table_Model(frame.getInvoices());
                // Update the table model of the frame with the new created model
                frame.setInvoice_Table_Model(invoice_Table_Model);
                // set the new model to model of the invoice table at the frame
                frame.getInvoicesTable().setModel(invoice_Table_Model);
                // inform the table that data has been changed to rediplay the invoice table
                frame.getInvoice_Table_Model().fireTableDataChanged();
                
                displayInvoices();
             
            }
        }    catch (NumberFormatException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Number Format Error\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "File Error\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Read Error\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

    }
    /**********************************************************************************************************************/
    /******************** Method of saving the contents of frame tables (Invoice & Line ) into selected files  *****************/

    private void saveInvoices() {
        
        // Create File chooser to choose file
        JFileChooser fc = new JFileChooser(); 
        try {
            // Open save dialog so user can choose the place to save the data of the invoice table
           int result = fc.showSaveDialog(frame);
           // If user chose a place and pressed ok button
        if(result == JFileChooser.APPROVE_OPTION)
        {
            // get the selcted file
            File invoiceFile = fc.getSelectedFile() ; 
            // Get the path of the selected file
            Path invoicePath = Paths.get(invoiceFile.getAbsolutePath());
            // create an array to store the invoices as CSV file
            java.util.List <String> invoiceLines = new ArrayList<>() ;
              
            // Loop through invoices of frame to convert them to CSV file
            for(Invoice invoice : frame.getInvoices())
            {
                String invoiceCSV = invoice.getAsInvoiceCSV();
                // Add the converted invoice to the array 
                invoiceLines.add(invoiceCSV);
            }
            // Save the array of CSV invoices to the selected file 
            Files.write(invoicePath, invoiceLines) ;
            // Display a messgae to inform the user that the invoices have been saved successfully 
            JOptionPane.showMessageDialog(frame, "Invoices Saved successfully", "Attention", JOptionPane.INFORMATION_MESSAGE);
      
            
            // Open save dialog so user can choose the place to save the data of the invoice table
            result = fc.showOpenDialog(frame) ;
            // If user chose a place and pressed ok button
            if(result == JFileChooser.APPROVE_OPTION)
            {
                // get the selcted file
                File lineFile = fc.getSelectedFile();
                // Get the path of the selected file
                Path linePath = Paths.get(lineFile.getAbsolutePath());
                // create an array to store the lines as CSV file
                java.util.List <String> lineLines = new ArrayList<String>();
                
                // Loop through invoices of frame to get the lines 
                for (Invoice invoice : frame.getInvoices()) {
                     // Loop through lines of invoice to convert them to CSV file
                    for(Line lineLine : invoice.getInvoiceLines())
                    {
                        String line = lineLine.getAsLineCSV();
                        // Add the converted lines to the array 
                        lineLines.add(line);
                    }
                    
                }
                 // Save the array of CSV lines to the selected file 
                Files.write(linePath, lineLines) ;
                // Display a messgae to inform the user that the lines have been saved successfully 
                JOptionPane.showMessageDialog(frame, "Lines Saved successfully", "Attention", JOptionPane.INFORMATION_MESSAGE);
            }
            
        }

        } catch (IOException e) {e.printStackTrace();}
        
    }
    
    /**********************************************************************************************************************/
    /************************************************** Create & Delete new invoice *******************************************/ 

    // Function to display the new invoice dialog
    private void addNewInvoice() {
        // Create a new invoice dialog to insert the data of the invoice
        new_Invoice_Dialog = new New_Invoice_Dialog(frame);
        // Display the dialog
        new_Invoice_Dialog.setVisible(true);
    }
    
    // If user pressed ok button then create the new invoice
    private void createInvoiceOk() {
        
        // Get the entered name of the invoice
        String newInvoiceName = new_Invoice_Dialog.getInvoiceClientNameField().getText();
        // Get the entered date of the invoice
        String newInvoiceDate = new_Invoice_Dialog.getInvoiceDateField().getText();
        // Get the number of the new created invoice
        int newInvoiceNumber = frame.getNextInvoiceNum();
        
        // Create the invoice with the captured data
        Invoice newInvoice = new Invoice(newInvoiceNumber, newInvoiceDate, newInvoiceName) ;
        // Add the invoice to the array of invoices
        frame.getInvoices().add(newInvoice);
        
        // Inform the invoice table that data has been changed
        frame.getInvoice_Table_Model().fireTableDataChanged();
        
        // destroy the dialog after getting the data from it
        new_Invoice_Dialog.setVisible(false);
        new_Invoice_Dialog.dispose();
        new_Invoice_Dialog = null ;
        
        // Display a message to inform the user that the new invoice has been created successfully
        JOptionPane.showMessageDialog(frame, "Invoice Created Successfully", "Attention", JOptionPane.INFORMATION_MESSAGE);
        
        displayInvoices();
    }

    // if user pressed Cancel button then stop the opertaion of creation of invoice and destroy the dialog
    private void createInvoiceCancel() {
        new_Invoice_Dialog.setVisible(false);
        new_Invoice_Dialog.dispose();
        new_Invoice_Dialog = null ;
    }
    
    
    // A function to delete a selected invoice from the user
    private void deleteSelectedInvoice() {
        // Get the row number of selected invoice
        int selsctedInvoice = frame.getInvoicesTable().getSelectedRow();
        
        
        /* check if user selected an invoice
        - selectedInvoice == -1 -> user has not selected an invoice from the table
        - any positive number -> the user has selected an invoice
        */     
        if(selsctedInvoice != -1)
        {
            // remove the selected invoice from the array of invoices of the frame
            frame.getInvoices().remove(selsctedInvoice);
            // Inform invoice table that data has been changed
            frame.getInvoice_Table_Model().fireTableDataChanged();
            
            // set invoice data labels to be empty 
            frame.getInvoiceNumberJlabel().setText("");
            frame.getInvoiceDateJlabel().setText("");
            frame.getClientNameJlabel().setText("");
            frame.getInvoiceTotalJlable().setText("");
            
            // Remove the array of lines of the removed invoice from the lines table
            Line_Table_Model line_Table_Model = new Line_Table_Model(new ArrayList<Line>());
            frame.setLine_Table_Model(line_Table_Model);
            frame.getLinesTable().setModel(line_Table_Model);
            frame.getLine_Table_Model().fireTableDataChanged();
            
        // display a message tells the user thst new invoice has been removed successfully
        JOptionPane.showMessageDialog(frame, "Invoice Removed Successfully", "Attention", JOptionPane.INFORMATION_MESSAGE);
        
        displayInvoices();
        }
    }
    
    /**********************************************************************************************************************/
    /************************************************** Create & Delete new line *******************************************/ 

    // Function to display the new line dialog
    private void addNewLine() {
        // Create a new invoice dialog to insert the data of the invoice
        new_Line_Dialog = new New_Line_Dialog(frame);
        // Display the dialog
        new_Line_Dialog.setVisible(true);
    }
    

 
    private void createLineOk() {
        
        if(Is_The_Same_Invoice == false)
        {
        // Get the row number of selected invoice
         selectedInvoice = frame.getInvoicesTable().getSelectedRow();
         Is_The_Same_Invoice = true ;
        }
        
        // Get the enterd line item name of the line
        String name = new_Line_Dialog.getLineItemNameField().getText();
        // Get the enterd line item price of the line
        double price = Double.parseDouble(new_Line_Dialog.getLineItemPriceField().getText());
        // Get the enterd line item count of the line
        int count = Integer.parseInt(new_Line_Dialog.getLineItemCountField().getText());
        
        
        /* check if user selected an invoice
        - selectedInvoice == -1 -> user has not selected an invoice from the table
        - any positive number -> the user has selected an invoice
        */
        
        if(selectedInvoice != -1)
        {
            // get the slected invoice to access the array of lines of it
            Invoice invoice = frame.getInvoices().get(selectedInvoice);
            // create new line with the entered data 
            Line line = new Line(name, price, count, invoice);
            // Add the line to the array of lines of the table model
            frame.getLine_Table_Model().getLines().add(line);
            // Inform the line table model that data has been changed
            frame.getLine_Table_Model().fireTableDataChanged();
            // Inform the invoice table model that data has been changed
            frame.getInvoice_Table_Model().fireTableDataChanged();
            // get the new total of invoice after adding the new line to it
            frame.getInvoiceTotalJlable().setText(""+invoice.getTotalInvoice());
                   
            // display a message tells the user thst new line has been created successfully
            JOptionPane.showMessageDialog(frame, "Line Created Successfully", "Attention", JOptionPane.INFORMATION_MESSAGE);
            
            displayInvoices();
        }
            // destroy line dialog   
            new_Line_Dialog.setVisible(false);
            new_Line_Dialog.dispose();
            new_Line_Dialog = null ;
            

    }

    // if user pressed Cancel button then stop the opertaion of creation of line and destroy the dialog
    private void createLineCancel() {
        new_Line_Dialog.setVisible(false);
        new_Line_Dialog.dispose();
        new_Line_Dialog = null ;
    }

    
   
    private void deleteSelectedLine() {
        
        // Get the row number of selected line
        int selectedLine = frame.getLinesTable().getSelectedRow();
        
        // if user selected a new invoice
        if(Is_The_Same_Invoice == false)
        {
        // Get the row number of selected invoice
         selectedInvoice = frame.getInvoicesTable().getSelectedRow();
         // raise a flag that there is a selected invoice until user select a new one
         Is_The_Same_Invoice = true ;
        }
        
        /* check if user selected an invoice and line
        - selectedInvoice == -1 -> user has not selected an invoice and line from the tablea
        - any positive number -> the user has selected an invoice and line
        */
        if(selectedLine != -1 )
        {
            // Get the selected line

            Line line  = frame.getLine_Table_Model().getLines().get(selectedLine);
            
            // Remove the selected line from the line table model
            frame.getLine_Table_Model().getLines().remove(selectedLine);
            // Inform the line table model that data has been changed
            frame.getLine_Table_Model().fireTableDataChanged();
            
            // Inform the invoice table model that data has been changed
            frame.getInvoice_Table_Model().fireTableDataChanged();
            // get the new total of invoice after removing the new line from it
            frame.getInvoiceTotalJlable().setText(""+line.getInvoiceOfLine().getTotalInvoice());
            
        // display a message tells the user thst new line has been removed successfully
        JOptionPane.showMessageDialog(frame, "Line Removed Successfully", "Attention", JOptionPane.INFORMATION_MESSAGE);
        
        displayInvoices();
            
        }
    }
    
    private void displayInvoices(){
         for (Invoice header :frame.getInvoices()) {
             System.out.println(header);
         }
     }

   
}
