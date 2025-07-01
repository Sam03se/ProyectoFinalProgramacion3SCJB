package run;

import InterfazGrafica.FormMenuPrincipal;
import gestores.GestorClientes;
import gestores.GestorPrestamos;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Establecer el estilo visual del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Crear los gestores
        GestorClientes gestorClientes = new GestorClientes();
        GestorPrestamos gestorPrestamos = new GestorPrestamos();

        // Lanzar la ventana principal
        SwingUtilities.invokeLater(() -> {
            FormMenuPrincipal ventana = new FormMenuPrincipal(gestorClientes, gestorPrestamos);
            ventana.setVisible(true);
        });
    }
}
