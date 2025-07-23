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
        long max = 9999999999L;
        return min + (long) (r.nextDouble() * (max - min + 1));
    }

    public String[] autenticar(String nroCuentaIngresado, String pinIngresado) {
        int intentos = 0; // Variable local para contar intentos
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
                            return null;
                        }

                        while (intentos < 3) {
                            if (pin.equals(pinIngresado)) {
                                System.out.println("Autenticación exitosa.");
                                return usuarios[i];
                            } else {
                                intentos++;
                                System.out.println("PIN incorrecto. Intento " + intentos + " de 3.");
                                if (intentos >= 3) {
                                    usuarios[i][5] = "false";
                                    actualizarUsuario(usuarios);
                                    System.out.println("Cuenta bloqueada por 3 intentos fallidos.");
                                    return null;
                                }

                                // Simulando una nueva entrada de PIN (porque no hay UI aquí)
                                System.out.println("Vuelve a ingresar el PIN:");
                                // Aquí deberías recibir el nuevo PIN, por ejemplo, desde consola o GUI
                                return null; // Como no tenemos forma de recibir nuevo PIN aquí
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al autenticar: " + e);
        }
        return null;
    }

    public void actualizarUsuario(String[][] data) throws IOException {
        FileWriter file = new FileWriter(path + File.separatorChar + file_name, false); // Sobrescribir archivo
        for (int i = 0; i < data.length; i++) {
            String linea = "";
            for (int j = 0; j < data[i].length; j++) {
                linea += data[i][j];
                if (j < data[i].length - 1) {
                    linea += "\t"; // Agregar tabulaciones entre columnas
                }
            }
            file.write(linea + "\n"); // Escribir una línea por usuario
        }
        file.close();
    }
    
     public Boolean validarCedula(String x) {
        int suma = 0;
        if (x.length() == 9) {
            
            return false;
        } else {
            int a[] = new int[x.length() / 2];
            int b[] = new int[(x.length() / 2)];
            int c = 0;
            int d = 1;
            for (int i = 0; i < x.length() / 2; i++) {
                a[i] = Integer.parseInt(String.valueOf(x.charAt(c)));
                c = c + 2;
                if (i < (x.length() / 2) - 1) {
                    b[i] = Integer.parseInt(String.valueOf(x.charAt(d)));
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
            if ((dec - suma) == Integer.parseInt(String.valueOf(x.charAt(x.length() - 1)))) {
                return true;
            } else if (suma % 10 == 0 && x.charAt(x.length() - 1) == '0') {
                return true;
            } else {
                return false;
            }

        }
    }

}
