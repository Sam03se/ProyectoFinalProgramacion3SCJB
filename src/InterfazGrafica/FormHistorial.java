package InterfazGrafica;

import gestores.GestorPrestamos;

import javax.swing.*;
import java.util.List;

public class FormHistorial extends JFrame {
    private JPanel panelHistorial;
    private JTextArea txtHistorial;

    public FormHistorial() {
        setTitle("Historial de Operaciones");
        setContentPane(panelHistorial);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        mostrarHistorial();
    }

    private void mostrarHistorial() {
        List<String> historial = GestorPrestamos.obtenerHistorial();

        if (historial.isEmpty()) {
            txtHistorial.setText("No hay registros en el historial.");
            return;
        }

        StringBuilder sb = new StringBuilder("HISTORIAL DE OPERACIONES\n\n");
        for (String evento : historial) {
            sb.append("- ").append(evento).append("\n");
        }

        txtHistorial.setEditable(false);
        txtHistorial.setText(sb.toString());
    }
}
