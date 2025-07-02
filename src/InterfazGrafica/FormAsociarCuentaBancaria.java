package InterfazGrafica;

import gestores.GestorClientes;
import gestores.GestorPrestamos;
import modelos.Cliente;
import modelos.Prestamo;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class FormAsociarCuentaBancaria extends JFrame {
    private JPanel panelPrincipal;
    private JTextField txtIdCliente;
    private JTextField txtCuenta;
    private JButton btnGuardar;
    private JButton btnCancelar;

    private final GestorClientes gestorClientes;
    private final GestorPrestamos gestorPrestamos;

    public FormAsociarCuentaBancaria(GestorClientes gestorClientes, GestorPrestamos gestorPrestamos) {
        this.gestorClientes = gestorClientes;
        this.gestorPrestamos = gestorPrestamos;

        setTitle("Asociar Cuenta Bancaria");
        setContentPane(panelPrincipal);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        configurarAcciones();
    }

    private void configurarAcciones() {
        btnGuardar.addActionListener((ActionEvent e) -> {
            try {
                int id = Integer.parseInt(txtIdCliente.getText().trim());
                String cuenta = txtCuenta.getText().trim();

                if (cuenta.length() != 10 || !cuenta.matches("\\d{10}")) {
                    mostrar("⚠️ La cuenta debe tener exactamente 10 dígitos numéricos.");
                    return;
                }

                Cliente c = gestorClientes.buscarClientePorId(id);
                if (c == null) {
                    mostrar("❌ Cliente no encontrado.");
                    return;
                }

                Prestamo p = gestorPrestamos.buscarPrestamoAprobadoPorCliente(c);
                if (p == null) {
                    mostrar("⚠️ El cliente no tiene un préstamo aprobado aún.");
                    return;
                }

                c.setCuentaBancaria(cuenta);
                mostrar("✅ Cuenta asociada con éxito.");
                dispose();
            } catch (Exception ex) {
                mostrar("❌ Error: " + ex.getMessage());
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }

    private void mostrar(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void createUIComponents() {
    }
}
