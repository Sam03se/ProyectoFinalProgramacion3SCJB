package run;

import java.util.Stack;

public class HistorialOperaciones {
    private static final Stack<String> historial = new Stack<>();

    public static void agregar(String operacion) {
        historial.push(operacion);
    }

    public static void mostrarHistorialConsola() {
        if (historial.isEmpty()) {
            System.out.println("Historial vacío.");
        } else {
            System.out.println("Historial:");
            for (int i = historial.size() - 1; i >= 0; i--) {
                System.out.println(historial.get(i));
            }
        }
    }

    public static Stack<String> obtenerHistorial() {
        return new Stack<>() {{
            addAll(historial);
        }};
    }

    public static void limpiar() {
        historial.clear();
    }
}
