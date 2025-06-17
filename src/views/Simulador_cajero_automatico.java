/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

import controller.Autenticacion;
import controller.Operacionescajero;
import controller.Otros;
import java.util.Scanner;

/**
 *
 * @author Usuario
 */
public class Simulador_cajero_automatico {

    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);
        Autenticacion auten = new Autenticacion();
        Operacionescajero opera = new Operacionescajero();
        Otros otros = new Otros();

        double saldo = 1000;
        String pinActual = " ";

        boolean acceso = false;
        int intentos = 0;

        while (intentos < 3 && !acceso) {
            System.out.print("Usuario: ");
            String usuario = sc.nextLine();
            System.out.print("PIN: ");
            String pinIngresado = sc.nextLine();

            if (auten.iniciarSesion(usuario, pinIngresado)) {
                acceso = true;
                pinActual = pinIngresado;
                System.out.println("¡Acceso concedido!");
            } else {
                System.out.println("Usuario o PIN incorrectos.");
                intentos++;
            }
        }

        if (!acceso) {
            System.out.println("Ha superado el número máximo de intentos. Tarjeta bloqueada.");
            return;
        }

        int opcion = 0;

        do {
            System.out.println("\n--- CAJERO AUTOMÁTICO ---");
            System.out.println("1. Consultar saldo");
            System.out.println("2. Depositar");
            System.out.println("3. Retirar");
            System.out.println("4. Cambiar PIN");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {

                case 1:
                    otros.consultarSaldo(saldo);
                    break;

                case 2:
                    System.out.print("Ingrese monto a depositar: ");
                    double deposito = sc.nextDouble();
                    saldo = opera.depositar(saldo, deposito);
                    break;

                case 3:
                    System.out.print("Ingrese monto a retirar: ");
                    double retiro = sc.nextDouble();
                    saldo = opera.retirar(saldo, retiro);
                    break;

                case 4:
                    sc.nextLine();
                    System.out.println("Ingrese su PIN: ");
                    String pinViejo = sc.nextLine();
                    System.out.println("Ingrese su nuevo PIN: ");
                    String pinNuevo = sc.nextLine();
                    pinActual = otros.cambiarPin(pinActual, pinViejo, pinNuevo);
                    break;

                case 5:
                    System.out.println("Gracias por usar el cajero.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 5);
        System.out.println("Finalizado");
    }
}
