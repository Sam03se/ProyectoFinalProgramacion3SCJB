package InterfazGrafica;

import gestores.GestorPrestamos;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class FormHistorial extends JFrame {
    private JPanel panelPrincipal;
    private JTextArea txtHistorial;
    private JButton btnActualizar;
    private JButton btnLimpiar;
    private final GestorPrestamos gestorPrestamos;

    public FormHistorial(GestorPrestamos gestorPrestamos) {
        this.gestorPrestamos = gestorPrestamos;

        setTitle("Historial de Operaciones");
        setContentPane(panelPrincipal);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        txtHistorial.setEditable(false);

        mostrarHistorial();

        btnActualizar.addActionListener((ActionEvent e) -> mostrarHistorial());

        btnLimpiar.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Deseas limpiar solo el historial? Esta acción no afecta los préstamos.",
                    "Confirmación", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                gestorPrestamos.limpiarSoloHistorial();
                txtHistorial.setText("🧹 Historial limpiado correctamente.");
            }
        });

    }

    private void mostrarHistorial() {
        StringBuilder sb = new StringBuilder("📜 Historial de Operaciones\n\n");

        int contador = 1;
        for (String linea : gestorPrestamos.getHistorialOperaciones()) {
            sb.append("[").append(contador++).append("] ").append(linea).append("\n----------------------------\n");
        }

        if (contador == 1) {
            sb.append("Sin operaciones registradas.");
        }

        txtHistorial.setText(sb.toString());
        txtHistorial.setCaretPosition(txtHistorial.getText().length()); // scroll abajo
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
