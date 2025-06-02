package InterfazGrafica;

import gestores.GestorPrestamos;
import modelos.Prestamo;

import javax.swing.*;
import java.util.List;

public class FormCertificados extends JFrame {
    private JPanel panelCertificados;
    private JTextArea txtCertificados;

    public FormCertificados() {
        setTitle("Certificados de Préstamos Aprobados");
        setContentPane(panelCertificados);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        cargarCertificados();
    }

    private void cargarCertificados() {
        List<Prestamo> lista = GestorPrestamos.obtenerPrestamosAprobados();
        StringBuilder sb = new StringBuilder();

        if (lista.isEmpty()) {
            sb.append("No hay préstamos aprobados.");
        } else {
            for (Prestamo p : lista) {
                sb.append("""
                    ===============================
                    ID Préstamo: %d
                    Cliente ID: %d
                    Monto: $%.2f
                    Destino: %s
                    Interés: %.2f%%
                    Total a pagar: $%.2f
                    Cuotas: %d
                    Valor por cuota: $%.2f
                    ===============================

                    """.formatted(
                        p.getId(),
                        p.getIdCliente(),
                        p.getMonto(),
                        p.getDestino(),
                        p.getInteres() * 100,
                        p.calcularTotalConInteres(),
                        p.getNumeroCuotas(),
                        p.calcularValorCuota()
                ));
            }
        }

        txtCertificados.setEditable(false);
        txtCertificados.setText(sb.toString());
    }
}
