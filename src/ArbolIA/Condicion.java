package ArbolIA;

import modelos.Cliente;

public interface Condicion {
    boolean evaluar(Cliente cliente);
    String descripcion();
}
