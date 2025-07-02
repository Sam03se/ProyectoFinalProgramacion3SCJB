package InterfazGrafica;

import gestores.GestorClientes;
import gestores.GestorPrestamos;
import modelos.Cliente;
import modelos.Prestamo;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;

public class FormExportarResumenPrestamo extends JFrame {
    private JPanel panelPrincipal;
    private JTextField txtIdCliente;
    private JTextArea txtResumen;
    private JButton btnGenerar;
    private JButton btnExportar;

    private final GestorPrestamos gestorPrestamos;
    private final GestorClientes gestorClientes;

    public FormExportarResumenPrestamo(GestorPrestamos gestorPrestamos, GestorClientes gestorClientes) {
        this.gestorPrestamos = gestorPrestamos;
        this.gestorClientes = gestorClientes;

        setTitle("Exportar Resumen de Préstamo");
        setContentPane(panelPrincipal);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        btnGenerar.addActionListener(e -> mostrarResumen());
        btnExportar.addActionListener(e -> exportarResumen());
    }

    private void mostrarResumen() {
        try {
            int id = Integer.parseInt(txtIdCliente.getText().trim());
            Cliente c = gestorClientes.buscarPorId(id);
            if (c == null) {
                txtResumen.setText("❌ Cliente no encontrado.");
                return;
            }

            Prestamo p = gestorPrestamos.buscarPrestamoAprobadoPorCliente(c);
            if (p == null) {
                txtResumen.setText("⚠️ No hay préstamo aprobado.");
                return;
            }

            String resumen = "📄 COMPROBANTE DE PRÉSTAMO\n"
                    + "Cliente: " + c.getNombre() + " " + c.getApellido() + "\n"
                    + "Cédula: " + c.getCedula() + "\n"
                    + "Monto aprobado: $" + String.format("%.2f", p.getMonto()) + "\n"
                    + "Cuotas: " + p.getCuotas() + "\n"
                    + "Interés: " + (p.getInteres() * 100) + "%\n"
                    + "Cuenta bancaria: " + (c.getCuentaBancaria() != null ? c.getCuentaBancaria() : "No registrada") + "\n"
                    + "Total a pagar: $" + String.format("%.2f", p.calcularTotalConInteres());

            txtResumen.setText(resumen);

        } catch (Exception ex) {
            txtResumen.setText("❌ Error: " + ex.getMessage());
        }
    }

    private void exportarResumen() {
        String contenido = txtResumen.getText();
        if (contenido.isEmpty() || contenido.startsWith("❌") || contenido.startsWith("⚠️")) {
            JOptionPane.showMessageDialog(this, "⚠️ No hay resumen válido para exportar.");
            return;
        }

        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar resumen");
            int resultado = fileChooser.showSaveDialog(this);

            if (resultado == JFileChooser.APPROVE_OPTION) {
                FileWriter fw = new FileWriter(fileChooser.getSelectedFile() + ".txt");
                fw.write(contenido);
                fw.close();
                JOptionPane.showMessageDialog(this, "✅ Resumen exportado exitosamente.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al guardar: " + e.getMessage());
        }
    }
}
