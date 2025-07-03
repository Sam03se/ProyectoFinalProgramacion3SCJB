package gestores;

import modelos.Cliente;
import modelos.Prestamo;

import java.util.*;

public class GestorPrestamos {
    private final List<Prestamo> prestamos;
    private final List<String> historialOperaciones;
    private final List<Prestamo> solicitudesPendientes;
    private final Map<Integer, String> motivosRechazo;
    private final Map<Prestamo, String> prestamosRechazados;

    public GestorPrestamos() {
        prestamos = new ArrayList<>();
        historialOperaciones = new ArrayList<>();
        solicitudesPendientes = new ArrayList<>();
        motivosRechazo = new HashMap<>();
        prestamosRechazados = new HashMap<>();
    }

    // ✅ Permite obtener las solicitudes pendientes
    public List<Prestamo> getSolicitudesPendientes() {
        return solicitudesPendientes;
    }

    public void agregarSolicitudPendiente(Prestamo prestamo) {
        solicitudesPendientes.add(prestamo);
    }

    public boolean agregarPrestamo(Prestamo p) {
        if (buscarPorId(p.getId()) != null) return false;
        prestamos.add(p);
        historialOperaciones.add("📥 Solicitud registrada: " + p.resumen());
        return true;
    }

    public Prestamo buscarPorId(int id) {
        for (Prestamo p : prestamos) {
            if (p.getId() == id) return p;
        }
        for (Prestamo p : solicitudesPendientes) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public boolean aprobarPrestamoPorId(int id) {
        Prestamo p = buscarPorId(id);
        if (p != null && solicitudesPendientes.contains(p)) {
            solicitudesPendientes.remove(p);
            double interes = (p.getCuotas() <= 6) ? 0.05 : 0.10;
            p.setInteres(interes);
            prestamos.add(p);
            historialOperaciones.add("✅ Aprobado manualmente: ID: " + p.getId() + " | Interés: " + (interes * 100) + "%");
            return true;
        }
        return false;
    }

    public boolean rechazarPrestamoPorId(int id, String motivo) {
        Prestamo p = buscarPorId(id);
        if (p != null && solicitudesPendientes.contains(p)) {
            solicitudesPendientes.remove(p);
            motivosRechazo.put(id, motivo);
            prestamosRechazados.put(p, motivo);
            historialOperaciones.add("❌ Rechazado: ID " + id + " | Cliente: " + p.getCliente().getNombre() + " | Motivo: " + motivo);
            return true;
        }
        return false;
    }

    public void agregarPrestamoRechazado(Prestamo prestamo, String razon) {
        prestamosRechazados.put(prestamo, razon);
        historialOperaciones.add("❌ Rechazado préstamo ID " + prestamo.getId()
                + " de " + prestamo.getCliente().getNombre()
                + " | Motivo: " + razon);
    }

    public Map<Prestamo, String> getPrestamosRechazados() {
        return prestamosRechazados;
    }

    public String obtenerMotivoRechazo(int id) {
        return motivosRechazo.getOrDefault(id, "No registrado");
    }

    public int generarNuevoId() {
        int maxId = 0;
        for (Prestamo p : prestamos) {
            if (p.getId() > maxId) maxId = p.getId();
        }
        for (Prestamo p : solicitudesPendientes) {
            if (p.getId() > maxId) maxId = p.getId();
        }
        return maxId + 1;
    }

    public boolean asociarCuentaBancariaACliente(int idPrestamo, String cuenta) {
        Prestamo p = buscarPorId(idPrestamo);
        if (p == null) return false;
        try {
            p.getCliente().setCuentaBancaria(cuenta);
            historialOperaciones.add("💳 Cuenta asociada al préstamo ID: " + p.getId() + " → Cuenta: " + cuenta);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    public List<Prestamo> listarPrestamos() {
        return new ArrayList<>(prestamos);
    }

    public boolean eliminarPrestamo(int id) {
        Prestamo p = buscarPorId(id);
        if (p != null) {
            prestamos.remove(p);
            historialOperaciones.add("🗑️ Eliminado: " + p.resumen());
            return true;
        }
        return false;
    }

    public List<Prestamo> obtenerPrestamosAprobados() {
        List<Prestamo> aprobados = new ArrayList<>();
        for (Prestamo p : prestamos) {
            if (p.getInteres() > 0.0) {
                aprobados.add(p);
            }
        }
        return aprobados;
    }

    public List<Prestamo> obtenerPrestamosPendientesDeCuenta() {
        List<Prestamo> sinCuenta = new ArrayList<>();
        for (Prestamo p : obtenerPrestamosAprobados()) {
            if (p.getCliente().getCuentaBancaria() == null) {
                sinCuenta.add(p);
            }
        }
        return sinCuenta;
    }

    public List<Prestamo> prestamosPorCliente(int idCliente) {
        List<Prestamo> lista = new ArrayList<>();
        for (Prestamo p : prestamos) {
            if (p.getCliente().getId() == idCliente) {
                lista.add(p);
            }
        }
        return lista;
    }

    public Prestamo buscarPrestamoAprobadoPorCliente(Cliente cliente) {
        for (Prestamo p : prestamos) {
            if (p.getCliente().getId() == cliente.getId() && p.getInteres() > 0) {
                return p;
            }
        }
        return null;
    }

    public void limpiarSoloHistorial() {
        historialOperaciones.clear();
    }

    public void agregarOperacionAlHistorial(String mensaje) {
        historialOperaciones.add(mensaje);
    }

    public List<String> getHistorialOperaciones() {
        List<String> historialConCuentas = new ArrayList<>();
        for (String registro : historialOperaciones) {
            if (registro.startsWith("✅ Aprobado manualmente:")) {
                try {
                    int idInicio = registro.indexOf("ID: ") + 4;
                    int idFin = registro.indexOf(" |", idInicio);
                    int id = Integer.parseInt(registro.substring(idInicio, idFin));
                    for (Prestamo p : obtenerPrestamosAprobados()) {
                        if (p.getId() == id && p.getCliente().getCuentaBancaria() != null) {
                            registro += " 💳 Cuenta: " + p.getCliente().getCuentaBancaria();
                            break;
                        }
                    }
                } catch (Exception ignored) {}
            }
            historialConCuentas.add(registro);
        }
        return historialConCuentas;
    }

    public PriorityQueue<Prestamo> obtenerColaPrioridad() {
        PriorityQueue<Prestamo> cola = new PriorityQueue<>((p1, p2) -> {
            int edad1 = p1.getCliente().getEdad();
            int edad2 = p2.getCliente().getEdad();
            double ingreso1 = p1.getCliente().getIngresoMensual();
            double ingreso2 = p2.getCliente().getIngresoMensual();

            if (edad1 != edad2) {
                return Integer.compare(edad1, edad2);
            } else {
                return Double.compare(ingreso2, ingreso1);
            }
        });

        for (Prestamo p : solicitudesPendientes) {
            cola.offer(p);
        }

        return cola;
    }
}
