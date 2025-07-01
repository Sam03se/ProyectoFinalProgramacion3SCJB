package ArbolIA;

import modelos.Cliente;

public class Nodo {
    private Condicion condicion;
    private Nodo hijoSi;
    private Nodo hijoNo;
    private String resultado;

    // Nodo de decisión
    public Nodo(Condicion condicion, Nodo hijoSi, Nodo hijoNo) {
        this.condicion = condicion;
        this.hijoSi = hijoSi;
        this.hijoNo = hijoNo;
    }

    // Nodo hoja
    public Nodo(String resultado) {
        this.resultado = resultado;
    }

    public String predecir(Cliente cliente, StringBuilder trazabilidad) {
        if (resultado != null) {
            trazabilidad.append("→ Resultado final: ").append(resultado).append("\n");
            return resultado;
        }

        boolean eval = condicion.evaluar(cliente);
        trazabilidad.append(condicion.descripcion())
                .append(" → ").append(eval ? "Sí\n" : "No\n");

        return eval ? hijoSi.predecir(cliente, trazabilidad) :
                hijoNo.predecir(cliente, trazabilidad);
    }
}
