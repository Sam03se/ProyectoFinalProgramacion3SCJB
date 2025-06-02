package gestores;

import modelos.Cliente;
import modelos.Prestamo;
import run.HistorialOperaciones;

import java.util.*;

public class GestorPrestamos {
    private static final Queue<Prestamo> colaSolicitudes = new LinkedList<>();
    private static final PriorityQueue<Prestamo> colaPrioridad = new PriorityQueue<>();
    private static int contadorId = 1;

    public static Prestamo solicitarPrestamo(Cliente cliente, double monto, String destino) {
        return solicitarPrestamo(cliente, monto, destino, false, 0, 0.0);
    }

    public static Prestamo solicitarPrestamo(Cliente cliente, double monto, String destino,
                                             boolean diferido, int cuotas, double interes) {
        if (cliente == null || monto <= 0 || destino.isEmpty()) return null;

        Prestamo p = new Prestamo(contadorId++, cliente.getId(), monto, destino);
        p.setDiferido(diferido);
        p.setNumeroCuotas(cuotas);
        p.setInteres(interes);

        colaSolicitudes.offer(p);
        HistorialOperaciones.agregar("Solicitud: $" + monto + " para " + destino + " [Cliente ID " + cliente.getId() + "]");
        return p;
    }
    public static double calcularInteresPorCuotas(int cuotas) {
        if (cuotas <= 3) return 0.03;
        if (cuotas <= 6) return 0.06;
        if (cuotas <= 12) return 0.09;
        if (cuotas <= 24) return 0.18;
        if (cuotas <= 36) return 0.30;
        if (cuotas <= 48) return 0.45;
        if (cuotas <= 60) return 0.60;
        if (cuotas <= 72) return 0.72;
        return 0.80; // Más de 72
    }


    public static Prestamo aprobarPrestamo() {
        if (colaSolicitudes.isEmpty()) return null;
        Prestamo aprobado = colaSolicitudes.poll();
        colaPrioridad.offer(aprobado);
        HistorialOperaciones.agregar("Préstamo aprobado: $" + aprobado.getMonto() + " para " + aprobado.getDestino());
        return aprobado;
    }

    public static Prestamo aprobarPrestamoPorId(int idPrestamo) {
        Prestamo seleccionado = null;
        List<Prestamo> temp = new ArrayList<>();

        while (!colaSolicitudes.isEmpty()) {
            Prestamo p = colaSolicitudes.poll();
            if (p.getId() == idPrestamo) {
                seleccionado = p;
            } else {
                temp.add(p);
            }
        }

        // Restaurar la cola con los elementos no seleccionados
        colaSolicitudes.addAll(temp);

        if (seleccionado != null) {
            colaPrioridad.offer(seleccionado);
            HistorialOperaciones.agregar("Préstamo aprobado manualmente: $" + seleccionado.getMonto() + " para " + seleccionado.getDestino());
        }

        return seleccionado;
    }

    public static List<Prestamo> obtenerSolicitudesPendientes() {
        return new ArrayList<>(colaSolicitudes);
    }

    public static List<Prestamo> obtenerPrestamosPrioridad() {
        return new ArrayList<>(colaPrioridad);
    }

    public static void reiniciarPrestamos() {
        colaSolicitudes.clear();
        colaPrioridad.clear();
        contadorId = 1;
        HistorialOperaciones.agregar("Sistema de préstamos reiniciado.");
    }
}
