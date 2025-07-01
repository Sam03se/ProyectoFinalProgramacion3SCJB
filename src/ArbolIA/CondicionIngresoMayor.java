package ArbolIA;

import modelos.Cliente;

public class CondicionIngresoMayor implements Condicion {
    private final double umbral;

    public CondicionIngresoMayor(double umbral) {
        this.umbral = umbral;
    }

    @Override
    public boolean evaluar(Cliente cliente) {
        return cliente.getIngresoMensual() > umbral;
    }

    @Override
    public String descripcion() {
        return "¿Ingreso mensual mayor a $" + umbral + "?";
    }
}
