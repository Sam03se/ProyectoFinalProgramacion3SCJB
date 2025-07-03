package InterfazGrafica;

import gestores.GestorPrestamos;
import modelos.Prestamo;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class FormAprobacionManual extends JPanel {
    private JList<String> listaSolicitudes;
    private JButton btnAprobarSeleccionado;
    private JButton btnRechazarSeleccionado;
    private JTextArea txtResumenAprobacion;
    private JPanel FormAprobacionManual;
    private DefaultListModel<String> modeloLista;
    private GestorPrestamos gestorPrestamos;

    public FormAprobacionManual(GestorPrestamos gestorPrestamos) {
        this.gestorPrestamos = gestorPrestamos;
        setLayout(null);

        JLabel lblTitulo = new JLabel("Solicitudes Pendientes");
        lblTitulo.setBounds(20, 10, 200, 25);
        add(lblTitulo);

        modeloLista = new DefaultListModel<>();
        listaSolicitudes = new JList<>(modeloLista);
        JScrollPane scroll = new JScrollPane(listaSolicitudes);
        scroll.setBounds(20, 40, 400, 150);
        add(scroll);

        btnAprobarSeleccionado = new JButton("Aprobar Seleccionado");
        btnAprobarSeleccionado.setBounds(20, 200, 200, 30);
        add(btnAprobarSeleccionado);

        btnRechazarSeleccionado = new JButton("Rechazar Seleccionado");
        btnRechazarSeleccionado.setBounds(230, 200, 200, 30);
        add(btnRechazarSeleccionado);

        txtResumenAprobacion = new JTextArea();
        txtResumenAprobacion.setEditable(false);
        JScrollPane scrollResumen = new JScrollPane(txtResumenAprobacion);
        scrollResumen.setBounds(20, 240, 400, 120);
        add(scrollResumen);

        cargarSolicitudes();

        btnAprobarSeleccionado.addActionListener((ActionEvent e) -> aprobarSolicitud());
        btnRechazarSeleccionado.addActionListener((ActionEvent e) -> rechazarSolicitud());
    }

    private void aprobarSolicitud() {
        int index = listaSolicitudes.getSelectedIndex();
        if (index >= 0) {
            String selectedValue = modeloLista.getElementAt(index);
            int id = extraerIdDesdeTexto(selectedValue);

            Prestamo p = buscarSolicitudPorId(id);

            if (p != null) {
                gestorPrestamos.getSolicitudesPendientes().remove(p);
                gestorPrestamos.agregarPrestamo(p);

                String resumen = "✅ Préstamo aprobado\n"
                        + "Cliente: " + p.getCliente().getNombre() + " " + p.getCliente().getApellido() + "\n"
                        + "Monto: $" + String.format("%.2f", p.getMonto()) + "\n"
                        + "Destino: " + p.getDestino() + "\n"
                        + "Cuotas: " + p.getCuotas() + "\n"
                        + "Interés aplicado: " + (p.getInteres() * 100) + "%\n"
                        + "Total a pagar: $" + String.format("%.2f", p.calcularTotalConInteres()) + "\n"
                        + "Cuota mensual: $" + String.format("%.2f", p.calcularCuotaMensual());

                txtResumenAprobacion.setText(resumen);
                gestorPrestamos.agregarOperacionAlHistorial("📥 Aprobado préstamo ID " + p.getId()
                        + " para " + p.getCliente().getNombre() + " por $" + String.format("%.2f", p.getMonto()));

                cargarSolicitudes();
            } else {
                txtResumenAprobacion.setText("❌ No se pudo encontrar la solicitud.");
            }
        } else {
            txtResumenAprobacion.setText("⚠️ Selecciona una solicitud para aprobar.");
        }
    }

    private void rechazarSolicitud() {
        int index = listaSolicitudes.getSelectedIndex();
        if (index >= 0) {
            String selectedValue = modeloLista.getElementAt(index);
            int id = extraerIdDesdeTexto(selectedValue);

            Prestamo p = buscarSolicitudPorId(id);

            if (p != null) {
                String razon = JOptionPane.showInputDialog(this, "Motivo del rechazo:", "Rechazar Solicitud", JOptionPane.PLAIN_MESSAGE);
                if (razon != null && !razon.trim().isEmpty()) {
                    gestorPrestamos.getSolicitudesPendientes().removeIf(prestamo -> prestamo.getId() == p.getId());
                    gestorPrestamos.agregarPrestamoRechazado(p, razon.trim());

                    txtResumenAprobacion.setText("❌ Préstamo rechazado\nMotivo: " + razon);

                    gestorPrestamos.agregarOperacionAlHistorial("🚫 Rechazado préstamo ID " + p.getId()
                            + " para " + p.getCliente().getNombre() + ". Motivo: " + razon);

                    cargarSolicitudes();
                } else {
                    JOptionPane.showMessageDialog(this, "Debes ingresar un motivo para rechazar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                txtResumenAprobacion.setText("❌ No se pudo encontrar la solicitud.");
            }
        } else {
            txtResumenAprobacion.setText("⚠️ Selecciona una solicitud para rechazar.");
        }
    }

    private void cargarSolicitudes() {
        modeloLista.clear();
        for (Prestamo p : gestorPrestamos.getSolicitudesPendientes()) {
            modeloLista.addElement("ID: " + p.getId()
                    + " | Cliente: " + p.getCliente().getNombre()
                    + " | $" + p.getMonto()
                    + " | Cuotas: " + p.getCuotas());
        }
    }

    private int extraerIdDesdeTexto(String texto) {
        try {
            return Integer.parseInt(texto.split("\\|")[0].replace("ID:", "").trim());
        } catch (Exception e) {
            return -1;
        }
    }

    private Prestamo buscarSolicitudPorId(int id) {
        for (Prestamo p : gestorPrestamos.getSolicitudesPendientes()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}
