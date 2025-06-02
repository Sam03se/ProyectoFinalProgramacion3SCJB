package gestores;

import modelos.Prestamo;

import java.util.*;

public class GestorPrestamos {
    private static final Queue<Prestamo> solicitudesPendientes = new LinkedList<>();
    private static final PriorityQueue<Prestamo> prestamosAprobados = new PriorityQueue<>();
    private static final List<String> historialOperaciones = new ArrayList<>();
    private static int contadorId = 1;

    // ✅ Registra una solicitud de préstamo (pendiente)
    public static void solicitarPrestamo(int idCliente, double monto, String destino, int cuotas) {
        double interes = obtenerInteresPorMeses(cuotas);

        Prestamo nuevo = new Prestamo(generarNuevoId(), idCliente, monto, destino);
        nuevo.setDiferido(cuotas > 1);
        nuevo.setNumeroCuotas(cuotas);
        nuevo.setInteres(interes);

        solicitudesPendientes.offer(nuevo);

        historialOperaciones.add("📌 Solicitud registrada: Cliente ID " + idCliente +
                " | Monto: $" + monto +
                " | Destino: " + destino +
                " | Cuotas: " + cuotas +
                " | Interés estimado: " + (interes * 100) + "%");
    }

    // ✅ Aprueba un préstamo por ID
    public static Prestamo aprobarPrestamoPorId(int id) {
        Prestamo aprobado = null;
        List<Prestamo> temp = new ArrayList<>();

        while (!solicitudesPendientes.isEmpty()) {
            Prestamo p = solicitudesPendientes.poll();
            if (p.getId() == id) {
                aprobado = p;
            } else {
                temp.add(p);
            }
        }

        solicitudesPendientes.addAll(temp);

        if (aprobado != null) {
            prestamosAprobados.offer(aprobado);
            historialOperaciones.add("✅ Préstamo aprobado: Cliente ID " + aprobado.getIdCliente() +
                    " | Monto: $" + aprobado.getMonto() +
                    " | Cuotas: " + aprobado.getNumeroCuotas() +
                    " | Interés: " + (aprobado.getInteres() * 100) + "%");
        }

        return aprobado;
    }

    // ✅ Devuelve la lista de solicitudes pendientes
    public static List<Prestamo> obtenerSolicitudesPendientes() {
        return new ArrayList<>(solicitudesPendientes);
    }

    // ✅ Devuelve la lista de préstamos aprobados
    public static List<Prestamo> obtenerPrestamosAprobados() {
        return new ArrayList<>(prestamosAprobados);
    }

    // ✅ Cálculo del interés según las cuotas
    public static double obtenerInteresPorMeses(int meses) {
        if (meses <= 3) return 0.03;
        if (meses <= 6) return 0.06;
        if (meses <= 12) return 0.09;
        if (meses <= 24) return 0.12;
        if (meses <= 36) return 0.18;
        if (meses <= 48) return 0.25;
        if (meses <= 60) return 0.30;
        if (meses <= 72) return 0.36;
        return 0.40;
    }

    // ✅ Historial de eventos (solicitudes y aprobaciones)
    public static List<String> obtenerHistorial() {
        return new ArrayList<>(historialOperaciones);
    }

    // ✅ Generador incremental de ID de préstamos
    private static int generarNuevoId() {
        return contadorId++;
    }

    // ✅ Reinicia todo el sistema (opcional)
    public static void reiniciarSistema() {
        solicitudesPendientes.clear();
        prestamosAprobados.clear();
        historialOperaciones.clear();
        contadorId = 1;
    }
}
