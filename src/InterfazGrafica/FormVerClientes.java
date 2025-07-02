package InterfazGrafica;

import gestores.GestorClientes;
import gestores.GestorPrestamos;
import modelos.Cliente;
import modelos.Prestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FormVerClientes extends JFrame {
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JPanel panelPrincipal;
    private final GestorClientes gestorClientes;
    private final GestorPrestamos gestorPrestamos;

    public FormVerClientes(GestorClientes gestorClientes, GestorPrestamos gestorPrestamos) {
        this.gestorClientes = gestorClientes;
        this.gestorPrestamos = gestorPrestamos;

        setTitle("Estado de Clientes y Préstamos");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        panelPrincipal = new JPanel(new BorderLayout());

        String[] columnas = {"ID", "Nombre", "¿Solicitó?", "Cuotas Pagadas", "Total Cuotas", "Estado Deuda"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaClientes = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaClientes);

        panelPrincipal.add(scroll, BorderLayout.CENTER);

        JButton btnVerDetalle = new JButton("Ver Detalle del Cliente");
        panelPrincipal.add(btnVerDetalle, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
        setVisible(true);

        cargarDatos();

        btnVerDetalle.addActionListener(e -> mostrarDetalleCliente());
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0); // limpiar

        for (Cliente c : gestorClientes.listarClientes()) {
            Prestamo p = gestorPrestamos.buscarPrestamoAprobadoPorCliente(c);
            if (p == null) {
                modeloTabla.addRow(new Object[]{
                        c.getId(),
                        c.getNombre() + " " + c.getApellido(),
                        "❌ No",
                        "-", "-", "Sin préstamo"
                });
            } else {
                String estado = p.estaPagado() ? "✅ Pagado" : "🕓 Deuda pendiente";

                modeloTabla.addRow(new Object[]{
                        c.getId(),
                        c.getNombre() + " " + c.getApellido(),
                        "✅ Sí",
                        p.getCuotasPagadas(),
                        p.getCuotas(),
                        estado
                });
            }
        }
    }

    private void mostrarDetalleCliente() {
        int fila = tablaClientes.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente de la tabla.");
            return;
        }

        int idCliente = (int) modeloTabla.getValueAt(fila, 0);
        Cliente cliente = gestorClientes.buscarPorId(idCliente);
        Prestamo prestamo = gestorPrestamos.buscarPrestamoAprobadoPorCliente(cliente);

        StringBuilder sb = new StringBuilder();
        sb.append("Cliente: ").append(cliente.getNombre()).append(" ").append(cliente.getApellido()).append("\n");
        if (prestamo != null) {
            sb.append("Destino: ").append(prestamo.getDestino()).append("\n")
                    .append("Monto: $").append(prestamo.getMonto()).append("\n")
                    .append("Cuotas: ").append(prestamo.getCuotas()).append("\n")
                    .append("Pagadas: ").append(prestamo.getCuotasPagadas()).append("\n")
                    .append("Interés: ").append(prestamo.getInteres() * 100).append("%\n")
                    .append("Total a pagar: $").append(prestamo.calcularTotalConInteres()).append("\n");
        } else {
            sb.append("No tiene préstamo aprobado.");
        }

        JOptionPane.showMessageDialog(this, sb.toString(), "Detalle del Cliente", JOptionPane.INFORMATION_MESSAGE);
    }
}
