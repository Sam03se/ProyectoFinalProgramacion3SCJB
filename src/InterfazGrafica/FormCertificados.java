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
        StringBuilder sb = new StringBuilder("📄 CERTIFICADOS DE PRÉSTAMOS\n\n");

        List<Prestamo> lista = gestorPrestamos.obtenerPrestamosAprobados();

        if (lista.isEmpty()) {
            sb.append("No hay préstamos aprobados.");
        } else {
            sb.append("🔵 ACTIVOS:\n");
            for (Prestamo p : lista) {
                if (!p.estaPagado()) {
                    sb.append(certificadoTexto(p)).append("\n");
                }
            }

            sb.append("\n🟢 COMPLETAMENTE PAGADOS:\n");
            for (Prestamo p : lista) {
                if (p.estaPagado()) {
                    sb.append(certificadoTexto(p)).append("\n");
                }
            }
        }

        txtCertificados.setText(sb.toString());
    }

    private String certificadoTexto(Prestamo p) {
        Cliente c = p.getCliente();
        return "Cliente: " + c.getNombre() + " " + c.getApellido() + "\n"
                + "Monto aprobado: $" + String.format("%.2f", p.getMonto()) + "\n"
                + "Cuotas: " + p.getCuotas() + " | Pagadas: " + p.getCuotasPagadas() + "\n"
                + "Interés: " + String.format("%.2f", p.getInteres() * 100) + "%\n"
                + "Total a pagar: $" + String.format("%.2f", p.calcularTotalConInteres()) + "\n"
                + "----------------------------------------";
    }
}