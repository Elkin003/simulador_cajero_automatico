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
public class ModelotablasUsuarios extends AbstractTableModel {

    private String[][] data;

    public String[][] getData() {
        return data;
    }

    public void setData(String[][] data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        if (data == null) {
            return 0;
        } else {
            return data.length;
        }
    }

    @Override
    public int getColumnCount() {
        if (data == null) {
            return 0;
        } else {
            return data[0].length;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return data[rowIndex][0];
            case 1:
                return data[rowIndex][1];
            case 2:
                return data[rowIndex][2];
            case 3:
                return data[rowIndex][3];
            case 4:
                return data[rowIndex][4];
            case 5:
                return data[rowIndex][5];
            case 6:
                String estado = data[rowIndex][6];
                if (estado.equals("true")){
                    return "Activo";
                } else if (estado.equals("false")){
                    return "Bloqueado";
                }
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Cliente";
            case 1:
                return "Teléfono";
            case 2:
                return "Cédula";
            case 3:
                return "Nro Cuenta";
            case 4:
                return "PIN";
            case 5:
                return "Saldo";
            case 6:
                return "Estado";
            default:
                return null;
        }

    }

}
