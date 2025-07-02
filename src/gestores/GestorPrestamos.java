package gestores;

import modelos.Cliente;
import modelos.Prestamo;

import java.util.*;

public class GestorPrestamos {
    private final List<Prestamo> prestamos;
    private final List<String> historialOperaciones; // historial agregado

    public GestorPrestamos() {
        prestamos = new ArrayList<>();
        historialOperaciones = new ArrayList<>();
    }

    public boolean agregarPrestamo(Prestamo p) {
        if (buscarPorId(p.getId()) != null) return false;
        prestamos.add(p);
        historialOperaciones.add("Préstamo agregado: " + p);
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
            historialOperaciones.add("Préstamo eliminado: " + p);
            return true;
        }
        return false;
    }

    public List<Prestamo> obtenerPrestamosPendientes() {
        List<Prestamo> pendientes = new ArrayList<>();
        for (Prestamo p : prestamos) {
            if (!p.esDiferido() && p.getInteres() == 0.0) {
                pendientes.add(p);
            }
        }
        return pendientes;
    }

    public List<Prestamo> obtenerPrestamosAprobados() {
        List<Prestamo> aprobados = new ArrayList<>();
        for (Prestamo p : prestamos) {
            if (p.getInteres() > 0) {
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

    public boolean asociarCuentaBancariaACliente(int idPrestamo, String cuenta) {
        Prestamo p = buscarPorId(idPrestamo);
        if (p == null) return false;
        try {
            p.getCliente().setCuentaBancaria(cuenta);
            historialOperaciones.add("Cuenta asociada al préstamo ID: " + p.getId() + " → Cuenta: " + cuenta);
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
    // limpia historial sin borrar datos
    public void limpiarSoloHistorial() {
        prestamos.removeIf(p -> p.getInteres() == 0 && p.esDiferido()); // Ejemplo de lógica si se consideran "rechazados"
    }

    public Prestamo buscarPrestamoAprobadoPorCliente(Cliente cliente) {
        for (Prestamo p : prestamos) {
            if (p.getCliente().getId() == cliente.getId() && p.getInteres() > 0) {
                return p;
            }
        }
        return null;
    }

    // ✅ HISTORIAL CON CUENTA BANCARIA SI EXISTE
    public List<String> getHistorialOperaciones() {
        List<String> historialConCuentas = new ArrayList<>();
        for (String registro : historialOperaciones) {
            if (registro.startsWith(" Aprobado manualmente: ")) {
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
    // ✅ Método para solicitar un préstamo
    public boolean solicitarPrestamo(Cliente cliente, double monto, String destino, int cuotas) {
        int id = prestamos.size() + 1;
        Prestamo p = new Prestamo(id, cliente, monto, destino, cuotas);

        if (cuotas > 1) {
            p.setDiferido(true);
            // Aplica interés según número de cuotas
            if (cuotas <= 3) {
                p.setInteres(0.05); // 5%
            } else if (cuotas <= 6) {
                p.setInteres(0.10); // 10%
            } else {
                p.setInteres(0.15); // 15%
            }
        } else {
            p.setDiferido(false);
            p.setInteres(0.0);


        prestamos.add(p);
        return true;
    }


    int nuevoId = prestamos.size() + 1;
        Prestamo nuevo = new Prestamo(nuevoId, cliente, monto, destino, cuotas);
        return agregarPrestamo(nuevo);
    }
    // ✅ Método para aprobar un préstamo por ID
    public boolean aprobarPrestamoPorId(int id) {
        Prestamo p = buscarPorId(id);
        if (p != null && !p.esDiferido() && p.getInteres() == 0.0) {
            // Establece condiciones de aprobación
            p.setInteres(0.05); // por ejemplo, 5%
            return true;
        }
        return false;
    }

    // ✅ Método para obtener préstamos aún no aprobados
    public List<Prestamo> obtenerSolicitudesPendientes() {
        List<Prestamo> pendientes = new ArrayList<>();
        for (Prestamo p : prestamos) {
            if (!p.esDiferido() && p.getInteres() == 0.0) {
                pendientes.add(p);
            }
        }
        return pendientes;
    }

    public void agregarOperacionAlHistorial(String mensaje) {
        historialOperaciones.add(mensaje);
    }
    public PriorityQueue<Prestamo> obtenerColaPrioridad() {
        PriorityQueue<Prestamo> cola = new PriorityQueue<>((p1, p2) -> {
            // Estrategia simple: menor edad y mayor ingreso tienen prioridad
            int edad1 = p1.getCliente().getEdad();
            int edad2 = p2.getCliente().getEdad();
            double ingreso1 = p1.getCliente().getIngresoMensual();
            double ingreso2 = p2.getCliente().getIngresoMensual();

            if (edad1 != edad2) {
                return Integer.compare(edad1, edad2);  // menor edad primero
            } else {
                return Double.compare(ingreso2, ingreso1);  // mayor ingreso primero
            }
        });

        for (Prestamo p : obtenerPrestamosPendientes()) {
            cola.offer(p);
        }

        return cola;
    }

}
