package InterfazGrafica;

import gestores.GestorClientes;
import gestores.GestorPrestamos;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class FormMenuPrincipal extends JFrame {
    private JPanel panelPrincipal;
    private JButton btnRegistrarCliente;
    private JButton btnSolicitarPrestamo;
    private JButton btnAprobarPrestamo;
    private JButton btnVerHistorial;
    private JButton btnVerPrioridad;
    private JButton btnVerCertificados;
    private JButton btnGestionarClientes;
    private JButton btnSimularPagoCuenta;
    private JButton btnExportarResumenPrestamo;
    private JButton btnAsociarCuenta;

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
        btnAprobarPrestamo.addActionListener((ActionEvent e) -> new FormAprobacionManual(gestorPrestamos));
        btnVerHistorial.addActionListener((ActionEvent e) -> new FormHistorial(gestorPrestamos));
        btnVerPrioridad.addActionListener((ActionEvent e) -> new FormConsultarPrioridad(gestorPrestamos));
        btnVerCertificados.addActionListener(e -> new FormCertificados(gestorPrestamos, gestorClientes));
        btnSimularPagoCuenta.addActionListener(e -> new FormSimularPagoCuenta(gestorPrestamos, gestorClientes));
        btnExportarResumenPrestamo.addActionListener(e -> new FormExportarResumenPrestamo(gestorPrestamos, gestorClientes));
        btnAsociarCuenta.addActionListener(e -> new FormAsociarCuentaBancaria(gestorClientes, gestorPrestamos));

    }

}
