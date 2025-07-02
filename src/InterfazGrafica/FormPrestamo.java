package InterfazGrafica;

import gestores.GestorClientes;
import gestores.GestorPrestamos;
import modelos.Cliente;
import modelos.Prestamo;

import javax.swing.*;

public class FormPrestamo extends JFrame {
    private JPanel panelPrincipal;
    private JComboBox<Cliente> cmbClientes;
    private JTextField txtMonto;
    private JTextField txtDestino;
    private JButton btnSolicitar;
    private JCheckBox chkDiferido;
    private JComboBox<Integer> cmbCuotas;
    private JTextArea txtResumenCalculo;
    private JButton btnCancelar;

    private final GestorClientes gestorClientes;
    private final GestorPrestamos gestorPrestamos;

    public FormPrestamo(GestorClientes gestorClientes, GestorPrestamos gestorPrestamos) {
        this.gestorClientes = gestorClientes;
        this.gestorPrestamos = gestorPrestamos;

        setContentPane(panelPrincipal);
        setTitle("Solicitar Préstamo");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        inicializarCampos();
        configurarAcciones();
    }

    private void inicializarCampos() {
        cmbClientes.removeAllItems();
        for (Cliente c : gestorClientes.listarClientes()) {
            cmbClientes.addItem(c);
        }

        int[] opcionesCuotas = {0, 3, 6, 9, 12, 18, 24, 36, 48, 60, 72};
        for (int cuotas : opcionesCuotas) {
            cmbCuotas.addItem(cuotas);
        }

        txtResumenCalculo.setEditable(false);
    }

    private void configurarAcciones() {
        btnSolicitar.addActionListener(e -> {
            Cliente cliente = (Cliente) cmbClientes.getSelectedItem();
            if (cliente == null) return;

            try {
                double monto = Double.parseDouble(txtMonto.getText());
                String destino = txtDestino.getText().trim();
                int cuotas = (int) cmbCuotas.getSelectedItem();

                if (cuotas < 0) {
                    JOptionPane.showMessageDialog(this, "Las cuotas no pueden ser negativas.");
                    return;
                }

                boolean diferido = chkDiferido.isSelected();
                double interes;

                // Aplicar lógica de interés según cuotas
                if (cuotas == 0) {
                    interes = 0.015; // 1.5%
                } else if (cuotas <= 3) {
                    interes = 0.025; // 2.5%
                } else if (cuotas <= 6) {
                    interes = 0.04;  // 4%
                } else if (cuotas <= 12) {
                    interes = 0.06;  // 6%
                } else if (cuotas <= 24) {
                    interes = 0.08;  // 8%
                } else {
                    interes = 0.12;  // 12%
                }

                Prestamo prestamo = new Prestamo(
                        gestorPrestamos.generarNuevoId(),
                        cliente, monto, destino, cuotas
                );
                prestamo.setDiferido(diferido);
                prestamo.setInteres(interes);

                gestorPrestamos.agregarSolicitudPendiente(prestamo);

                double total = prestamo.calcularTotalConInteres();
                double cuota = prestamo.calcularCuotaMensual();
                double valorInteres = total - monto;

                txtResumenCalculo.setText("");
                txtResumenCalculo.append("Cliente: " + cliente.getNombre() + "\n");
                txtResumenCalculo.append("Monto solicitado: $" + String.format("%.2f", monto) + "\n");
                txtResumenCalculo.append("Interés aplicado: " + (interes * 100) + "%\n");
                txtResumenCalculo.append("Valor del interés: $" + String.format("%.2f", valorInteres) + "\n");
                txtResumenCalculo.append("Total a pagar: $" + String.format("%.2f", total) + "\n");
                txtResumenCalculo.append("Cuotas: " + cuotas + "\n");

                if (diferido) {
                    txtResumenCalculo.append("Cuota mensual: $" + String.format("%.2f", cuota) + " (diferido)\n");
                } else {
                    txtResumenCalculo.append("Cuota mensual: $" + String.format("%.2f", cuota) + "\n");
                }

                JOptionPane.showMessageDialog(this, "Solicitud registrada exitosamente. Espere aprobación.");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error en el formato de monto.");
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }
}
