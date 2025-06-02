package InterfazGrafica;

import gestores.GestorPrestamos;

import javax.swing.*;

public class FormHistorial extends JFrame {
    private JPanel panelPrincipal;
    private JTextArea txtHistorial;
    private final GestorPrestamos gestorPrestamos;

    public FormHistorial(GestorPrestamos gestorPrestamos) {
        this.gestorPrestamos = gestorPrestamos;

        setContentPane(panelPrincipal);
        setTitle("Historial de Operaciones");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        mostrarHistorial();
    }

    private void mostrarHistorial() {
        StringBuilder sb = new StringBuilder("📜 Historial:\n");
        for (String linea : gestorPrestamos.getHistorialOperaciones()) {
            sb.append("• ").append(linea).append("\n");
        }
        txtHistorial.setText(sb.toString());
    }
}
