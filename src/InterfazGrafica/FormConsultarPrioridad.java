package InterfazGrafica;

import gestores.GestorPrestamos;
import modelos.Prestamo;

import javax.swing.*;
import java.awt.*;
import java.util.PriorityQueue;

public class FormConsultarPrioridad extends JFrame {
    private JPanel panelPrincipal;
    private JTextArea txtAreaPrioridad;

    public FormConsultarPrioridad(GestorPrestamos gestorPrestamos) {
        setTitle("Cola de Prioridad - Préstamos Pendientes");
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());

        txtAreaPrioridad = new JTextArea();
        txtAreaPrioridad.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtAreaPrioridad);

        panelPrincipal.add(scroll, BorderLayout.CENTER);
        setContentPane(panelPrincipal);

        mostrarCola(gestorPrestamos);
        setVisible(true);
    }

    private void mostrarCola(GestorPrestamos gestorPrestamos) {
        PriorityQueue<Prestamo> cola = gestorPrestamos.obtenerColaPrioridad();
        StringBuilder sb = new StringBuilder("📌 Orden de prioridad para aprobación:\n\n");

        if (cola.isEmpty()) {
            sb.append("No hay solicitudes pendientes.");
        } else {
            int pos = 1;
            while (!cola.isEmpty()) {
                Prestamo p = cola.poll();
                sb.append(pos++).append(". Cliente: ").append(p.getCliente().getNombre())
                        .append(" | Edad: ").append(p.getCliente().getEdad())
                        .append(" | Ingreso: $").append(p.getCliente().getIngresoMensual())
                        .append(" | Monto: $").append(p.getMonto()).append("\n");
            }
        }

        txtAreaPrioridad.setText(sb.toString());
    }
}
