package InterfazGrafica;

import gestores.GestorClientes;
import gestores.GestorPrestamos;
import modelos.Cliente;
import modelos.Prestamo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormPrestamo extends JFrame {
    private JPanel panelPrestamo;
    private JComboBox<String> comboClientes;
    private JTextField txtMonto;
    private JTextField txtDestino;
    private JButton btnSolicitar;
    private JTextArea txtResumenSolicitudes;
    private JLabel lblResumen;
    private JCheckBox chkDiferido;
    private JComboBox<Integer> comboCuotas;
    private JLabel lblResumenCalculo;

    public FormPrestamo() {
        setTitle("Solicitar Préstamo");
        setContentPane(panelPrestamo);
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarCampos();
        cargarClientes();
        actualizarListaSolicitudes();

        comboCuotas.addActionListener(e -> calcularResumen());

        btnSolicitar.addActionListener(e -> {
            if (comboClientes.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "No hay clientes registrados.");
                return;
            }

            int idSeleccionado = comboClientes.getSelectedIndex();
            Cliente cliente = GestorClientes.obtenerClientes().get(idSeleccionado);

            try {
                double monto = Double.parseDouble(txtMonto.getText().trim());
                String destino = txtDestino.getText().trim();
                boolean esDiferido = chkDiferido.isSelected();
                int cuotas = esDiferido ? (Integer) comboCuotas.getSelectedItem() : 0;
                double interes = esDiferido ? GestorPrestamos.calcularInteresPorCuotas(cuotas) : 0.0;

                Prestamo p = GestorPrestamos.solicitarPrestamo(cliente, monto, destino, esDiferido, cuotas, interes);
                if (p != null) {
                    JOptionPane.showMessageDialog(this, "Solicitud registrada.");
                    txtMonto.setText("");
                    txtDestino.setText("");
                    actualizarListaSolicitudes();
                } else {
                    JOptionPane.showMessageDialog(this, "Error en los datos.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese un monto válido.");
            }
        });
    }

    private void inicializarCampos() {
        for (int i = 1; i <= 72; i++) {
            comboCuotas.addItem(i);
        }
        comboCuotas.setEnabled(false);
        chkDiferido.addActionListener(e -> {
            comboCuotas.setEnabled(chkDiferido.isSelected());
            calcularResumen();
        });
        lblResumenCalculo.setText("Total estimado: $0.00 | Cuota: $0.00");
    }

    private void calcularResumen() {
        try {
            double monto = Double.parseDouble(txtMonto.getText().trim());
            if (!chkDiferido.isSelected()) {
                lblResumenCalculo.setText("Total estimado: $" + monto + " | Pago único");
                return;
            }

            int cuotas = (Integer) comboCuotas.getSelectedItem();
            double interes = GestorPrestamos.calcularInteresPorCuotas(cuotas);
            double total = monto + (monto * interes);
            double cuota = total / cuotas;

            lblResumenCalculo.setText(String.format("Total estimado: $%.2f | Cuota: $%.2f", total, cuota));

        } catch (Exception ignored) {
            lblResumenCalculo.setText("Total estimado: $0.00 | Cuota: $0.00");
        }
    }

    private void cargarClientes() {
        comboClientes.removeAllItems();
        for (Cliente c : GestorClientes.obtenerClientes()) {
            comboClientes.addItem("ID " + c.getId() + " - " + c.getNombre());
        }
    }

    private void actualizarListaSolicitudes() {
        StringBuilder sb = new StringBuilder("Solicitudes pendientes:\n");
        for (Prestamo p : GestorPrestamos.obtenerSolicitudesPendientes()) {
            sb.append("Cliente ID ").append(p.getIdCliente())
                    .append(" | $").append(p.getMonto())
                    .append(" | ").append(p.getDestino()).append("\n");
        }
        txtResumenSolicitudes.setText(sb.toString());
    }
}
