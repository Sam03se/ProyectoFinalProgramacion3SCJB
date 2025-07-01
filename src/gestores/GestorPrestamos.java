package gestores;

import modelos.Cliente;
import modelos.Prestamo;

import java.util.*;

public class GestorPrestamos {
    private Queue<Prestamo> colaSolicitudes = new LinkedList<>();
    private PriorityQueue<Prestamo> colaPrioridad = new PriorityQueue<>();
    private List<String> historialOperaciones = new ArrayList<>();
    private List<Prestamo> prestamosAprobados = new ArrayList<>();
    private int idPrestamoCounter = 1;

    private final Map<Integer, Double> tablaIntereses = Map.ofEntries(
            Map.entry(3, 0.03), Map.entry(6, 0.06), Map.entry(9, 0.09),
            Map.entry(12, 0.12), Map.entry(18, 0.15), Map.entry(24, 0.18),
            Map.entry(36, 0.21), Map.entry(48, 0.25), Map.entry(60, 0.30),
            Map.entry(72, 0.35)
    );

    // Evaluación simple del cliente para demo IA
    public boolean clienteEsElegible(Cliente cliente) {
        return cliente.getEdad() >= 21 &&
                cliente.getIngresoMensual() >= 350 &&
                cliente.getAntiguedadLaboral() >= 1;
    }

    // Registrar solicitud
    public boolean solicitarPrestamo(Cliente cliente, double monto, String destino, int cuotas) {
        if (!clienteEsElegible(cliente)) {
            historialOperaciones.add("❌ Rechazado: Cliente " + cliente.getId() + " no cumple condiciones.");
            return false;
        }

        double interes = obtenerInteresPorMeses(cuotas);
        Prestamo prestamo = new Prestamo(idPrestamoCounter++, cliente, monto, destino, cuotas, false, interes);

        colaSolicitudes.add(prestamo);
        historialOperaciones.add("📝 Solicitud registrada: " + prestamo);
        return true;
    }

    // Aprobar por ID
    public boolean aprobarPrestamoPorId(int id) {
        Iterator<Prestamo> iterator = colaSolicitudes.iterator();
        while (iterator.hasNext()) {
            Prestamo p = iterator.next();
            if (p.getId() == id) {
                iterator.remove();
                colaPrioridad.add(p);
                prestamosAprobados.add(p);
                historialOperaciones.add("✅ Aprobado: " + p);
                return true;
            }
        }
        return false;
    }

    public List<Prestamo> obtenerSolicitudesPendientes() {
        return new ArrayList<>(colaSolicitudes);
    }

    public List<Prestamo> obtenerPrestamosAprobados() {
        return new ArrayList<>(prestamosAprobados);
    }

    public List<String> getHistorialOperaciones() {
        return new ArrayList<>(historialOperaciones);
    }

    public PriorityQueue<Prestamo> obtenerColaPrioridad() {
        return new PriorityQueue<>(colaPrioridad);
    }

    public double obtenerInteresPorMeses(int meses) {
        return tablaIntereses.getOrDefault(meses, 0.15);
    }

    public void limpiarDatos() {
        colaSolicitudes.clear();
        colaPrioridad.clear();
        historialOperaciones.clear();
        prestamosAprobados.clear();
        idPrestamoCounter = 1;
    }
}
