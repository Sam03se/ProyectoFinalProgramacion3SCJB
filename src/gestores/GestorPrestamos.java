package gestores;

import modelos.Prestamo;

import java.util.*;

public class GestorPrestamos {
    private Queue<Prestamo> colaSolicitudes = new LinkedList<>();
    private PriorityQueue<Prestamo> colaPrioridad = new PriorityQueue<>();
    private List<String> historialOperaciones = new ArrayList<>();
    private List<Prestamo> prestamosAprobados = new ArrayList<>();
    private int idPrestamoCounter = 1;

    // Tabla de intereses por meses
    private final Map<Integer, Double> tablaIntereses = Map.ofEntries(
            Map.entry(3, 0.03), Map.entry(6, 0.06), Map.entry(9, 0.09),
            Map.entry(12, 0.12), Map.entry(18, 0.15), Map.entry(24, 0.18),
            Map.entry(36, 0.21), Map.entry(48, 0.25), Map.entry(60, 0.30),
            Map.entry(72, 0.35)
    );

    // Registrar solicitud de préstamo
    public void solicitarPrestamo(int idCliente, double monto, String destino, int cuotas) {
        Prestamo prestamo = new Prestamo(idPrestamoCounter++, idCliente, monto, destino, cuotas);
        colaSolicitudes.add(prestamo);
        historialOperaciones.add("📝 Solicitud registrada: Cliente " + idCliente + ", $" + monto + ", destino: " + destino + ", cuotas: " + cuotas);
    }

    // Aprobar préstamo por ID
    public boolean aprobarPrestamoPorId(int id) {
        Iterator<Prestamo> iterator = colaSolicitudes.iterator();
        while (iterator.hasNext()) {
            Prestamo p = iterator.next();
            if (p.getId() == id) {
                iterator.remove();
                colaPrioridad.add(p);
                prestamosAprobados.add(p);
                historialOperaciones.add("✅ Préstamo aprobado: ID " + p.getId() + " para Cliente " + p.getIdCliente());
                return true;
            }
        }
        return false;
    }

    // Obtener préstamos pendientes (cola de solicitudes)
    public List<Prestamo> obtenerPrestamosPendientes() {
        return new ArrayList<>(colaSolicitudes);
    }

    // Alias para compatibilidad
    public List<Prestamo> obtenerSolicitudesPendientes() {
        return obtenerPrestamosPendientes();
    }

    // Obtener préstamos aprobados
    public List<Prestamo> obtenerPrestamosAprobados() {
        return prestamosAprobados;
    }

    // Obtener historial
    public List<String> getHistorialOperaciones() {
        return historialOperaciones;
    }

    // Obtener cola de prioridad
    public PriorityQueue<Prestamo> obtenerColaPrioridad() {
        return new PriorityQueue<>(colaPrioridad);
    }

    // Calcular interés por meses
    public double obtenerInteresPorMeses(int meses) {
        return tablaIntereses.getOrDefault(meses, 0.0);
    }

    // Reset de todos los datos (para pruebas o reinicio)
    public void limpiarDatos() {
        colaSolicitudes.clear();
        colaPrioridad.clear();
        historialOperaciones.clear();
        prestamosAprobados.clear();
        idPrestamoCounter = 1;
    }
}
