package InterfazGrafica;

import gestores.GestorClientes;
import gestores.GestorPrestamos;
import modelos.Cliente;
import modelos.Prestamo;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class FormPrestamo extends JFrame {
    private JPanel panelPrincipal;
    private JComboBox<Cliente> comboClientes;
    private JTextField txtMonto;
    private JTextField txtDestino;
    private JButton btnSolicitar;
    private JCheckBox chkDiferido;
    private JComboBox<Integer> comboCuotas;
    private JTextArea txtResumenCalculo;
    private JButton btnCancelar;
    private JComboBox comboBox1;

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
        comboClientes.removeAllItems();
        for (Cliente c : gestorClientes.listarClientes()) {
            comboClientes.addItem(c);
        }

        int[] opcionesCuotas = {3, 6, 9, 12, 18, 24, 36, 48, 60, 72};
        for (int cuotas : opcionesCuotas) {
            comboCuotas.addItem(cuotas);
        }
    }

    private void configurarAcciones() {
        btnSolicitar.addActionListener((ActionEvent e) -> {
            Cliente cliente = (Cliente) comboClientes.getSelectedItem();
            if (cliente == null) {
                txtResumenCalculo.setText("️ Selecciona un cliente.");
                return;
            }

            try {
                double monto = Double.parseDouble(txtMonto.getText().trim());
                String destino = txtDestino.getText().trim();
                int cuotas = (int) comboCuotas.getSelectedItem();
                boolean diferido = chkDiferido.isSelected();

                double interes = gestorPrestamos.obtenerInteresPorMeses(cuotas);

                boolean exito = gestorPrestamos.solicitarPrestamo(cliente, monto, destino, cuotas);
                if (!exito) {
                    txtResumenCalculo.setText(" Cliente no califica para el préstamo.");
                    return;
                }

                // Obtener último préstamo aprobado (opcional: podrías buscarlo por ID también)
                Prestamo ultimo = gestorPrestamos.obtenerSolicitudesPendientes().getLast();

                txtResumenCalculo.setText("✅ Solicitud registrada\n" +
                        "Cliente: " + cliente.getNombre() + " " + cliente.getApellido() + "\n" +
                        "Monto: $" + monto + "\n" +
                        "Cuotas: " + cuotas + " meses\n" +
                        "Interés aplicado: " + (interes * 100) + "%\n" +
                        "Total a pagar: $" + String.format("%.2f", ultimo.calcularTotalConInteres()) + "\n" +
                        "Cuota mensual: $" + String.format("%.2f", ultimo.calcularValorCuotaAvanzada()) +
                        (diferido ? " (diferido)" : "")
                );
            } catch (NumberFormatException ex) {
                txtResumenCalculo.setText("⚠️ Ingrese un monto válido.");
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }
}
