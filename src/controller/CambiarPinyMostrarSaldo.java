package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CambiarPinyMostrarSaldo {

    private String path = "data";
    private String file_name = "Usuarios.dat";

    // Obtener saldo del usuario autenticado
    public double obtenerSaldo(String[] usuarioAutenticado) {
        if (usuarioAutenticado != null) {
            String saldoArchivo = usuarioAutenticado[4];
            try {
                double saldoObtenido = Double.parseDouble(saldoArchivo);
                return saldoObtenido;
            } catch (NumberFormatException e) {
                System.out.println(e);
                return 0;
            }
        } else {
            return 0;
        }
    }

    // Cambiar PIN y actualizar el archivo
    public String cambiarPin(String[] usuarioAutenticado, String pinIngresado, String pinNuevo) {
        if (usuarioAutenticado != null) {
            String pinActual = usuarioAutenticado[3];
            if (pinActual.equals(pinIngresado)) {
                boolean exito = actualizarPin(usuarioAutenticado[2], pinNuevo);
                if (exito) {
                    usuarioAutenticado[3] = pinNuevo;
                    System.out.println("PIN cambiado y guardado correctamente.");
                    return pinNuevo;
                } else {
                    System.out.println("Error al guardar el nuevo PIN.");
                    return pinActual;
                }
            } else {
                System.out.println("El PIN actual no coincide.");
                return pinActual;
            }
        } else {
            System.out.println("No hay usuario autenticado.");
            return null;
        }
    }

    // Método para actualizar el PIN en el archivo Usuarios.dat
    public boolean actualizarPin(String nroCuenta, String nuevoPin) {
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
                if (partes.length >= 5 && partes[2].equals(nroCuenta)) {
                    partes[3] = nuevoPin;
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
            System.out.println("Error al actualizar PIN: " + e);
            return false;
        }
    }

}
