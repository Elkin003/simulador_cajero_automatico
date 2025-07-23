/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author Usuario
 */
public class Autenticacion {

    // Leer archivos
    private String path = "data";

    public void save(String text, String name_file) throws IOException {
        FileWriter file = new FileWriter(path + File.separatorChar + name_file, true);
        file.write(text);
        file.close();
    }
    //Registrar usuarios 
    private String file_name = "Usuarios.dat";

    public boolean registrarUsuario(String cliente, String telefono, String pin,
            String nroCuenta, String saldo) {
        String data = cliente + "\t" + telefono + "\t" + nroCuenta + "\t" + pin + "\t" + saldo + "\t" + true + "\n";
        try {
            save(data, file_name);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public String[][] listAll(String name_file) throws IOException {
        String[][] data = null;
        Integer filas = countRegister(name_file);
        if (filas > 0) {
            Integer col = countColumn(name_file);
            data = new String[filas][col];
            FileReader file = new FileReader(path + File.separatorChar + name_file);
            BufferedReader br = new BufferedReader(file);
            String linea = br.readLine();
            int fil = 0;
            while (linea != null) {
                String[] columas = linea.split("\t");
                for (int j = 0; j < columas.length; j++) {
                    data[fil][j] = columas[j];
                }
                fil++;
                linea = br.readLine();
            }
            file.close();
            br.close();
        }

        System.out.println();
        return data;
    }

    private int countRegister(String name_file) throws IOException {
        FileReader file = new FileReader(path + File.separatorChar + name_file);
        BufferedReader br = new BufferedReader(file);
        int lines = (int) br.lines().count();
        file.close();
        br.close();
        return lines;
    }

    private int countColumn(String name_file) throws IOException {
        FileReader file = new FileReader(path + File.separatorChar + name_file);
        BufferedReader br = new BufferedReader(file);
        String line = br.readLine();
        file.close();
        br.close();
        return line.split("\t").length;
    }

    public String[][] listar() {
        try {
            return listAll(file_name);
        } catch (Exception e) {
            System.out.println("Error en listar " + e);
            return null;
        }

    }

    //generar pin
    public String generarPin() {
        Random r = new Random();
        return String.format("%06d", r.nextInt(1_000_000));
    }

    public long generarNroCuenta() {
        Random r = new Random();
        long min = 1000000000L;
        long max = 9999999999L;
        return min + (long) (r.nextDouble() * (max - min + 1));
    }

    public String[] autenticar(String nroCuentaIngresado, String pinIngresado) {
        try {
            String[][] usuarios = listar();
            if (usuarios != null) {
                for (int i = 0; i < usuarios.length; i++) {
                    String nroCuenta = usuarios[i][2];
                    String pin = usuarios[i][3];
                    boolean activo = Boolean.parseBoolean(usuarios[i][5]);

                    if (nroCuenta.equals(nroCuentaIngresado)) {
                        if (!activo) {
                            System.out.println("La cuenta está bloqueada.");
                            return new String[]{"BLOQUEADA"};
                        }

                        if (pin.equals(pinIngresado)) {
                            System.out.println("Autenticación exitosa.");
                            return usuarios[i];

                        } else {
                            System.out.println("PIN incorrecto.");
                            return new String[]{"PIN INCORRECTO"};
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al autenticar: " + e);
        }
        return new String[]{"NO ENCONTRADA"};
    }

    public void actualizarUsuario(String[][] data) throws IOException {
        FileWriter file = new FileWriter(path + File.separatorChar + file_name, false);
        for (int i = 0; i < data.length; i++) {
            String linea = "";
            for (int j = 0; j < data[i].length; j++) {
                linea += data[i][j];
                if (j < data[i].length - 1) {
                    linea += "\t";
                }
            }
            file.write(linea + "\n");
        }
        file.close();
    }

    public void bloquearCuenta(String nroCuenta) {
        try {
            String[][] usuarios = listar();
            for (int i = 0; i < usuarios.length; i++) {
                if (usuarios[i][2].equals(nroCuenta)) {
                    usuarios[i][5] = "false";
                    break;
                }
            }
            actualizarUsuario(usuarios); 
        } catch (Exception e) {
            System.out.println("Error al bloquear cuenta: " + e);
        }
    }

}
