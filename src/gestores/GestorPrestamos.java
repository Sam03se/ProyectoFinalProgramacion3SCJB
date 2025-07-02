package gestores;

import modelos.Cliente;
import modelos.Prestamo;

import java.util.*;

public class GestorPrestamos {
    private final List<Prestamo> prestamos;
    private final List<String> historialOperaciones;

    public GestorPrestamos() {
        prestamos = new ArrayList<>();
        historialOperaciones = new ArrayList<>();
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
        return null;
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

    public List<Prestamo> obtenerSolicitudesPendientes() {
        List<Prestamo> pendientes = new ArrayList<>();
        for (Prestamo p : prestamos) {
            if (p.getInteres() == 0.0) {
                pendientes.add(p);
            }
        }
        return pendientes;
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

    public boolean aprobarPrestamoPorId(int id) {
        Prestamo p = buscarPorId(id);
        if (p != null && p.getInteres() == 0.0) {
            double interes = (p.getCuotas() <= 6) ? 0.05 : 0.10;
            p.setInteres(interes);
            historialOperaciones.add("✅ Aprobado manualmente: ID: " + p.getId() + " | Interés: " + (interes * 100) + "%");
            return true;
        }
        return false;
    }

    public boolean solicitarPrestamo(Cliente cliente, double monto, String destino, int cuotas) {
        int id = prestamos.size() + 1;
        Prestamo p = new Prestamo(id, cliente, monto, destino, cuotas);
        p.setInteres(0.0);  // aún no aprobado
        p.setDiferido(cuotas > 1);
        return agregarPrestamo(p);
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

        for (Prestamo p : obtenerSolicitudesPendientes()) {
            cola.offer(p);
        }

        return cola;
    }
    private final List<Prestamo> solicitudesPendientes = new ArrayList<>();

    public void agregarSolicitudPendiente(Prestamo prestamo) {
        solicitudesPendientes.add(prestamo);
    }

    public List<Prestamo> getSolicitudesPendientes() {
        return solicitudesPendientes;
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


}
