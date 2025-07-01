package InterfazGrafica;

import gestores.GestorPrestamos;
import modelos.Prestamo;

import javax.swing.*;
import java.util.PriorityQueue;

public class FormConsultarPrioridad extends JFrame {
    private JPanel panelPrincipal;
    private JTextArea txtPrioridad;
    private final GestorPrestamos gestorPrestamos;

    public FormConsultarPrioridad(GestorPrestamos gestorPrestamos) {
        this.gestorPrestamos = gestorPrestamos;

        setContentPane(panelPrincipal);
        setTitle("Préstamos Prioritarios");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        mostrarColaPrioridad();
    }

    private void mostrarColaPrioridad() {
        StringBuilder sb = new StringBuilder("🔝 Cola de Préstamos Prioritarios:\n");
        PriorityQueue<Prestamo> cola = gestorPrestamos.obtenerColaPrioridad();
        for (Prestamo p : cola) {
            sb.append("Cliente: ").append(p.getCliente().getNombre()).append(" ").append(p.getCliente().getApellido())
                    .append(" | Zona: ").append(p.getCliente().getZona())
                    .append(" | Monto: $").append(p.getMonto())
                    .append(" | Destino: ").append(p.getDestino()).append("\n");
        }
        txtPrioridad.setText(sb.toString());
    }
}
