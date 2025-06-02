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
    private JLabel lblCliente;
    private JLabel lblMonto;
    private JLabel lblDestino;
    private JLabel lblResumen;

    public FormPrestamo() {
        setTitle("Solicitud de Préstamo");
        setContentPane(panelPrestamo);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarEtiquetas();
        cargarClientes();
        actualizarListaSolicitudes();

        btnSolicitar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboClientes.getItemCount() == 0) {
                    JOptionPane.showMessageDialog(FormPrestamo.this, "No hay clientes registrados.");
                    return;
                }

                int idSeleccionado = comboClientes.getSelectedIndex();
                Cliente cliente = GestorClientes.obtenerClientes().get(idSeleccionado);

                try {
                    double monto = Double.parseDouble(txtMonto.getText());
                    String destino = txtDestino.getText().trim();

                    if (monto <= 0 || destino.isEmpty()) {
                        JOptionPane.showMessageDialog(FormPrestamo.this, "Ingrese un monto mayor a 0 y un destino válido.");
                        return;
                    }

                    Prestamo p = GestorPrestamos.solicitarPrestamo(cliente, monto, destino);
                    if (p != null) {
                        JOptionPane.showMessageDialog(FormPrestamo.this, "Solicitud registrada correctamente para: " + cliente.getNombre());
                        txtMonto.setText("");
                        txtDestino.setText("");
                        actualizarListaSolicitudes();
                    } else {
                        JOptionPane.showMessageDialog(FormPrestamo.this, "Error al registrar el préstamo.");
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(FormPrestamo.this, "Ingrese un número válido en el campo de monto.");
                }
            }
        });
    }

    private void inicializarEtiquetas() {
        lblCliente.setText("Seleccione un cliente:");
        lblMonto.setText("Monto del préstamo (USD):");
        lblDestino.setText("Destino del préstamo:");
        lblResumen.setText("Solicitudes pendientes:");
        txtResumenSolicitudes.setEditable(false);
    }

    private void cargarClientes() {
        comboClientes.removeAllItems();
        for (Cliente c : GestorClientes.obtenerClientes()) {
            comboClientes.addItem("ID " + c.getId() + " - " + c.getNombre());
        }
    }

    private void actualizarListaSolicitudes() {
        StringBuilder sb = new StringBuilder();
        for (Prestamo p : GestorPrestamos.obtenerSolicitudesPendientes()) {
            sb.append("Cliente ID ").append(p.getIdCliente())
                    .append(" | Monto: $").append(p.getMonto())
                    .append(" | Destino: ").append(p.getDestino()).append("\n");
        }
        if (sb.length() == 0) {
            sb.append("No hay solicitudes pendientes.");
        }
        txtResumenSolicitudes.setText(sb.toString());
    }

    // Método que se puede usar desde el menú principal
    public static boolean aprobarPrestamoDesdeBoton() {
        Prestamo aprobado = GestorPrestamos.aprobarPrestamo();
        if (aprobado != null) {
            JOptionPane.showMessageDialog(null,
                    "Préstamo aprobado:\n" +
                            "Cliente ID: " + aprobado.getIdCliente() +
                            "\nMonto: $" + aprobado.getMonto() +
                            "\nDestino: " + aprobado.getDestino());
            return true;
        }
        return false;
    }
}
