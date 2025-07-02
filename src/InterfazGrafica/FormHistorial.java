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
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        txtHistorial.setEditable(false);

        List<String> historial = gestorPrestamos.getHistorialOperaciones();
        if (historial.isEmpty()) {
            txtHistorial.setText("No hay operaciones registradas.");
        } else {
            txtHistorial.append("📥 Solicitudes Registradas\n");
            for (String linea : historial) {
                if (linea.startsWith("📥")) txtHistorial.append("• " + linea + "\n");
            }

            txtHistorial.append("\n✅ Préstamos Aprobados\n");
            for (String linea : historial) {
                if (linea.startsWith("✅")) txtHistorial.append("• " + linea + "\n");
            }

            txtHistorial.append("\n💵 Pagos Registrados\n");
            for (String linea : historial) {
                if (linea.startsWith("💵")) txtHistorial.append("• " + linea + "\n");
            }
        }


        btnCerrar.addActionListener(e -> dispose());
    }

    public JPanel getPanel() {
        return panelPrincipal;
    }
}
