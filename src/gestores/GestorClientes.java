package gestores;

import modelos.Cliente;
import java.util.ArrayList;

public class GestorClientes {
    private ArrayList<Cliente> clientes = new ArrayList<>();

    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public ArrayList<Cliente> listarClientes() {
        return clientes;
    }

    // Alias opcional para evitar errores con "obtenerClientes"
    public ArrayList<Cliente> obtenerClientes() {
        return listarClientes();
    }

    public Cliente buscarClientePorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) return cliente;
        }
        return null;
    }

    public boolean editarCliente(int id, String nuevoNombre) {
        Cliente cliente = buscarClientePorId(id);
        if (cliente != null) {
            cliente.setNombre(nuevoNombre);
            return true;
        }
        return false;
    }

    public boolean eliminarCliente(int id) {
        Cliente cliente = buscarClientePorId(id);
        if (cliente != null) {
            clientes.remove(cliente);
            return true;
        }
        return false;
    }
}

