package InterfazGrafica;

import gestores.GestorClientes;
import gestores.GestorPrestamos;

import javax.swing.*;

public class FormMenuPrincipal extends JFrame {
    private JPanel panelPrincipal;
    private JButton btnRegistrarCliente;
    private JButton btnSolicitarPrestamo;
    private JButton btnAprobarPrestamo;
    private JButton btnVerHistorial;
    private JButton btnVerPrioridad;
    private JButton btnVerCertificados;
    private JButton btnGestionClientes;

    private GestorClientes gestorClientes;
    private GestorPrestamos gestorPrestamos;

    public FormMenuPrincipal() {
        setTitle("Sistema de Préstamos");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        gestorClientes = new GestorClientes();
        gestorPrestamos = new GestorPrestamos();

        btnRegistrarCliente.addActionListener(e -> new FormRegistroCliente(gestorClientes).setVisible(true));
        btnSolicitarPrestamo.addActionListener(e -> new FormPrestamo(gestorClientes, gestorPrestamos).setVisible(true));
        btnAprobarPrestamo.addActionListener(e -> new FormAprobacionManual(gestorPrestamos).setVisible(true));
        btnVerHistorial.addActionListener(e -> new FormHistorial(gestorPrestamos).setVisible(true));
        btnVerPrioridad.addActionListener(e -> new FormConsultarPrioridad(gestorPrestamos).setVisible(true));
        btnVerCertificados.addActionListener(e -> new FormCertificados(gestorPrestamos, gestorClientes).setVisible(true));
        btnGestionClientes.addActionListener(e -> new FormCliente(gestorClientes).setVisible(true));

        setVisible(true);
    }
}
