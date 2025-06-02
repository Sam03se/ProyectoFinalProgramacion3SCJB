package InterfazGrafica;

import gestores.GestorClientes;
import gestores.GestorPrestamos;
import modelos.Cliente;

import javax.swing.*;

public class FormPrestamo extends JFrame {
    private JPanel panelPrincipal;

    private JLabel lblCliente;
    private JComboBox<String> comboClientes;

    private JLabel lblMonto;
    private JTextField txtMonto;

    private JLabel lblDestino;
    private JTextField txtDestino;

    private JCheckBox chkDiferido;
    private JLabel lblCuotas;
    private JComboBox<Integer> comboCuotas;

    private JLabel lblResumen;
    private JTextArea txtResumenCalculo;

    private JButton btnSolicitar;
    private JButton btnCancelar;

    public FormPrestamo() {
        setTitle("Solicitar Préstamo");
        setSize(500, 600);
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarCampos();

        chkDiferido.addActionListener(e -> {
            comboCuotas.setEnabled(chkDiferido.isSelected());
            actualizarResumen();
        });

        comboCuotas.addActionListener(e -> actualizarResumen());

        btnSolicitar.addActionListener(e -> procesarSolicitud());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void inicializarCampos() {
        comboClientes.removeAllItems();
        for (Cliente c : GestorClientes.obtenerClientes()) {
            comboClientes.addItem(c.getId() + " - " + c.getNombre());
        }

        comboCuotas.removeAllItems();
        for (int i = 1; i <= 72; i++) {
            comboCuotas.addItem(i);
        }
        comboCuotas.setEnabled(false);

        txtResumenCalculo.setEditable(false);
        txtResumenCalculo.setText("Resumen del préstamo:\nTotal estimado: $0.00\nCuota mensual: $0.00");
    }

    private void actualizarResumen() {
        try {
            double monto = Double.parseDouble(txtMonto.getText().trim());
            String destino = txtDestino.getText().trim();
            if (!chkDiferido.isSelected()) {
                txtResumenCalculo.setText("Destino: " + destino + "\nPago único\nMonto total: $" + monto);
                return;
            }

            int cuotas = (Integer) comboCuotas.getSelectedItem();
            double interes = GestorPrestamos.obtenerInteresPorMeses(cuotas);
            double total = monto + (monto * interes);
            double cuota = total / cuotas;

            String resumen = String.format("""
                Monto solicitado: $%.2f
                Destino: %s
                Interés aplicado: %.2f%%
                Cuotas: %d
                Total a pagar: $%.2f
                Valor por cuota: $%.2f
                """, monto, destino, interes * 100, cuotas, total, cuota);

            txtResumenCalculo.setText(resumen);
        } catch (Exception e) {
            txtResumenCalculo.setText("Error en el cálculo. Verifique los campos.");
        }
    }

    private void procesarSolicitud() {
        if (comboClientes.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente.");
            return;
        }

        String montoStr = txtMonto.getText().trim();
        String destino = txtDestino.getText().trim();

        if (montoStr.isEmpty() || destino.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar todos los campos.");
            return;
        }

        double monto;
        try {
            monto = Double.parseDouble(montoStr);
            if (monto <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Monto inválido.");
            return;
        }

        int cuotas = chkDiferido.isSelected() ? (Integer) comboCuotas.getSelectedItem() : 1;
        int idCliente = GestorClientes.obtenerClientes().get(comboClientes.getSelectedIndex()).getId();

        GestorPrestamos.solicitarPrestamo(idCliente, monto, destino, cuotas);

        JOptionPane.showMessageDialog(this, "Solicitud registrada. Aún no ha sido aprobada.");
        dispose();
    }

    }

