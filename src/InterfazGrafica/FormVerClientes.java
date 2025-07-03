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

        String[] columnas = {"ID", "Nombre", "¿Solicitó Préstamo?", "Cuotas Pagadas", "Total Cuotas", "Estado Deuda"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaClientes = new JTable(modeloTabla);

        JScrollPane scroll = new JScrollPane(tablaClientes);
        panelPrincipal.add(scroll, BorderLayout.CENTER);
        setContentPane(panelPrincipal);
        setVisible(true);

        cargarDatos();
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0); // Limpiar tabla

        for (Cliente c : gestorClientes.listarClientes()) {
            Prestamo p = gestorPrestamos.buscarPrestamoAprobadoPorCliente(c);
            if (p == null) {
                modeloTabla.addRow(new Object[]{
                        c.getId(),
                        c.getNombre() + " " + c.getApellido(),
                        "No",
                        "-", "-", "Sin préstamo"
                });
            } else {
                String estado = p.estaPagado() ? "✅ Pagado" : "🕓 Deuda pendiente";

                modeloTabla.addRow(new Object[]{
                        c.getId(),
                        c.getNombre() + " " + c.getApellido(),
                        "Sí",
                        p.getCuotasPagadas(),
                        p.getCuotas(),
                        estado
                });
            }
        }
    }
}
