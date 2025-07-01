package modelos;

public class Prestamo implements Comparable<Prestamo> {
    private int id;
    private Cliente cliente;
    private double monto;
    private String destino;
    private int cuotas;
    private boolean diferido;
    private double interes;

    public Prestamo(int id, Cliente cliente, double monto, String destino, int cuotas) {
        if (monto <= 0 || cuotas <= 0 || cliente == null || destino.isEmpty()) {
            throw new IllegalArgumentException("Datos del préstamo inválidos.");
        }

        this.id = id;
        this.cliente = cliente;
        this.monto = monto;
        this.destino = destino;
        this.cuotas = cuotas;
        this.diferido = false;
        this.interes = 0.0;
    }

    public Prestamo(int id, Cliente cliente, double monto, String destino, int cuotas, boolean diferido, double interes) {
        this(id, cliente, monto, destino, cuotas);
        this.diferido = diferido;
        this.interes = interes;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
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

    public void setInteres(double interes) {
        this.interes = interes;
    }

    // Fórmula financiera real
    public double calcularValorCuotaAvanzada() {
        double tasaMensual = interes / cuotas;
        return (monto * tasaMensual * Math.pow(1 + tasaMensual, cuotas)) / (Math.pow(1 + tasaMensual, cuotas) - 1);
    }

    public double calcularTotalConInteres() {
        return calcularValorCuotaAvanzada() * cuotas;
    }

    @Override
    public int compareTo(Prestamo otro) {
        return Double.compare(otro.monto, this.monto); // mayor monto = mayor prioridad
    }

    @Override
    public String toString() {
        return "Préstamo ID: " + id +
                " | Cliente: " + cliente.getId() + " - " + cliente.getNombre() +
                " | $" + monto + " | Cuotas: " + cuotas;
    }
}
