package run;

import modelos.Prestamo;

import java.time.LocalDate;

public class ComprobantePrestamo {

    public static String generar(Prestamo p) {
        StringBuilder sb = new StringBuilder();
        sb.append("====== COMPROBANTE DE PRÉSTAMO ======\n");
        sb.append("Fecha de aprobación: ").append(LocalDate.now()).append("\n");
        sb.append("ID Préstamo: ").append(p.getId()).append("\n");
        sb.append("ID Cliente: ").append(p.getIdCliente()).append("\n");
        sb.append("Destino: ").append(p.getDestino()).append("\n");
        sb.append("Monto solicitado: $").append(p.getMonto()).append("\n");

        if (p.esDiferido()) {
            sb.append("Tipo: Diferido\n");
            sb.append("Cuotas: ").append(p.getNumeroCuotas()).append("\n");
            sb.append("Interés aplicado: ").append(p.getInteres() * 100).append("%\n");
            sb.append("Total a pagar: $").append(String.format("%.2f", p.calcularTotalConInteres())).append("\n");
            sb.append("Valor por cuota: $").append(String.format("%.2f", p.calcularValorCuota())).append("\n");
        } else {
            sb.append("Tipo: Pago único\n");
            sb.append("Total a pagar: $").append(String.format("%.2f", p.calcularTotalConInteres())).append("\n");
        }

        sb.append("=====================================\n");

        return sb.toString();
    }
}
