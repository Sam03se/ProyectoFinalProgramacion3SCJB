package modelos;

public class Prestamo implements Comparable<Prestamo> {
    private int id;
    private int idCliente;
    private double monto;
    private String destino;
    private int cuotas;
    private boolean diferido;
    private double interes; // % expresado como decimal (ej: 0.12 = 12%)

    public Prestamo(int id, int idCliente, double monto, String destino, int cuotas) {
        this.id = id;
        this.idCliente = idCliente;
        this.monto = monto;
        this.destino = destino;
        this.cuotas = cuotas;
        this.diferido = false;
        this.interes = 0.0;
    }

    // Constructor con opción de diferido e interés
    public Prestamo(int id, int idCliente, double monto, String destino, int cuotas, boolean diferido, double interes) {
        this.id = id;
        this.idCliente = idCliente;
        this.monto = monto;
        this.destino = destino;
        this.cuotas = cuotas;
        this.diferido = diferido;
        this.interes = interes;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public double getMonto() {
        return monto;
    }

    public String getDestino() {
        return destino;
    }

    public int getCuotas() {
        return cuotas;
    }

    public boolean esDiferido() {
        return diferido;
    }

    public double getInteres() {
        return interes;
    }

    public int getNumeroCuotas() {
        return cuotas;
    }

    // Cálculo de valores
    public double calcularTotalConInteres() {
        return monto + (monto * interes);
    }

    public double calcularValorCuota() {
        return calcularTotalConInteres() / cuotas;
    }

    @Override
    public int compareTo(Prestamo otro) {
        return Double.compare(otro.monto, this.monto); // mayor monto = mayor prioridad
    }

    @Override
    public String toString() {
        return "Préstamo ID: " + id + " | Cliente: " + idCliente + " | $" + monto;
    }
}
