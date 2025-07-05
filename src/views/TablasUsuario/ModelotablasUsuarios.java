/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views.TablasUsuario;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Usuario
 */
public class ModelotablasUsuarios extends AbstractTableModel{
    private String [][] data;

    public String[][] getData() {
        return data;
    }

    public void setData(String[][] data) {
        this.data = data;
    }
    

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return data [rowIndex][0];
            case 1:
                return data [rowIndex][1];
            case 2:
                return data [rowIndex][2];
            case 3:
                return data [rowIndex][3];    
            default:
                return null;
        }         
    }

    @Override
    public String getColumnName(int column) {
              switch (column) {
            case 0:
                return "CLiente";
            case 1:
                return "Telefono";
            case 2:
                return "Nro Cuenta";
            case 3:
                return "Nro Cuenta";    
            default:
                return null;
        }  
        
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    
}
