package InterfazGrafica;

import gestores.GestorClientes;
import gestores.GestorPrestamos;
import modelos.Cliente;
import modelos.Prestamo;

import javax.swing.*;

public class FormSimularPagoCuenta extends JFrame {
    private JPanel panelPrincipal;
    private JComboBox<Cliente> comboClientes;
    private JButton btnRegistrarPago;
    private JTextArea txtResumen;

    private final GestorPrestamos gestorPrestamos;
    private final GestorClientes gestorClientes;

    public FormSimularPagoCuenta(GestorPrestamos gestorPrestamos, GestorClientes gestorClientes) {
        this.gestorPrestamos = gestorPrestamos;
        this.gestorClientes = gestorClientes;

        setTitle("Simular Pago a Cuenta");
        setContentPane(panelPrincipal);
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        llenarClientes();

        comboClientes.addActionListener(e -> mostrarResumen());
        btnRegistrarPago.addActionListener(e -> registrarPago());
    }

    private void llenarClientes() {
        comboClientes.removeAllItems();
        for (Cliente c : gestorClientes.listarClientes()) {
            Prestamo p = gestorPrestamos.buscarPrestamoAprobadoPorCliente(c);
            if (p != null && !p.estaPagadoCompletamente()) {
                comboClientes.addItem(c);
            }
        }
    }

    private void mostrarResumen() {
        Cliente seleccionado = (Cliente) comboClientes.getSelectedItem();
        if (seleccionado == null) return;

        Prestamo p = gestorPrestamos.buscarPrestamoAprobadoPorCliente(seleccionado);
        if (p == null) return;

        StringBuilder sb = new StringBuilder();
        sb.append("📄 RESUMEN DEL PRÉSTAMO\n");
        sb.append("Cliente: ").append(seleccionado.getNombre()).append(" ").append(seleccionado.getApellido()).append("\n");
        sb.append("Monto Total: $").append(String.format("%.2f", p.calcularTotalConInteres())).append("\n");
        sb.append("Cuotas Totales: ").append(p.getCuotas()).append("\n");
        sb.append("Cuotas Pagadas: ").append(p.getCuotasPagadas()).append("\n");
        sb.append("Cuota Mensual: $").append(String.format("%.2f", p.calcularCuotaMensual())).append("\n");
        sb.append("Valor Restante: $").append(String.format("%.2f", p.calcularTotalConInteres() - p.getCuotasPagadas() * p.calcularCuotaMensual())).append("\n");

        txtResumen.setText(sb.toString());
    }

    private void registrarPago() {
        Cliente seleccionado = (Cliente) comboClientes.getSelectedItem();
        if (seleccionado == null) return;

        Prestamo p = gestorPrestamos.buscarPrestamoAprobadoPorCliente(seleccionado);
        if (p == null || p.estaPagadoCompletamente()) {
            JOptionPane.showMessageDialog(this, "Este préstamo ya fue pagado completamente.");
            return;
        }

        p.pagarCuota();
        gestorPrestamos.agregarOperacionAlHistorial("💰 Pago registrado: " + seleccionado.getNombre() + " pagó una cuota del préstamo ID " + p.getId());

        mostrarResumen();

        if (p.estaPagadoCompletamente()) {
            JOptionPane.showMessageDialog(this, "🎉 ¡Préstamo pagado completamente!");
        } else {
            JOptionPane.showMessageDialog(this, "✅ Pago registrado correctamente.");
        }
    }
}
