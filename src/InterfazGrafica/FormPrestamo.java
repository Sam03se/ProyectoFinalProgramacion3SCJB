package InterfazGrafica;

import gestores.GestorClientes;
import gestores.GestorPrestamos;
import modelos.Cliente;
import modelos.Prestamo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

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

        txtResumenCalculo.setEditable(false);
    }

    private void configurarAcciones() {
        btnSolicitar.addActionListener((ActionEvent e) -> {
            Cliente cliente = (Cliente) comboClientes.getSelectedItem();
            if (cliente == null) {
                txtResumenCalculo.setText("⚠️ Selecciona un cliente.");
                return;
            }

            try {
                double monto = Double.parseDouble(txtMonto.getText().trim());
                String destino = txtDestino.getText().trim();
                int cuotas = (int) comboCuotas.getSelectedItem();
                boolean diferido = chkDiferido.isSelected();

                boolean exito = gestorPrestamos.solicitarPrestamo(cliente, monto, destino, cuotas);

                List<String> historial = gestorPrestamos.getHistorialOperaciones();
                String trazabilidad = historial.get(historial.size() - 2); // evaluación IA
                String resultado = historial.get(historial.size() - 1);    // aprobado o rechazado

                txtResumenCalculo.setText(trazabilidad + "\n" + resultado);

                if (!exito) {
                    return;
                }

                // Mostrar detalles del préstamo si fue exitoso
                Prestamo ultimo = gestorPrestamos.obtenerSolicitudesPendientes().getLast();
                double total = ultimo.calcularTotalConInteres();
                double cuota = ultimo.calcularValorCuotaAvanzada();

                txtResumenCalculo.append("\n--------------------------------------\n");
                txtResumenCalculo.append("Cliente: " + cliente.getNombre() + " " + cliente.getApellido() + "\n");
                txtResumenCalculo.append("Monto: $" + String.format("%.2f", monto) + "\n");
                txtResumenCalculo.append("Cuotas: " + cuotas + "\n");
                txtResumenCalculo.append("Interés aplicado: " + (ultimo.getInteres() * 100) + "%\n");
                txtResumenCalculo.append("Total a pagar: $" + String.format("%.2f", total) + "\n");
                txtResumenCalculo.append("Cuota mensual: $" + String.format("%.2f", cuota));
                if (diferido) txtResumenCalculo.append(" (diferido)");

            } catch (NumberFormatException ex) {
                txtResumenCalculo.setText("⚠️ Ingrese un monto válido.");
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }
}
