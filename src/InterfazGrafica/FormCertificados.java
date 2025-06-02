package InterfazGrafica;

import gestores.GestorClientes;
import gestores.GestorPrestamos;
import modelos.Prestamo;

import javax.swing.*;
import java.util.List;

public class FormCertificados extends JFrame {
    private JPanel panelPrincipal;
    private JTextArea txtCertificados;

    private final GestorPrestamos gestorPrestamos;
    private final GestorClientes gestorClientes;

    public FormCertificados(GestorPrestamos gestorPrestamos, GestorClientes gestorClientes) {
        this.gestorPrestamos = gestorPrestamos;
        this.gestorClientes = gestorClientes;

        setContentPane(panelPrincipal);
        setTitle("Certificados de Préstamos Aprobados");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        mostrarCertificados();
    }

    private void mostrarCertificados() {
        StringBuilder sb = new StringBuilder("📄 Certificados de préstamos:\n");
        List<Prestamo> lista = gestorPrestamos.obtenerPrestamosAprobados();
        for (Prestamo p : lista) {
            String nombre = gestorClientes.buscarClientePorId(p.getIdCliente()).getNombre();
            sb.append("Cliente: ").append(nombre)
                    .append(" | Monto: $").append(p.getMonto())
                    .append(" | Cuotas: ").append(p.getCuotas()).append("\n");
        }
        txtCertificados.setText(sb.toString());
    }
}
