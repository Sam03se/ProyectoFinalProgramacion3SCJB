package gestores;

import modelos.Cliente;
import modelos.Prestamo;
import ArbolIA.ArbolEvaluador;
import run.ComprobantePrestamo;

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

    private final ArbolEvaluador evaluador = new ArbolEvaluador();
    private GestorClientes gestorClientes; // Para vincular cliente al préstamo

    public void setGestorClientes(GestorClientes gestorClientes) {
        this.gestorClientes = gestorClientes;
    }

    // Registrar solicitud de préstamo con IA y comprobante
    public boolean solicitarPrestamo(Cliente cliente, double monto, String destino, int cuotas) {
        StringBuilder trazabilidad = new StringBuilder();
        String resultadoIA = evaluador.evaluarCliente(cliente, trazabilidad);

        historialOperaciones.add("📊 Evaluación IA cliente " + cliente.getId() + ":\n" + trazabilidad);

        if (!resultadoIA.equalsIgnoreCase("Aprobado")) {
            historialOperaciones.add("❌ Rechazado por IA: Cliente " + cliente.getId());
            return false;
        }

        double interes = obtenerInteresPorMeses(cuotas);
        Prestamo prestamo = new Prestamo(idPrestamoCounter++, cliente, monto, destino, cuotas, false, interes);

        colaSolicitudes.add(prestamo);
        historialOperaciones.add("📝 Solicitud registrada: " + prestamo);

        ComprobantePrestamo.guardarComoTxt(prestamo);
        return true;
    }

    // Solo borra el historial, no toca datos
    public void limpiarSoloHistorial() {
        historialOperaciones.clear();
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
                historialOperaciones.add("✅ Aprobado manualmente: " + p);
                return true;
            }
        }
        return false;
    }

    public List<Prestamo> obtenerSolicitudesPendientes() {
        List<Prestamo> lista = new ArrayList<>();
        for (Prestamo p : colaSolicitudes) {
            if (p.getCliente() == null && gestorClientes != null) {
                p.setCliente(gestorClientes.buscarClientePorId(p.getIdCliente()));
            }
            lista.add(p);
        }
        return lista;
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
        return tablaIntereses.getOrDefault(meses, 0.15); // valor por defecto si no está
    }

    public void limpiarDatos() {
        colaSolicitudes.clear();
        colaPrioridad.clear();
        historialOperaciones.clear();
        prestamosAprobados.clear();
        idPrestamoCounter = 1;
    }
}
