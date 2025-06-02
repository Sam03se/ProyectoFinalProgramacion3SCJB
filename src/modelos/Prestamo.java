package modelos;

public class Prestamo implements Comparable<Prestamo> {
    private int id;
    private int idCliente;
    private double monto;
    private String destino;

    // Nuevos atributos para préstamo diferido
    private boolean esDiferido;
    private int numeroCuotas;
    private double interes; // 0.12 = 12%

    public Prestamo(int id, int idCliente, double monto, String destino) {
        this.id = id;
        this.idCliente = idCliente;
        this.monto = monto;
        this.destino = destino;
        this.esDiferido = false;
        this.numeroCuotas = 0;
        this.interes = 0.0;
    }

    // Getters
    public int getId() { return id; }
    public int getIdCliente() { return idCliente; }
    public double getMonto() { return monto; }
    public String getDestino() { return destino; }
    public boolean esDiferido() { return esDiferido; }
    public int getNumeroCuotas() { return numeroCuotas; }
    public double getInteres() { return interes; }

    // Setters
    public void setMonto(double monto) { this.monto = monto; }
    public void setDestino(String destino) { this.destino = destino; }
    public void setDiferido(boolean esDiferido) { this.esDiferido = esDiferido; }
    public void setNumeroCuotas(int numeroCuotas) { this.numeroCuotas = numeroCuotas; }
    public void setInteres(double interes) { this.interes = interes; }

    // Cálculo del total con interés
    public double calcularTotalConInteres() {
        return monto + (monto * interes);
    }

    // Cálculo del valor de cada cuota (o total si no es diferido)
    public double calcularValorCuota() {
        if (!esDiferido || numeroCuotas <= 0) {
            return calcularTotalConInteres(); // Pago único
        }
        return calcularTotalConInteres() / numeroCuotas;
    }

    @Override
    public int compareTo(Prestamo otro) {
        return Double.compare(otro.monto, this.monto); // orden descendente por monto
    }
}
