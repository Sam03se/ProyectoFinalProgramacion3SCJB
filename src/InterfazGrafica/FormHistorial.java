package InterfazGrafica;

import gestores.GestorPrestamos;

import javax.swing.*;
import java.util.List;

public class FormHistorial extends JFrame {
    private JPanel panelPrincipal;
    private JTextArea txtHistorial;
    private JButton btnCerrar;

    private final GestorPrestamos gestorPrestamos;

    public FormHistorial(GestorPrestamos gestorPrestamos) {
        this.gestorPrestamos = gestorPrestamos;

        setTitle("Historial de Préstamos");
        setContentPane(panelPrincipal);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        txtHistorial.setEditable(false);

        mostrarHistorial();

        btnCerrar.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void mostrarHistorial() {
        List<String> historial = gestorPrestamos.getHistorialOperaciones();

        if (historial.isEmpty()) {
            txtHistorial.setText("No hay operaciones registradas.");
        } else {
            StringBuilder sb = new StringBuilder("🧾 HISTORIAL DE OPERACIONES\n\n");
            for (String linea : historial) {
                sb.append("• ").append(linea).append("\n");
            }
            txtHistorial.setText(sb.toString());
        }
    }

    public JPanel getPanel() {
        return panelPrincipal;
    }
}
