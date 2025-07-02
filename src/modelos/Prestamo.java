package modelos;

public class Prestamo {
    private int id;
    private Cliente cliente;
    private double monto;
    private String destino;
    private int cuotas;
    private boolean diferido;
    private double interes;
    private boolean cuotaAvanzada; //
    private String estado;



    public Prestamo(int id, Cliente cliente, double monto, String destino, int cuotas) {
        this.id = id;
        this.cliente = cliente;
        this.monto = monto;
        this.destino = destino;
        this.cuotas = cuotas;
        this.diferido = false;
        this.interes = 0.0;
        this.cuotaAvanzada = false; // por defecto, aún no se ha pagado nada
    }

    // --- Getters y Setters ---
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

    public boolean getCuotaAvanzada() {
        return cuotaAvanzada;
    }

    public void setDiferido(boolean diferido) {
        this.diferido = diferido;
    }

    public void setInteres(double interes) {
        this.interes = interes;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setCuotaAvanzada(boolean cuotaAvanzada) {
        this.cuotaAvanzada = cuotaAvanzada;
    }

    // --- Métodos de cálculo ---
    public double calcularTotalConInteres() {
        return monto + (monto * interes);
    }

    public double calcularCuotaMensual() {
        if (cuotas == 0) return 0;
        return calcularTotalConInteres() / cuotas;
    }

    @Override
    public String toString() {
        return "Préstamo ID: " + id +
                " | Cliente: " + cliente.getId() + " - " + cliente.getNombre() +
                " | $" + monto +
                " | Cuotas: " + cuotas;
    }
    public String resumen() {
        return "ID: " + id +
                " | Cliente: " + cliente.getNombre() + " " + cliente.getApellido() +
                " | Monto: $" + String.format("%.2f", monto) +
                " | Cuotas: " + cuotas +
                " | Interés: " + (interes * 100) + "%";
    }
    private int cuotasPagadas = 0;

    public void pagarCuota() {
        if (cuotasPagadas < cuotas) {
            cuotasPagadas++;
        }
    }

    public boolean estaPagado() {
        return cuotasPagadas >= cuotas;
    }

    public int getCuotasPagadas() {
        return cuotasPagadas;
    }

}
