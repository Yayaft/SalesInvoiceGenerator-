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
public class New_Line_Dialog extends JDialog{
    
    private JTextField lineFieldItemNameField;
    private JLabel lineItemNameLbl;
     
    private JTextField lineItemCountField;
    private JLabel lineItemCountLbl;
   
    
    private JTextField lineItemPriceField;
    private JLabel lineItemPriceLbl;
     
    private JButton lineOKBtn;
    private JButton lineCancelBtn;
    
    public New_Line_Dialog(Sales_Invoice_Gen_Frame frame) {
        lineFieldItemNameField = new JTextField(20);
        lineItemNameLbl = new JLabel("Item Name");
        
        lineItemCountField = new JTextField(20);
        lineItemCountLbl = new JLabel("Item Count");
        
        lineItemPriceField = new JTextField(20);
        lineItemPriceLbl = new JLabel("Item Price");
        
        lineOKBtn = new JButton("OK");
        lineCancelBtn = new JButton("Cancel");
        
        lineOKBtn.setActionCommand("Create Line OK");
        lineCancelBtn.setActionCommand("Create Line Cancel");
        
        lineOKBtn.addActionListener(frame.getController());
        lineCancelBtn.addActionListener(frame.getController());
        
        setLayout(new GridLayout(4, 2));
        
        add(lineItemNameLbl);
        add(lineFieldItemNameField);
        add(lineItemCountLbl);
        add(lineItemCountField);
        add(lineItemPriceLbl);
        add(lineItemPriceField);
        add(lineOKBtn);
        add(lineCancelBtn);
        
        pack();
    }

    public JTextField getLineItemNameField() {
        return lineFieldItemNameField;
    }

    public JTextField getLineItemCountField() {
        return lineItemCountField;
    }

    public JTextField getLineItemPriceField() {
        return lineItemPriceField;
    }


    
}
  
