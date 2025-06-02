package InterfazGrafica;

import gestores.GestorPrestamos;
import modelos.Prestamo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormAprobacionManual extends JPanel {
    private JList<String> listaSolicitudes;
    private JButton btnAprobarSeleccionado;
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

        txtResumenAprobacion = new JTextArea();
        txtResumenAprobacion.setEditable(false);
        JScrollPane scrollResumen = new JScrollPane(txtResumenAprobacion);
        scrollResumen.setBounds(20, 240, 400, 120);
        add(scrollResumen);

        cargarSolicitudes();

        btnAprobarSeleccionado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = listaSolicitudes.getSelectedIndex();
                if (index >= 0) {
                    String selectedValue = modeloLista.getElementAt(index);
                    int id = extraerIdDesdeTexto(selectedValue);
                    if (gestorPrestamos.aprobarPrestamoPorId(id)) {
                        txtResumenAprobacion.setText("✅ Préstamo aprobado correctamente.\n" + selectedValue);
                        cargarSolicitudes();
                    } else {
                        txtResumenAprobacion.setText("❌ No se pudo aprobar el préstamo.");
                    }
                } else {
                    txtResumenAprobacion.setText("⚠️ Selecciona una solicitud para aprobar.");
                }
            }
        });
    }

    private void cargarSolicitudes() {
        modeloLista.clear();
        for (Prestamo p : gestorPrestamos.obtenerPrestamosPendientes()) {
            modeloLista.addElement("ID: " + p.getId() + " | Cliente: " + p.getIdCliente() + " | $" + p.getMonto() + " | Cuotas: " + p.getCuotas());
        }
    }

    private int extraerIdDesdeTexto(String texto) {
        try {
            return Integer.parseInt(texto.split("\\|")[0].replace("ID:", "").trim());
        } catch (Exception e) {
            return -1;
        }
    }
}
