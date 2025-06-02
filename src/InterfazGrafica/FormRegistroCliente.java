package InterfazGrafica;

import gestores.GestorClientes;
import modelos.Cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormRegistroCliente extends JFrame {
    private JPanel panelPrincipal;
    private JTextField txtId;
    private JTextField txtNombre;
    private JButton btnRegistrarCliente;
    private JLabel IDLabel;
    private JLabel lblNombre;
    private JButton btnCancelar;

    private final GestorClientes gestorClientes;

    public FormRegistroCliente(GestorClientes gestorClientes) {
        this.gestorClientes = gestorClientes;
        setContentPane(panelPrincipal);
        setTitle("Registrar Cliente");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        inicializarEventos();
    }

    private void inicializarEventos() {
        btnRegistrarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(txtId.getText().trim());
                    String nombre = txtNombre.getText().trim();

                    if (nombre.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "⚠️ El nombre no puede estar vacío.");
                        return;
                    }

                    Cliente nuevo = new Cliente(id, nombre);
                    gestorClientes.agregarCliente(nuevo);

                    JOptionPane.showMessageDialog(null, "✅ Cliente registrado correctamente.");
                    dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "⚠️ El ID debe ser un número entero.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "❌ Error al registrar: " + ex.getMessage());
                }
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }
}
