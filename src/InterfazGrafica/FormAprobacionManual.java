package InterfazGrafica;

import gestores.GestorPrestamos;
import modelos.Prestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FormAprobacionManual extends JFrame {
    private JPanel panelAprobacion;
    private JTable tablaSolicitudes;
    private JButton btnAprobarSeleccionado;
    private JTextArea txtDetalles;

    private DefaultTableModel modelo;

    public FormAprobacionManual() {
        setTitle("Aprobar Préstamos Manualmente");
        setContentPane(panelAprobacion);
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        configurarTabla();
        cargarSolicitudes();

        btnAprobarSeleccionado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tablaSolicitudes.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(FormAprobacionManual.this, "Selecciona una solicitud.");
                    return;
                }

                int idPrestamo = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
                Prestamo aprobado = GestorPrestamos.aprobarPrestamoPorId(idPrestamo);
                if (aprobado != null) {
                    JOptionPane.showMessageDialog(FormAprobacionManual.this, "Préstamo aprobado correctamente.");
                    cargarSolicitudes();
                    mostrarDetalles(aprobado);
                } else {
                    JOptionPane.showMessageDialog(FormAprobacionManual.this, "Error al aprobar préstamo.");
                }
            }
        });
    }

    private void configurarTabla() {
        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{
                "ID", "Cliente ID", "Monto", "Destino", "Diferido", "Cuotas", "Interés", "Total", "Cuota"
        });
        tablaSolicitudes.setModel(modelo);
    }

    private void cargarSolicitudes() {
        modelo.setRowCount(0);
        List<Prestamo> solicitudes = GestorPrestamos.obtenerSolicitudesPendientes();

        for (Prestamo p : solicitudes) {
            double total = p.calcularTotalConInteres();
            double cuota = p.calcularValorCuota();
            modelo.addRow(new Object[]{
                    p.getId(), p.getIdCliente(), p.getMonto(), p.getDestino(),
                    p.esDiferido(), p.getNumeroCuotas(), p.getInteres() * 100,
                    String.format("%.2f", total), String.format("%.2f", cuota)
            });
        }
    }

    private void mostrarDetalles(Prestamo p) {
        StringBuilder sb = new StringBuilder();
        sb.append("Préstamo aprobado:\n");
        sb.append("Cliente ID: ").append(p.getIdCliente()).append("\n");
        sb.append("Monto: $").append(p.getMonto()).append("\n");
        sb.append("Destino: ").append(p.getDestino()).append("\n");
        if (p.esDiferido()) {
            sb.append("Cuotas: ").append(p.getNumeroCuotas()).append("\n");
            sb.append("Interés: ").append(p.getInteres() * 100).append("%\n");
            sb.append("Total a pagar: $").append(String.format("%.2f", p.calcularTotalConInteres())).append("\n");
            sb.append("Valor por cuota: $").append(String.format("%.2f", p.calcularValorCuota())).append("\n");
        } else {
            sb.append("Pago único (no diferido).\n");
        }
        txtDetalles.setText(sb.toString());
    }
}
