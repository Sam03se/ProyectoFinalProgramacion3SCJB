package run;

import modelos.Prestamo;
import modelos.Cliente;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ComprobantePrestamo {

    public static String generarTexto(Prestamo p) {
        Cliente c = p.getCliente();

        StringBuilder sb = new StringBuilder();
        sb.append("====== COMPROBANTE DE PRÉSTAMO ======\n");
        sb.append("Fecha de aprobación: ").append(LocalDate.now()).append("\n");
        sb.append("ID Préstamo: ").append(p.getId()).append("\n");
        sb.append("Cliente: ").append(c.getNombre()).append(" ").append(c.getApellido()).append("\n");
        sb.append("Zona: ").append(c.getZona()).append(" | Edad: ").append(c.getEdad()).append("\n");
        sb.append("Destino: ").append(p.getDestino()).append("\n");
        sb.append("Monto solicitado: $").append(String.format("%.2f", p.getMonto())).append("\n");

        if (p.getCuotas() > 1) {
            sb.append("Tipo: Cuotas mensuales\n");
            sb.append("Cuotas: ").append(p.getCuotas()).append("\n");
            sb.append("Interés aplicado: ").append(String.format("%.2f", p.getInteres() * 100)).append("%\n");
            sb.append("Valor por cuota: $").append(String.format("%.2f", p.calcularValorCuotaAvanzada())).append("\n");
            sb.append("Total a pagar: $").append(String.format("%.2f", p.calcularTotalConInteres())).append("\n");
        } else {
            sb.append("Tipo: Pago único\n");
            sb.append("Interés aplicado: ").append(String.format("%.2f", p.getInteres() * 100)).append("%\n");
            sb.append("Total a pagar: $").append(String.format("%.2f", p.calcularTotalConInteres())).append("\n");
        }

        sb.append("=====================================\n");

        return sb.toString();
    }

    public static void guardarComoTxt(Prestamo p) {
        String texto = generarTexto(p);

        // Ruta: ./comprobantes/comprobante_{clienteId}_{fecha}.txt
        String nombreArchivo = "comprobante_" + p.getCliente().getId() + "_" +
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".txt";

        try {
            java.nio.file.Files.createDirectories(java.nio.file.Path.of("comprobantes")); // Crear carpeta si no existe
            FileWriter writer = new FileWriter("comprobantes/" + nombreArchivo);
            writer.write(texto);
            writer.close();
            System.out.println("✅ Comprobante generado: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("❌ Error al guardar comprobante: " + e.getMessage());
        }
    }
}
