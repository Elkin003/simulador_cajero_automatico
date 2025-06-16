package controller;



public class Operacionescajero {

    public double depositar(double saldoActual, double monto) {
        if (monto > 0) {
            saldoActual += monto;
        }
        return saldoActual;
    }

    public double retirar(double saldoActual, double monto) {
        if (monto > 0 && monto <= saldoActual) {
            saldoActual -= monto;
        } else {
            System.out.println("Fondos insuficientes o monto inválido.");
        }
        return saldoActual;
    }

}