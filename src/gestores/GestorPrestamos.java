package gestores;

import modelos.Cliente;
import modelos.Prestamo;

import java.util.*;

public class GestorPrestamos {
    private final List<Prestamo> prestamos;

    public GestorPrestamos() {
        prestamos = new ArrayList<>();
    }

    public boolean agregarPrestamo(Prestamo p) {
        if (buscarPorId(p.getId()) != null) return false;
        prestamos.add(p);
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

    // ✅ NUEVO: prestamos aprobados sin cuenta asociada
    public List<Prestamo> obtenerPrestamosPendientesDeCuenta() {
        List<Prestamo> sinCuenta = new ArrayList<>();
        for (Prestamo p : obtenerPrestamosAprobados()) {
            if (p.getCliente().getCuentaBancaria() == null) {
                sinCuenta.add(p);
            }
        }
        return sinCuenta;
    }

    // ✅ NUEVO: método para asociar una cuenta bancaria
    public boolean asociarCuentaBancariaACliente(int idPrestamo, String cuenta) {
        Prestamo p = buscarPorId(idPrestamo);
        if (p == null) return false;
        try {
            p.getCliente().setCuentaBancaria(cuenta);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    // OPCIONAL: prestamos de un cliente específico
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

}

