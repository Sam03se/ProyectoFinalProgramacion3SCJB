package InterfazGrafica;

import gestores.GestorClientes;
import gestores.GestorPrestamos;
import modelos.Prestamo;
import modelos.Cliente;

import javax.swing.*;
import java.util.List;

public class FormCertificados extends JFrame {
    private JPanel panelPrincipal;
    private JTextArea txtCertificados;

    private final GestorPrestamos gestorPrestamos;
    private final GestorClientes gestorClientes;

    public FormCertificados(GestorPrestamos gestorPrestamos, GestorClientes gestorClientes) {
        this.gestorPrestamos = gestorPrestamos;
        this.gestorClientes = gestorClientes;

        setContentPane(panelPrincipal);
        setTitle("Certificados de Préstamos Aprobados");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        mostrarCertificados();
        setVisible(true);
    }

    private void mostrarCertificados() {
        StringBuilder sb = new StringBuilder("📄 Certificados de préstamos:\n\n");
        List<Prestamo> lista = gestorPrestamos.obtenerPrestamosAprobados();
        if (lista.isEmpty()) {
            sb.append("No hay préstamos aprobados.");
        } else {
            for (Prestamo p : lista) {
                Cliente c = p.getCliente();
                sb.append("Cliente: ").append(c.getNombre()).append(" ").append(c.getApellido()).append("\n")
                        .append("Monto aprobado: $").append(String.format("%.2f", p.getMonto())).append("\n")
                        .append("Cuotas: ").append(p.getCuotas()).append(" | Interés: ")
                        .append(String.format("%.2f", p.getInteres() * 100)).append("%\n")
                        .append("Total a pagar: $").append(String.format("%.2f", p.calcularTotalConInteres())).append("\n")
                        .append("----------------------------------------\n");
            }
        }
        txtCertificados.setText(sb.toString());
    }

    private void createUIComponents() {
        // si usas .form, deja esto vacío
    }
}
