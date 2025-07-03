package InterfazGrafica;

import gestores.GestorPrestamos;
import modelos.Prestamo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FormConsultarPrioridad extends JFrame {
    private JPanel panelPrincipal;
    private JTextArea txtAreaPrioridad;
    private JScrollPane scrollPane;

    private final GestorPrestamos gestorPrestamos;

    public FormConsultarPrioridad(GestorPrestamos gestorPrestamos) {
        this.gestorPrestamos = gestorPrestamos;

        setTitle("Cola de Solicitudes Pendientes");
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        panelPrincipal = new JPanel(new BorderLayout());
        txtAreaPrioridad = new JTextArea();
        txtAreaPrioridad.setEditable(false);
        scrollPane = new JScrollPane(txtAreaPrioridad);

        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        setContentPane(panelPrincipal);

        mostrarSolicitudes();
        setVisible(true);
    }

    private void mostrarSolicitudes() {
        List<Prestamo> solicitudes = gestorPrestamos.getSolicitudesPendientes();

        if (solicitudes.isEmpty()) {
            txtAreaPrioridad.setText("📭 No hay solicitudes pendientes actualmente.");
            return;
        }

        StringBuilder sb = new StringBuilder("📌 Solicitudes pendientes (orden de llegada):\n\n");
        int orden = 1;

        for (Prestamo p : solicitudes) {
            sb.append("Orden #").append(orden++)
                    .append(" | ID: ").append(p.getId())
                    .append(" | Cliente: ").append(p.getCliente().getNombre())
                    .append(" | Monto: $").append(String.format("%.2f", p.getMonto()))
                    .append(" | Cuotas: ").append(p.getCuotas())
                    .append(" | Interés: ").append(p.getInteres() * 100).append("%\n");
        }

        txtAreaPrioridad.setText(sb.toString());
    }
}
