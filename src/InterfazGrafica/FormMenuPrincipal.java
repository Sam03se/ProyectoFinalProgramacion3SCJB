package InterfazGrafica;

import gestores.GestorClientes;
import gestores.GestorPrestamos;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class FormMenuPrincipal extends JFrame {
    private JPanel panelPrincipal;
    private JButton btnRegistrarCliente;
    private JButton btnSolicitarPrestamo;
    private JButton btnAprobarPrestamos;
    private JButton btnHistorial;
    private JButton btnVerPrioridad;
    private JButton btnVerCertificados;
    private JButton btnGestionarClientes;
    private JButton btnSimularPagoCuenta;
    private JButton btnExportarResumenPrestamo;
    private JButton btnAsociarCuenta;
    private JButton getBtnVerClientes;
    JButton btnVerClientes = new JButton("Ver Clientes y Préstamos");


    private final GestorClientes gestorClientes;
    private final GestorPrestamos gestorPrestamos;

    public FormMenuPrincipal(GestorClientes gestorClientes, GestorPrestamos gestorPrestamos) {
        this.gestorClientes = gestorClientes;
        this.gestorPrestamos = gestorPrestamos;

        setTitle("Sistema de Préstamos");
        setContentPane(panelPrincipal);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        configurarAcciones();
    }

    private void configurarAcciones() {
        btnRegistrarCliente.addActionListener((ActionEvent e) -> new FormCliente(gestorClientes));
        btnSolicitarPrestamo.addActionListener((ActionEvent e) -> new FormPrestamo(gestorClientes, gestorPrestamos));
        btnAprobarPrestamos.addActionListener(e -> {
            JFrame frame = new JFrame("Aprobación Manual");
            frame.setContentPane(new FormAprobacionManual(gestorPrestamos));  // Aquí se pasa el gestor
            frame.setSize(500, 400);  // Ajusta según tu GUI
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        });

        btnHistorial.addActionListener(e -> {
            JFrame frame = new JFrame("Historial de Operaciones");
            new FormHistorial(gestorPrestamos);
            frame.setSize(500, 400);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        });

        btnVerPrioridad.addActionListener((ActionEvent e) -> new FormConsultarPrioridad(gestorPrestamos));
        btnVerCertificados.addActionListener(e -> new FormCertificados(gestorPrestamos, gestorClientes));
        btnSimularPagoCuenta.addActionListener(e -> new FormSimularPagoCuenta(gestorPrestamos, gestorClientes));
        btnExportarResumenPrestamo.addActionListener(e -> new FormExportarResumenPrestamo(gestorPrestamos));
        btnAsociarCuenta.addActionListener(e -> new FormAsociarCuentaBancaria(gestorClientes, gestorPrestamos));
        btnVerClientes.addActionListener(e -> new FormVerClientes(gestorClientes, gestorPrestamos));


    }

}
