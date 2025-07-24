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

    public boolean registrarUsuario(String cliente, String telefono, String cedula, String pin,
            String nroCuenta, String saldo) {
        String data = cliente + "\t" + telefono + "\t" + cedula + "\t" + nroCuenta + "\t" + pin + "\t" + saldo + "\t" + true + "\n";
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
        long max = 10000000000L;
        return  r.nextLong(min, max);
    }

    public String[] autenticar(String nroCuentaIngresado, String pinIngresado) {
        try {
            String[][] usuarios = listar();
            if (usuarios != null) {
                for (int i = 0; i < usuarios.length; i++) {
                    String nroCuenta = usuarios[i][3];
                    String pin = usuarios[i][4];
                    boolean activo = Boolean.parseBoolean(usuarios[i][6]);

                    if (nroCuenta.equals(nroCuentaIngresado)) {
                        if (!activo) {
                            return new String[]{"BLOQUEADA"};
                        }

                        if (pin.equals(pinIngresado)) {
                            return usuarios[i];

                        } else {
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
                if (usuarios[i][3].equals(nroCuenta)) {
                    usuarios[i][6] = "false";
                    break;
                }
            }
            actualizarUsuario(usuarios);
        } catch (Exception e) {
            System.out.println("Error al bloquear cuenta: " + e);
        }
    }

    public Boolean validarCedula(String cedula) {
        int suma = 0;
        
        if (cedula.length() != 10) {
            return false;
            
        } else {
            int a[] = new int[cedula.length() / 2];
            int b[] = new int[cedula.length() / 2];
            int c = 0;
            int d = 1;
            for (int i = 0; i < cedula.length() / 2; i++) {
                a[i] = Integer.parseInt(String.valueOf(cedula.charAt(c)));
                c = c + 2;
                if (i < (cedula.length() / 2) - 1) {
                    b[i] = Integer.parseInt(String.valueOf(cedula.charAt(d)));
                    d = d + 2;
                }
            }

            for (int i = 0; i < a.length; i++) {
                a[i] = a[i] * 2;
                if (a[i] > 9) {
                    a[i] = a[i] - 9;
                }
                suma = suma + a[i] + b[i];
            }
            int aux = suma / 10;
            int dec = (aux + 1) * 10;
            if ((dec - suma) == Integer.parseInt(String.valueOf(cedula.charAt(cedula.length() - 1)))) {
                return true;
            } else if (suma % 10 == 0 && cedula.charAt(cedula.length() - 1) == '0') {
                return true;
            } else {
                return false;
            }
        }
    }
}
