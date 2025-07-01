package ArbolIA;

import modelos.Cliente;

public class CondicionEdadMayor implements Condicion {
    private final int umbral;

    public CondicionEdadMayor(int umbral) {
        this.umbral = umbral;
    }

    @Override
    public boolean evaluar(Cliente cliente) {
        return cliente.getEdad() > umbral;
    }

    @Override
    public String descripcion() {
        return "¿Edad mayor a " + umbral + "?";
    }
}
