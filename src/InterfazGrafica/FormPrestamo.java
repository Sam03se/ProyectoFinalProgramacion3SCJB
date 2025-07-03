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

        int[] opcionesCuotas = {0, 3, 6, 9, 12, 18, 24, 36}; // incluye 0 cuotas para casos sin diferir
        for (int cuotas : opcionesCuotas) {
            cmbCuotas.addItem(cuotas);
        }

        txtResumenCalculo.setEditable(false);
    }

    private void configurarAcciones() {
        btnSolicitar.addActionListener(e -> {
            Cliente cliente = (Cliente) cmbClientes.getSelectedItem();
            if (cliente == null) return;

            // Validar si tiene préstamos activos (no pagados)
            boolean tieneActivo = false;
            for (Prestamo p : gestorPrestamos.prestamosPorCliente(cliente.getId())) {
                if (!p.estaPagadoCompletamente()) {
                    tieneActivo = true;
                    break;
                }
            }

            if (tieneActivo) {
                JOptionPane.showMessageDialog(this, "❌ Este cliente ya tiene un préstamo activo o no ha terminado de pagar el anterior.");
                return;
            }

            try {
                double monto = Double.parseDouble(txtMonto.getText());
                String destino = txtDestino.getText().trim();
                int cuotas = (int) cmbCuotas.getSelectedItem();

                if (cuotas < 0) {
                    JOptionPane.showMessageDialog(this, "❌ Cuotas inválidas.");
                    return;
                }

                boolean diferido = chkDiferido.isSelected();

                // Calcular interés según cuotas o caso especial
                double interes;
                if (!diferido && cuotas == 0) {
                    interes = 0.01;  // caso especial sin diferir
                } else if (cuotas <= 3) {
                    interes = 0.02;
                } else if (cuotas <= 6) {
                    interes = 0.04;
                } else if (cuotas <= 12) {
                    interes = 0.06;
                } else {
                    interes = 0.10;
                }

                Prestamo prestamo = new Prestamo(
                        gestorPrestamos.generarNuevoId(), cliente, monto, destino, cuotas);
                prestamo.setDiferido(diferido);
                prestamo.setInteres(interes);

                gestorPrestamos.agregarSolicitudPendiente(prestamo);
                gestorPrestamos.agregarOperacionAlHistorial("🕒 Solicitud pendiente: " + cliente + " | Monto: $" + monto);

                double total = prestamo.calcularTotalConInteres();
                double cuotaMensual = prestamo.calcularCuotaMensual();

                txtResumenCalculo.setText("📋 Resumen de Solicitud\n");
                txtResumenCalculo.append("Cliente: " + cliente.getNombre() + " " + cliente.getApellido() + "\n");
                txtResumenCalculo.append("Destino: " + destino + "\n");
                txtResumenCalculo.append("Monto: $" + monto + "\n");
                txtResumenCalculo.append("Interés: " + (interes * 100) + "%\n");
                txtResumenCalculo.append("Total con interés: $" + String.format("%.2f", total) + "\n");
                txtResumenCalculo.append("Cuotas: " + cuotas + "\n");
                txtResumenCalculo.append("Cuota mensual: $" + String.format("%.2f", cuotaMensual) + (diferido ? " (diferido)" : "") + "\n");

                JOptionPane.showMessageDialog(this, "✅ Solicitud registrada. Espere aprobación.");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Formato inválido en monto.");
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }
}
