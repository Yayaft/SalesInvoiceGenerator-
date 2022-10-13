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
public class Line_Table_Model extends AbstractTableModel{
    
    private ArrayList<Line> lines ;
    private String [] lineTableColumns = {"Line Number" , "Item Name" , "Item Price" , "Item Count" , "Item Total"};

    
      /************************************************ Constructors ************************************************/
    public Line_Table_Model(ArrayList<Line> lines) {
        this.lines = lines;
    }
    
    
    /********************************* Getter of line of table Model ***********************************************/
    public ArrayList<Line> getLines() {
        return lines;
    }
    
    /*****************************************************************************************************************/

    @Override
    public int getRowCount() {
        return lines.size();
    }

    @Override
    public int getColumnCount() {
        return lineTableColumns.length;
    }

    @Override
    public String getColumnName(int column) {
        return lineTableColumns[column]; 
    }
    
    

    @Override
    public Object getValueAt(int row, int column) {
        Line line = lines.get(row);
        
        switch(column)
        {
            case 0 : return line.getInvoiceOfLine().getInvoiceNumber();
            case 1 : return line.getLineItemName();
            case 2 : return line.getLineItemPrice();
            case 3 : return line.getLineItemCount();
            case 4 : return line.getTotalLine();
            default: return "" ;
        }
        
    }


    
}
