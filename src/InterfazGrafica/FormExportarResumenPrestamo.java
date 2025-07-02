package InterfazGrafica;

import gestores.GestorPrestamos;
import modelos.Prestamo;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class FormExportarResumenPrestamo extends JFrame {
    private final GestorPrestamos gestorPrestamos;
    private JComboBox<Prestamo> cmbPrestamos;
    private JTextArea txtResumen;
    private JRadioButton rAprobado;
    private JRadioButton rPagado;
    private JButton btnExportar;

    public FormExportarResumenPrestamo(GestorPrestamos gestorPrestamos) {
        this.gestorPrestamos = gestorPrestamos;

        setTitle("Exportar Certificado de Préstamo");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Panel superior de selección
        JPanel panelFiltro = new JPanel(new FlowLayout());

        cmbPrestamos = new JComboBox<>();
        for (Prestamo p : gestorPrestamos.obtenerPrestamosAprobados()) {
            cmbPrestamos.addItem(p);
        }

        rAprobado = new JRadioButton("Aprobado", true);
        rPagado = new JRadioButton("Pagado");
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rAprobado);
        grupo.add(rPagado);

        panelFiltro.add(new JLabel("Préstamo:"));
        panelFiltro.add(cmbPrestamos);
        panelFiltro.add(rAprobado);
        panelFiltro.add(rPagado);

        panelPrincipal.add(panelFiltro, BorderLayout.NORTH);

        // Área de resumen
        txtResumen = new JTextArea();
        txtResumen.setEditable(false);
        JScrollPane scrollResumen = new JScrollPane(txtResumen);
        panelPrincipal.add(scrollResumen, BorderLayout.CENTER);

        // Botón de exportar
        btnExportar = new JButton("Exportar como .txt");
        panelPrincipal.add(btnExportar, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
        setVisible(true);

        btnExportar.addActionListener(e -> exportarResumen());
    }

    private void exportarResumen() {
        Prestamo p = (Prestamo) cmbPrestamos.getSelectedItem();
        if (p == null) return;

        boolean esAprobado = rAprobado.isSelected();
        boolean esPagado = p.estaPagado();

        if ((esAprobado && esPagado) || (!esAprobado && !esPagado)) {
            JOptionPane.showMessageDialog(this, "⚠️ El tipo de certificado no coincide con el estado del préstamo.");
            return;
        }

        String resumen = generarCertificadoTexto(p);
        txtResumen.setText(resumen);

        try {
            FileWriter fw = new FileWriter("certificado_prestamo.txt");
            fw.write(resumen);
            fw.close();
            JOptionPane.showMessageDialog(this, "✅ Certificado exportado correctamente.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "❌ Error al exportar: " + ex.getMessage());
        }
    }

    private String generarCertificadoTexto(Prestamo p) {
        return "📄 CERTIFICADO DE PRÉSTAMO\n\n" +
                "Cliente: " + p.getCliente().getNombre() + " " + p.getCliente().getApellido() + "\n" +
                "Destino: " + p.getDestino() + "\n" +
                "Monto: $" + String.format("%.2f", p.getMonto()) + "\n" +
                "Cuotas: " + p.getCuotasPagadas() + " de " + p.getCuotas() + "\n" +
                "Interés: " + String.format("%.2f", p.getInteres() * 100) + "%\n" +
                "Total a pagar: $" + String.format("%.2f", p.calcularTotalConInteres()) + "\n" +
                (p.estaPagado() ? "📌 Estado: COMPLETAMENTE PAGADO" : "📌 Estado: EN CURSO") + "\n";
    }
}
