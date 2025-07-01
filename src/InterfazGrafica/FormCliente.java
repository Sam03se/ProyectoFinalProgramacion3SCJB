
package InterfazGrafica;

import gestores.GestorClientes;
import modelos.Cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class FormCliente extends JFrame {
    private JPanel panelPrincipal;
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCedula;
    private JTextField txtCorreo;
    private JTextField txtEdad;
    private JComboBox<String> comboZona;
    private JTextField txtIngreso;
    private JTextField txtAntiguedad;
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
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        comboZona.addItem("urbana");
        comboZona.addItem("rural");

        configurarAcciones();
    }

    private void configurarAcciones() {
        btnAgregar.addActionListener((ActionEvent e) -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                String nombre = txtNombre.getText().trim();
                String apellido = txtApellido.getText().trim();
                String cedula = txtCedula.getText().trim();
                String correo = txtCorreo.getText().trim();
                int edad = Integer.parseInt(txtEdad.getText().trim());
                String zona = comboZona.getSelectedItem().toString();
                double ingreso = Double.parseDouble(txtIngreso.getText().trim());
                int antiguedad = Integer.parseInt(txtAntiguedad.getText().trim());

                Cliente c = new Cliente(id, nombre, apellido, cedula, correo, edad, zona, ingreso, antiguedad);
                if (gestorClientes.agregarCliente(c)) {
                    mostrar("✅ Cliente agregado correctamente.");
                    limpiarCampos();
                } else {
                    mostrar("⚠️ Ya existe un cliente con ese ID.");
                }
            } catch (Exception ex) {
                mostrar("❌ Error al agregar cliente: " + ex.getMessage());
            }
        });

        btnListar.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("📋 Lista de Clientes: ");
            for (Cliente c : gestorClientes.listarClientes()) {
                sb.append("ID: ").append(c.getId())
                        .append(" | ").append(c.getNombre()).append(" ").append(c.getApellido())
                        .append(" | Correo: ").append(c.getCorreo()).append(" ");
            }
            txtListaClientes.setText(sb.toString());
        });

        btnEditar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                String nombre = txtNombre.getText().trim();
                String apellido = txtApellido.getText().trim();
                String correo = txtCorreo.getText().trim();

                if (gestorClientes.editarCliente(id, nombre, apellido, correo)) {
                    mostrar("✅ Cliente editado correctamente.");
                } else {
                    mostrar("⚠️ Cliente no encontrado.");
                }
                limpiarCampos();
            } catch (Exception ex) {
                mostrar("❌ Error al editar cliente: " + ex.getMessage());
            }
        });

        btnEliminar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                if (gestorClientes.eliminarCliente(id)) {
                    mostrar("✅ Cliente eliminado correctamente.");
                } else {
                    mostrar("⚠️ Cliente no encontrado.");
                }
                limpiarCampos();
            } catch (Exception ex) {
                mostrar("❌ Error al eliminar cliente: " + ex.getMessage());
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtCedula.setText("");
        txtCorreo.setText("");
        txtEdad.setText("");
        txtIngreso.setText("");
        txtAntiguedad.setText("");
        comboZona.setSelectedIndex(0);
    }

    private void mostrar(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void createUIComponents() {
        // Se configura automáticamente en el archivo .form
    }
}