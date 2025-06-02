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
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        mostrarPrestamos();
    }

    private void mostrarPrestamos() {
        List<Prestamo> lista = GestorPrestamos.obtenerPrestamosAprobados();
        StringBuilder sb = new StringBuilder();

        if (lista.isEmpty()) {
            sb.append("No hay préstamos aprobados.");
        } else {
            for (Prestamo p : lista) {
                sb.append("ID Préstamo: ").append(p.getId())
                        .append(" | Cliente ID: ").append(p.getIdCliente())
                        .append(" | $").append(p.getMonto())
                        .append(" | Destino: ").append(p.getDestino()).append("\n");
            }
        }

        txtPrestamosPrioridad.setEditable(false);
        txtPrestamosPrioridad.setText(sb.toString());
    }
}
