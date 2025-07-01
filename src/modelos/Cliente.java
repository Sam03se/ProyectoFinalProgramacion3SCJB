package modelos;

public class Cliente {
    private int id;
    private String nombre;
    private String apellido;
    private String correo;
    private String cedula;
    private int edad;
    private String zona; // urbana o rural
    private double ingresoMensual;
    private int antiguedadLaboral; // en años

    // Constructor completo
    public Cliente(int id, String nombre, String apellido, String cedula, String correo, int edad, String zona, double ingresoMensual, int antiguedadLaboral) {
        if (id <= 0) throw new IllegalArgumentException("ID debe ser positivo.");
        if (nombre == null || nombre.isEmpty()) throw new IllegalArgumentException("Nombre no puede estar vacío.");
        if (apellido == null || apellido.isEmpty()) throw new IllegalArgumentException("Apellido no puede estar vacío.");
        if (!correo.contains("@")) throw new IllegalArgumentException("Correo inválido.");
        if (cedula == null || cedula.length() != 10) throw new IllegalArgumentException("Cédula inválida.");
        if (edad < 18 || edad > 100) throw new IllegalArgumentException("Edad no válida.");
        if (!zona.equalsIgnoreCase("urbana") && !zona.equalsIgnoreCase("rural")) throw new IllegalArgumentException("Zona inválida.");
        if (ingresoMensual < 0) throw new IllegalArgumentException("Ingreso mensual no puede ser negativo.");
        if (antiguedadLaboral < 0) throw new IllegalArgumentException("Antigüedad inválida.");

        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.correo = correo;
        this.edad = edad;
        this.zona = zona.toLowerCase();
        this.ingresoMensual = ingresoMensual;
        this.antiguedadLaboral = antiguedadLaboral;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getCedula() { return cedula; }
    public String getCorreo() { return correo; }
    public int getEdad() { return edad; }
    public String getZona() { return zona; }
    public double getIngresoMensual() { return ingresoMensual; }
    public int getAntiguedadLaboral() { return antiguedadLaboral; }

    // Setters con validación
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) throw new IllegalArgumentException("Nombre inválido.");
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.isEmpty()) throw new IllegalArgumentException("Apellido inválido.");
        this.apellido = apellido;
    }

    public void setCorreo(String correo) {
        if (correo == null || !correo.contains("@")) throw new IllegalArgumentException("Correo inválido.");
        this.correo = correo;
    }

    public void setEdad(int edad) {
        if (edad < 18 || edad > 100) throw new IllegalArgumentException("Edad no válida.");
        this.edad = edad;
    }

    public void setZona(String zona) {
        if (!zona.equalsIgnoreCase("urbana") && !zona.equalsIgnoreCase("rural")) throw new IllegalArgumentException("Zona inválida.");
        this.zona = zona.toLowerCase();
    }

    public void setIngresoMensual(double ingreso) {
        if (ingreso < 0) throw new IllegalArgumentException("Ingreso mensual inválido.");
        this.ingresoMensual = ingreso;
    }

    public void setAntiguedadLaboral(int antiguedad) {
        if (antiguedad < 0) throw new IllegalArgumentException("Antigüedad inválida.");
        this.antiguedadLaboral = antiguedad;
    }

    @Override
    public String toString() {
        return id + " - " + nombre + " " + apellido + " | " + correo;
    }
}
