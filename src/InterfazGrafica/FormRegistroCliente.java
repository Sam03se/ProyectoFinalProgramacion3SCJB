package InterfazGrafica;

import gestores.GestorClientes;
import modelos.Cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormRegistroCliente extends JFrame {
    private JPanel panelRegistro;
    private JTextField txtNombre;
    private JButton btnRegistrar;
    private JTextArea txtListaClientes;

    public FormRegistroCliente() {
        setTitle("Registrar Cliente");
        setContentPane(panelRegistro);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText().trim();
                if (!nombre.isEmpty()) {
                    Cliente nuevo = GestorClientes.crearCliente(nombre);
                    JOptionPane.showMessageDialog(FormRegistroCliente.this, "Cliente registrado con ID: " + nuevo.getId());
                    actualizarLista();
                    txtNombre.setText("");
                } else {
                    JOptionPane.showMessageDialog(FormRegistroCliente.this, "Ingrese un nombre válido.");
                }
            }
        });

        actualizarLista();
    }

    private void actualizarLista() {
        StringBuilder sb = new StringBuilder("Clientes registrados:\n");
        for (Cliente c : GestorClientes.obtenerClientes()) {
            sb.append("ID ").append(c.getId()).append(" - ").append(c.getNombre()).append("\n");
        }
        txtListaClientes.setText(sb.toString());
    }
}
