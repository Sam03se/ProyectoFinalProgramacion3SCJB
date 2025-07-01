package ArbolIA;

import modelos.Cliente;

public class CondicionAntiguedadMayor implements Condicion {
    private final int umbral;

    public CondicionAntiguedadMayor(int umbral) {
        this.umbral = umbral;
    }

    @Override
    public boolean evaluar(Cliente cliente) {
        return cliente.getAntiguedadLaboral() > umbral;
    }

    @Override
    public String descripcion() {
        return "¿Antigüedad laboral mayor a " + umbral + " años?";
    }
}
