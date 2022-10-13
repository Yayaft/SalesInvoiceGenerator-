/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salgen.View;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author eng_Nourhane
 */
public class New_Invoice_Dialog extends JDialog{
    private JTextField invoiceClientNameField;
    private JLabel invoiceClientNameLbl;
    
    private JTextField invoiceDateField;
    private JLabel invoiceDateLbl;
    
    private JButton invoiceOKBtn;
    private JButton invoiceCancelBtn;

    public New_Invoice_Dialog(Sales_Invoice_Gen_Frame frame) {

        invoiceClientNameLbl = new JLabel("Customer Name:");
        invoiceClientNameField = new JTextField(20);
        invoiceDateLbl = new JLabel("Invoice Date:");
        invoiceDateField = new JTextField(20);
        invoiceOKBtn = new JButton("OK");
        invoiceCancelBtn = new JButton("Cancel");
 
        invoiceOKBtn.setActionCommand("Create Invoice OK");
        invoiceCancelBtn.setActionCommand("Create Invoice Cancel");
        invoiceOKBtn.addActionListener(frame.getController());
        invoiceCancelBtn.addActionListener(frame.getController());
        setLayout(new GridLayout(3, 2));
        
        add(invoiceDateLbl);
        add(invoiceDateField);
        add(invoiceClientNameLbl);
        add(invoiceClientNameField);
        add(invoiceOKBtn);
        add(invoiceCancelBtn);
        
        pack();
        
    }

    public JTextField getInvoiceClientNameField() {
        return invoiceClientNameField;
    }

    public JTextField getInvoiceDateField() {
        return invoiceDateField;
    }

    
}
