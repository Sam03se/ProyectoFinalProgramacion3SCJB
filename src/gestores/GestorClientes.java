package gestores;

import modelos.Cliente;

import java.util.ArrayList;
import java.util.List;

public class GestorClientes {
    private final List<Cliente> listaClientes = new ArrayList<>();

    public boolean agregarCliente(Cliente cliente) {
        for (Cliente c : listaClientes) {
            if (c.getId() == cliente.getId() || c.getCedula().equals(cliente.getCedula())) {
                return false; // Cliente ya existe por ID o cédula
            }
        }
        listaClientes.add(cliente);
        return true;
    }

    // Editar cliente existente
    public boolean editarCliente(int id, String nombre, String apellido, String correo) {
        Cliente cliente = buscarPorId(id);
        if (cliente != null) {
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setCorreo(correo);
            return true;
        }
        return false;
    }

    // Eliminar cliente por ID
    public boolean eliminarCliente(int id) {
        Cliente cliente = buscarPorId(id);
        if (cliente != null) {
            listaClientes.remove(cliente);
            return true;
        }
        return false;
    }

    // Listar todos los clientes
    public List<Cliente> listarClientes() {
        return new ArrayList<>(listaClientes);
    }

    // Buscar cliente por ID (método NUEVO)
    public Cliente buscarPorId(int id) {
        for (Cliente c : listaClientes) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }
}
