package InterfazGrafica;

import gestores.GestorPrestamos;
import modelos.Prestamo;
import run.ComprobantePrestamo;

import javax.swing.*;
import java.util.List;

public class FormCertificados extends JFrame {
    private JLabel lblCliente;
    private JPanel panelCertificados;
    private JTextArea txtCertificados;

    public FormCertificados() {
        setTitle("Préstamos Aprobados");
        setContentPane(panelCertificados);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        mostrarCertificados();
    }

    private void mostrarCertificados() {
        List<Prestamo> lista = GestorPrestamos.obtenerPrestamosPrioridad();
        StringBuilder sb = new StringBuilder();

        if (lista.isEmpty()) {
            sb.append("No hay préstamos aprobados.");
        } else {
            for (Prestamo p : lista) {
                sb.append(ComprobantePrestamo.generar(p)).append("\n");
            }
        }

        txtCertificados.setText(sb.toString());
    }
}
