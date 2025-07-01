package gestores;

import modelos.Cliente;

import java.util.ArrayList;
import java.util.List;

public class GestorClientes {
    private List<Cliente> clientes;

    public GestorClientes() {
        clientes = new ArrayList<>();
    }

    // Agregar cliente si no existe el ID
    public boolean agregarCliente(Cliente nuevoCliente) {
        if (buscarClientePorId(nuevoCliente.getId()) != null) {
            System.out.println("Cliente con ID " + nuevoCliente.getId() + " ya existe.");
            return false;
        }
        clientes.add(nuevoCliente);
        return true;
    }

    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes); // evitar acceso directo al array
    }

    public Cliente buscarClientePorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }

    public boolean editarCliente(int id, String nuevoNombre, String nuevoApellido, String nuevoCorreo) {
        Cliente cliente = buscarClientePorId(id);
        if (cliente != null) {
            cliente.setNombre(nuevoNombre);
            cliente.setApellido(nuevoApellido);
            cliente.setCorreo(nuevoCorreo);
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

    public boolean existeCliente(int id) {
        return buscarClientePorId(id) != null;
    }

    public void limpiarClientes() {
        clientes.clear();
    }
}
