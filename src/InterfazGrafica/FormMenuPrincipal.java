package InterfazGrafica;

import javax.swing.*;

public class FormMenuPrincipal extends JFrame {
    private JPanel panelPrincipal;
    private JButton btnRegistrarCliente;
    private JButton btnSolicitarPrestamo;
    private JButton btnAprobarPrestamo;
    private JButton btnVerHistorial;
    private JButton btnVerPrioridad;

    public FormMenuPrincipal() {
        setTitle("Sistema de Préstamos Inclusivos");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);

        btnRegistrarCliente.addActionListener(e -> {
            new FormRegistroCliente().setVisible(true);
        });

        btnSolicitarPrestamo.addActionListener(e -> {
            new FormPrestamo().setVisible(true);
        });

        // ⛔ ELIMINAMOS APROBACIÓN AUTOMÁTICA
        // ✅ APROBACIÓN MANUAL
        btnAprobarPrestamo.addActionListener(e -> {
            new FormAprobacionManual().setVisible(true);
        });

        btnVerHistorial.addActionListener(e -> {
            new FormHistorial().setVisible(true);
        });

        btnVerPrioridad.addActionListener(e -> {
            new FormConsultarPrioridad().setVisible(true);
        });
    }
}
