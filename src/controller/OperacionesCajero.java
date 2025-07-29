/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.CambiarPinyMostrarSaldo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author asus
 */
public class OperacionesCajero {
    
    private String path = "data";
    private String file_name = "Usuarios.dat";
    
    CambiarPinyMostrarSaldo obSaldo = new CambiarPinyMostrarSaldo();
    
    
    public double depositar (String [] usuarioAutenticado, double monto){
        if (usuarioAutenticado != null && monto > 0){
             double saldoActual = obSaldo.obtenerSaldo(usuarioAutenticado);
            double nuevoSaldo = saldoActual + monto;
            boolean exito = actualizarSaldo(usuarioAutenticado[3], nuevoSaldo);
            if (exito) {
                usuarioAutenticado[5] = String.valueOf(nuevoSaldo);
                System.out.println("Depósito exitoso. Nuevo saldo: " + nuevoSaldo);
                return nuevoSaldo;
                
            } else {
                System.out.println("Error al actualizar saldo en el archivo.");
            }
        }
        return -1;
    }  
     public double retirar(String[] usuarioAutenticado, double monto) {
        if (usuarioAutenticado != null && monto > 0) {
            double saldoActual = obSaldo.obtenerSaldo(usuarioAutenticado);
            if (monto > saldoActual) {
                System.out.println("Fondos insuficientes.");
                return -1;
            }
            double nuevoSaldo = saldoActual - monto;
            boolean exito = actualizarSaldo(usuarioAutenticado[3], nuevoSaldo);
            if (exito) {
                usuarioAutenticado[5] = String.valueOf(nuevoSaldo);
                System.out.println("Retiro exitoso. Nuevo saldo: " + nuevoSaldo);
                return nuevoSaldo;
            } else {
                System.out.println("Error al actualizar saldo en el archivo.");
            }
        }
        return -1;
    }
    public boolean actualizarSaldo(String nroCuenta, double nuevoSaldo) {
        try {
            File file = new File(path + File.separatorChar + file_name);
            if (!file.exists()) {
                System.out.println("Archivo no encontrado");
                return false;
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\t");
                if (partes.length >= 5 && partes[3].equals(nroCuenta)) {
                    partes[5] = String.valueOf(nuevoSaldo);
                    linea = String.join("\t", partes);
                }
                sb.append(linea).append("\n");
            }
            br.close();

            FileWriter fw = new FileWriter(file, false);
            fw.write(sb.toString());
            fw.close();

            return true;
        } catch (IOException e) {
            System.out.println("Error al actualizar saldo: " + e);
            return false;
        }
    }

}
