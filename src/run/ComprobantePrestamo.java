package run;

import modelos.Prestamo;
import modelos.Cliente;

import java.time.LocalDate;

public class ComprobantePrestamo {

    public static String generar(Prestamo p) {
        Cliente c = p.getCliente();

        StringBuilder sb = new StringBuilder();
        sb.append("====== COMPROBANTE DE PRÉSTAMO ======\n");
        sb.append("Fecha de aprobación: ").append(LocalDate.now()).append("\n");
        sb.append("ID Préstamo: ").append(p.getId()).append("\n");
        sb.append("ID Cliente: ").append(c.getId()).append("\n");
        sb.append("Nombre: ").append(c.getNombre()).append(" ").append(c.getApellido()).append("\n");
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
}
