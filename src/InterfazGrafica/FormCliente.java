package InterfazGrafica;

import gestores.GestorClientes;
import modelos.Cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class FormCliente extends JFrame {
    private JPanel panelPrincipal;
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextArea txtListaClientes;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnListar;
    private JButton btnCancelar;

    private final GestorClientes gestorClientes;

    public FormCliente(GestorClientes gestorClientes) {
        this.gestorClientes = gestorClientes;

        setTitle("Gestión de Clientes");
        setContentPane(panelPrincipal);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        configurarAcciones();
    }

    private void configurarAcciones() {
        btnAgregar.addActionListener((ActionEvent e) -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                String nombre = txtNombre.getText().trim();
                if (nombre.isEmpty()) {
                    mostrar("⚠️ Nombre vacío.");
                    return;
                }
                Cliente c = new Cliente(id, nombre);
                gestorClientes.agregarCliente(c);
                mostrar("✅ Cliente agregado.");
                limpiarCampos();
            } catch (Exception ex) {
                mostrar("❌ Error al agregar: " + ex.getMessage());
            }
        });

        btnListar.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("📋 Lista de Clientes:\n");
            for (Cliente c : gestorClientes.listarClientes()) {
                sb.append("ID: ").append(c.getId()).append(" | Nombre: ").append(c.getNombre()).append("\n");
            }
            txtListaClientes.setText(sb.toString());
        });

        btnEditar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                String nuevoNombre = txtNombre.getText().trim();
                if (gestorClientes.editarCliente(id, nuevoNombre)) {
                    mostrar("✅ Cliente editado.");
                } else {
                    mostrar("⚠️ Cliente no encontrado.");
                }
                limpiarCampos();
            } catch (Exception ex) {
                mostrar("❌ Error al editar.");
            }
        });

        btnEliminar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                if (gestorClientes.eliminarCliente(id)) {
                    mostrar("✅ Cliente eliminado.");
                } else {
                    mostrar("⚠️ Cliente no encontrado.");
                }
                limpiarCampos();
            } catch (Exception ex) {
                mostrar("❌ Error al eliminar.");
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
    }

    private void mostrar(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
