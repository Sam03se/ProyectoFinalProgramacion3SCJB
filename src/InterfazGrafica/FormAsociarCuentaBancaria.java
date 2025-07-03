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
            Cliente cliente = gestorClientes.buscarPorId(id);

            if (cliente == null) {
                mostrar("❌ Cliente no encontrado.");
                return;
            }

            Prestamo prestamo = gestorPrestamos.buscarPrestamoAprobadoPorCliente(cliente);
            if (prestamo == null) {
                mostrar("❌ Este cliente no tiene un préstamo aprobado.");
                return;
            }

            String cuenta = txtCuenta.getText().trim();
            if (!cuenta.matches("\\d{10}")) {
                mostrar("⚠️ La cuenta bancaria debe tener exactamente 10 dígitos numéricos.");
                return;
            }

            if (cliente.tieneCuentaBancaria()) {
                mostrar("⚠️ Este cliente ya tiene una cuenta bancaria asociada: " + cliente.getCuentaBancaria());
                return;
            }

            boolean asociado = gestorPrestamos.asociarCuentaBancariaACliente(prestamo.getId(), cuenta);
            if (asociado) {
                txtResumen.setText("✅ Cuenta asociada correctamente\n\n" +
                        "Cliente: " + cliente.getNombre() + " " + cliente.getApellido() + "\n" +
                        "Cuenta: " + cuenta + "\n" +
                        "Monto aprobado: $" + String.format("%.2f", prestamo.getMonto()) + "\n" +
                        "Cuotas: " + prestamo.getCuotas() + "\n" +
                        "Estado: Aprobado");
                mostrar("✅ Cuenta bancaria asociada con éxito.");
            } else {
                mostrar("❌ No se pudo asociar la cuenta bancaria.");
            }

        } catch (NumberFormatException ex) {
            mostrar("⚠️ ID inválido. Ingresa solo números.");
        } catch (Exception ex) {
            mostrar("⚠️ Error: " + ex.getMessage());
        }
    }

    private void mostrar(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    private void createUIComponents() {
        // Diseñado en el archivo .form
    }
}
