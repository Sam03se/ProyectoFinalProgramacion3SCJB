package gestores;

import modelos.Cliente;
import run.HistorialOperaciones;

import java.util.ArrayList;

public class GestorClientes {
    private static final ArrayList<Cliente> listaClientes = new ArrayList<>();
    private static int contadorId = 1;

    public static Cliente crearCliente(String nombre) {
        Cliente nuevo = new Cliente(contadorId++, nombre);
        listaClientes.add(nuevo);
        HistorialOperaciones.agregar("Cliente registrado: " + nombre);
        return nuevo;
    }

    public static ArrayList<Cliente> obtenerClientes() {
        return listaClientes;
    }

    public static Cliente buscarPorId(int id) {
        for (Cliente c : listaClientes) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public static boolean eliminarCliente(int id) {
        Cliente c = buscarPorId(id);
        if (c != null) {
            listaClientes.remove(c);
            HistorialOperaciones.agregar("Cliente eliminado: " + c.getNombre());
            return true;
        }
        return false;
    }

    public static boolean actualizarNombre(int id, String nuevoNombre) {
        Cliente c = buscarPorId(id);
        if (c != null) {
            c.setNombre(nuevoNombre);
            HistorialOperaciones.agregar("Cliente actualizado: " + id + " → " + nuevoNombre);
            return true;
        }
        return false;
    }
}
