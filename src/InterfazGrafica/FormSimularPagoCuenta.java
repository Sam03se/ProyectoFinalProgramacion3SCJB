package InterfazGrafica;

import gestores.GestorClientes;
import gestores.GestorPrestamos;
import modelos.Cliente;
import modelos.Prestamo;

import javax.swing.*;

public class FormSimularPagoCuenta extends JFrame {
    private JPanel panelPrincipal;
    private JTextField txtIdCliente;
    private JButton btnSimularPago;
    private JTextArea txtResultado;

    private final GestorPrestamos gestorPrestamos;
    private final GestorClientes gestorClientes;

    public FormSimularPagoCuenta(GestorPrestamos gestorPrestamos, GestorClientes gestorClientes) {
        this.gestorPrestamos = gestorPrestamos;
        this.gestorClientes = gestorClientes;

        setTitle("Simular Pago a Cuenta");
        setContentPane(panelPrincipal);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        btnSimularPago.addActionListener(e -> simular());
    }

    private void simular() {
        try {
            int id = Integer.parseInt(txtIdCliente.getText().trim());
            Cliente c = gestorClientes.buscarPorId(id);
            if (c == null) {
                txtResultado.setText("Cliente no encontrado.");
                return;
            }
            if (c.getCuentaBancaria() == null) {
                txtResultado.setText("Cliente no tiene cuenta registrada.");
                return;
            }

            Prestamo p = gestorPrestamos.buscarPrestamoAprobadoPorCliente(c);
            if (p == null) {
                txtResultado.setText("No se encontró préstamo aprobado para el cliente.");
                return;
            }

            String mensaje = "💸 Pago simulado de $" + p.getMonto() + " a la cuenta: " + c.getCuentaBancaria();
            gestorPrestamos.agregarOperacionAlHistorial(mensaje);
            txtResultado.setText(mensaje);

        } catch (Exception ex) {
            txtResultado.setText("Error: " + ex.getMessage());
        }
    }
}
