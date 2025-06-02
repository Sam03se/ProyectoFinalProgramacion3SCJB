package run;

import InterfazGrafica.FormMenuPrincipal;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Configura estilo visual de Java Swing (opcional)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            FormMenuPrincipal ventana = new FormMenuPrincipal();
            ventana.setVisible(true);
        });
    }
}
