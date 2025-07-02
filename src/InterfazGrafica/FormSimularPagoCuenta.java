package InterfazGrafica;

import gestores.GestorClientes;
import gestores.GestorPrestamos;
import modelos.Cliente;
import modelos.Prestamo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class FormSimularPagoCuenta extends JFrame {
    private JPanel panelPrincipal;
    private JComboBox<Cliente> cmbClientes;
    private JTextArea txtEstado;
    private JPanel panelCuotas;
    private JButton btnPagarCuota;
    private final GestorClientes gestorClientes;
    private final GestorPrestamos gestorPrestamos;
    private Prestamo prestamoActual;
    private List<JCheckBox> checkboxesCuotas = new ArrayList<>();

    public FormSimularPagoCuenta(GestorPrestamos gestorPrestamos, GestorClientes gestorClientes) {
        this.gestorPrestamos = gestorPrestamos;
        this.gestorClientes = gestorClientes;

        setTitle("Simular Pago de Cuotas");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        panelPrincipal = new JPanel(new BorderLayout());

        // Panel superior: selección de cliente
        JPanel panelArriba = new JPanel(new FlowLayout());
        cmbClientes = new JComboBox<>();
        for (Cliente c : gestorClientes.listarClientes()) {
            cmbClientes.addItem(c);
        }
        panelArriba.add(new JLabel("Cliente:"));
        panelArriba.add(cmbClientes);

        btnPagarCuota = new JButton("Registrar Pago");
        panelArriba.add(btnPagarCuota);

        panelPrincipal.add(panelArriba, BorderLayout.NORTH);

        // Panel central: cuotas
        panelCuotas = new JPanel();
        panelCuotas.setLayout(new BoxLayout(panelCuotas, BoxLayout.Y_AXIS));
        JScrollPane scrollCuotas = new JScrollPane(panelCuotas);
        panelPrincipal.add(scrollCuotas, BorderLayout.CENTER);

        // Panel inferior: estado
        txtEstado = new JTextArea(5, 30);
        txtEstado.setEditable(false);
        JScrollPane scrollEstado = new JScrollPane(txtEstado);
        panelPrincipal.add(scrollEstado, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
        setVisible(true);

        // Eventos
        cmbClientes.addActionListener(e -> cargarPrestamo());
        btnPagarCuota.addActionListener(this::pagarCuotaSeleccionada);

        cargarPrestamo();  // cargar primero
    }

    private void cargarPrestamo() {
        panelCuotas.removeAll();
        checkboxesCuotas.clear();
        txtEstado.setText("");

        Cliente cliente = (Cliente) cmbClientes.getSelectedItem();
        if (cliente == null || cliente.getCuentaBancaria() == null) {
            txtEstado.setText("⚠️ El cliente no tiene cuenta asociada.");
            panelCuotas.revalidate();
            panelCuotas.repaint();
            return;
        }

        prestamoActual = gestorPrestamos.buscarPrestamoAprobadoPorCliente(cliente);
        if (prestamoActual == null) {
            txtEstado.setText("❌ El cliente no tiene préstamo aprobado.");
            panelCuotas.revalidate();
            panelCuotas.repaint();
            return;
        }

        txtEstado.setText("Préstamo de $" + prestamoActual.getMonto() +
                " | Cuotas: " + prestamoActual.getCuotas() +
                " | Pagadas: " + prestamoActual.getCuotasPagadas() +
                " | Total a pagar: $" + String.format("%.2f", prestamoActual.calcularTotalConInteres()) + "\n");

        for (int i = 1; i <= prestamoActual.getCuotas(); i++) {
            JCheckBox check = new JCheckBox("Cuota " + i);
            check.setEnabled(false);
            if (i <= prestamoActual.getCuotasPagadas()) {
                check.setSelected(true);
            }
            checkboxesCuotas.add(check);
            panelCuotas.add(check);
        }

        panelCuotas.revalidate();
        panelCuotas.repaint();
    }

    private void pagarCuotaSeleccionada(ActionEvent e) {
        if (prestamoActual == null || prestamoActual.estaPagado()) {
            txtEstado.setText("✅ Préstamo ya ha sido completamente pagado.");
            return;
        }

        prestamoActual.pagarCuota();
        gestorPrestamos.agregarOperacionAlHistorial("💵 Pago de cuota registrado para préstamo ID: " + prestamoActual.getId());

        cargarPrestamo();

        if (prestamoActual.estaPagado()) {
            txtEstado.append("\n✅ ¡El cliente ha terminado de pagar el préstamo!");
        } else {
            txtEstado.append("\n📌 Cuota registrada. Aún quedan cuotas pendientes.");
        }
    }
}
