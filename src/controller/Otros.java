package controller;

public class Otros {

    public void consultarSaldo(double saldoActual) {
        System.out.println("Su saldo actual es: $" + saldoActual);
    }

    public String cambiarPin(String pinActual, String pinViejo, String pinNuevo) {
        if (pinActual.equals(pinViejo)) {
            return pinNuevo;
        } else {
            System.out.println("El PIN actual no coincide.");
            return pinActual;
        }
    }

}
