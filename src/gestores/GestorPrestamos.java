package gestores;

import modelos.Prestamo;

import java.util.*;

public class GestorPrestamos {
    private static final Queue<Prestamo> solicitudesPendientes = new LinkedList<>();
    private static final PriorityQueue<Prestamo> prestamosAprobados = new PriorityQueue<>();
    private static int contadorId = 1;

    // ✅ Registrar solicitud (pendiente)
    public static void solicitarPrestamo(int idCliente, double monto, String destino, int cuotas) {
        double interes = obtenerInteresPorMeses(cuotas);
        Prestamo nuevo = new Prestamo(generarNuevoId(), idCliente, monto, destino);
        nuevo.setDiferido(cuotas > 1);
        nuevo.setNumeroCuotas(cuotas);
        nuevo.setInteres(interes);

        solicitudesPendientes.offer(nuevo);
    }

    // ✅ Aprobar manualmente por ID
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
        }

        return aprobado;
    }

    // ✅ Obtener solicitudes pendientes
    public static List<Prestamo> obtenerSolicitudesPendientes() {
        return new ArrayList<>(solicitudesPendientes);
    }

    // ✅ Obtener préstamos aprobados
    public static List<Prestamo> obtenerPrestamosAprobados() {
        return new ArrayList<>(prestamosAprobados);
    }

    // ✅ Calcular interés
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

    private static int generarNuevoId() {
        return contadorId++;
    }

    public static void reiniciarSistema() {
        solicitudesPendientes.clear();
        prestamosAprobados.clear();
        contadorId = 1;
    }
}
