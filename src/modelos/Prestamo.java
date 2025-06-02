package modelos;

public class Prestamo implements Comparable<Prestamo> {
    private int id;
    private int idCliente;
    private double monto;
    private String destino;

    public Prestamo(int id, int idCliente, double monto, String destino) {
        this.id = id;
        this.idCliente = idCliente;
        this.monto = monto;
        this.destino = destino;
    }

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

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    @Override
    public int compareTo(Prestamo otro) {
        // Orden descendente por monto (de mayor a menor)
        return Double.compare(otro.monto, this.monto);
    }
}
