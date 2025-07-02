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

        int[] opcionesCuotas = {3, 6, 9, 12, 18, 24, 36, 48, 60, 72};
        for (int cuotas : opcionesCuotas) {
            cmbCuotas.addItem(cuotas);
        }

        txtResumenCalculo.setEditable(false);
    }

    private void configurarAcciones() {
        btnSolicitar.addActionListener(e -> {
            String seleccionado = (String) cmbClientes.getSelectedItem();
            if (seleccionado == null) return;

            int id = Integer.parseInt(seleccionado.split(" - ")[0].trim());
            Cliente cliente = gestorClientes.buscarPorId(id);
            if (cliente == null) return;

            try {
                double monto = Double.parseDouble(txtMonto.getText());
                String destino = txtDestino.getText().trim();
                int cuotas = Integer.parseInt(cmbCuotas.getSelectedItem().toString());

                if (cuotas <= 0) {
                    JOptionPane.showMessageDialog(this, "Las cuotas deben ser mayores a cero.");
                    return;
                }

                boolean diferido = chkDiferido.isSelected();

                // Asignar interés según las cuotas
                double interes = (cuotas <= 6) ? 0.05 : 0.10;

                // Crear el préstamo
                Prestamo prestamo = new Prestamo(
                        gestorPrestamos.listarPrestamos().size() + 1,
                        cliente, monto, destino, cuotas
                );
                prestamo.setDiferido(diferido);
                prestamo.setInteres(interes);

                if (!gestorPrestamos.agregarPrestamo(prestamo)) {
                    JOptionPane.showMessageDialog(this, "Ya existe un préstamo con ese ID.");
                    return;
                }

                double total = prestamo.calcularTotalConInteres();
                double cuota = prestamo.calcularCuotaMensual();

                txtResumenCalculo.setText("");
                txtResumenCalculo.append("Cliente: " + cliente.getNombre() + "\n");
                txtResumenCalculo.append("Monto: $" + String.format("%.2f", monto) + "\n");
                txtResumenCalculo.append("Cuotas: " + cuotas + "\n");
                txtResumenCalculo.append("Interés aplicado: " + (interes * 100) + "%\n");
                txtResumenCalculo.append("Total a pagar: $" + String.format("%.2f", total) + "\n");

                if (diferido) {
                    txtResumenCalculo.append("Cuota mensual: $" + String.format("%.2f", cuota) + " (diferido)\n");
                } else {
                    txtResumenCalculo.append("Cuota mensual: $" + String.format("%.2f", cuota) + "\n");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error en el formato de monto.");
            }
        });
        btnCancelar.addActionListener(e -> dispose());
    }
}
