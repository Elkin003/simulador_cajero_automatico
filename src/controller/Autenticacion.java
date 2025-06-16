/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author Usuario
 */
public class Autenticacion {

    public boolean iniciarSesion(String usuarioIngresado, String pinIngresado) {

        String[] usuarios = {"Erick", "Andres", "Elkin"};
        String[] pines = {"1234", "5678", "9012"};

        for (int i = 0; i < usuarios.length; i++) {
            if (usuarios[i].equals(usuarioIngresado) && pines[i].equals(pinIngresado)) {
                return true;
            }
        }
        return false;
    }

}
