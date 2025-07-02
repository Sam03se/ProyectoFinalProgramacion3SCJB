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
    private JButton btnAsociar;
    private JTextArea txtResumen;
    private JTextField txtCuenta;
    private JButton btnCerrar;

    private final GestorClientes gestorClientes;
    private final GestorPrestamos gestorPrestamos;

    public FormAsociarCuentaBancaria(GestorClientes gestorClientes, GestorPrestamos gestorPrestamos) {
        this.gestorClientes = gestorClientes;
        this.gestorPrestamos = gestorPrestamos;

        setTitle("Asociar Cuenta Bancaria");
        setSize(500, 400);
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        btnAsociar.addActionListener((ActionEvent e) -> asociarCuenta());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void asociarCuenta() {
        try {
            int id = Integer.parseInt(txtIdCliente.getText().trim());
            Cliente c = gestorClientes.buscarPorId(id);
            if (c == null) {
                mostrar("Cliente no encontrado.");
                return;
            }

            Prestamo p = gestorPrestamos.buscarPrestamoAprobadoPorCliente(c);
            if (p == null) {
                mostrar("Cliente no tiene préstamo aprobado.");
                return;
            }

            String cuenta = txtCuenta.getText().trim();
            if (cuenta.length() != 10 || !cuenta.matches("\\d+")) {
                mostrar("La cuenta bancaria debe tener 10 dígitos.");
                return;
            }

            if (gestorPrestamos.asociarCuentaBancariaACliente(p.getId(), cuenta)) {
                mostrar("Cuenta asociada correctamente.");
                txtResumen.setText("Cliente: " + c.getNombre() + " " + c.getApellido() + "\n" +
                        "Cuenta asociada: " + cuenta + "\n" +
                        "Monto aprobado: $" + p.getMonto());
            } else {
                mostrar("No se pudo asociar la cuenta.");
            }
        } catch (Exception ex) {
            mostrar("Error: " + ex.getMessage());
        }
    }

    private void mostrar(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    private void createUIComponents() {
        // Se diseña en el .form
    }
}
