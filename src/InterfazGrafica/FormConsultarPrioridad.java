package InterfazGrafica;

import gestores.GestorPrestamos;
import modelos.Prestamo;

import javax.swing.*;
import java.util.List;

public class FormConsultarPrioridad extends JFrame {
    private JPanel panelPrioridad;
    private JTextArea txtPrestamosPrioridad;

    public FormConsultarPrioridad() {
        setTitle("Préstamos Aprobados por Prioridad");
        setContentPane(panelPrioridad);
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        cargarPrestamosPrioritarios();
    }

    private void cargarPrestamosPrioritarios() {
        List<Prestamo> lista = GestorPrestamos.obtenerPrestamosPrioridad();
        StringBuilder sb = new StringBuilder("Préstamos aprobados (prioridad por monto):\n");

        if (lista.isEmpty()) {
            sb.append("No hay préstamos aprobados.");
        } else {
            for (Prestamo p : lista) {
                sb.append("Cliente ID: ").append(p.getIdCliente())
                        .append(" | $").append(p.getMonto())
                        .append(" para ").append(p.getDestino()).append("\n");
            }
        }

        txtPrestamosPrioridad.setText(sb.toString());
    }
}
