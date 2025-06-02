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
        if (cliente == null || monto <= 0 || destino.isEmpty()) {
            return null;
        }
        Prestamo p = new Prestamo(contadorId++, cliente.getId(), monto, destino);
        colaSolicitudes.offer(p);
        HistorialOperaciones.agregar("Solicitud: $" + monto + " para " + destino + " [Cliente ID " + cliente.getId() + "]");
        return p;
    }

    public static Prestamo aprobarPrestamo() {
        if (colaSolicitudes.isEmpty()) return null;
        Prestamo aprobado = colaSolicitudes.poll();
        colaPrioridad.offer(aprobado);
        HistorialOperaciones.agregar("Préstamo aprobado: $" + aprobado.getMonto() + " para " + aprobado.getDestino());
        return aprobado;
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
