package ArbolIA;

import modelos.Cliente;

public class ArbolEvaluador {
    private Nodo raiz;

    public ArbolEvaluador() {
        construirArbol();
    }

    private void construirArbol() {
        Nodo aprobado = new Nodo("Aprobado");
        Nodo rechazado = new Nodo("Rechazado");

        Nodo nodoAntiguedad = new Nodo(
                new CondicionAntiguedadMayor(1), aprobado, rechazado
        );

        Nodo nodoIngreso = new Nodo(
                new CondicionIngresoMayor(350), nodoAntiguedad, rechazado
        );

        Nodo nodoEdad = new Nodo(
                new CondicionEdadMayor(20), nodoIngreso, rechazado
        );

        raiz = nodoEdad;
    }

    public String evaluarCliente(Cliente cliente, StringBuilder trazabilidad) {
        trazabilidad.setLength(0);  // Limpiar trazabilidad anterior
        return raiz.predecir(cliente, trazabilidad);
    }
}
