package InterfazGrafica;

import run.HistorialOperaciones;

import javax.swing.*;
import java.util.Stack;

public class FormHistorial extends JFrame {
    private JPanel panelHistorial;
    private JTextArea txtHistorial;

    public FormHistorial() {
        setTitle("Historial de Operaciones");
        setContentPane(panelHistorial);
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        cargarHistorial();
    }

    private void cargarHistorial() {
        Stack<String> historial = HistorialOperaciones.obtenerHistorial();
        StringBuilder sb = new StringBuilder("Historial de acciones:\n");
        for (int i = historial.size() - 1; i >= 0; i--) {
            sb.append(historial.get(i)).append("\n");
        }
        txtHistorial.setText(sb.toString());
    }
}
