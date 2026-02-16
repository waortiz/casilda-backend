package co.edu.udea.casilda.model.enums;

public enum EstadoSolicitud {
    PENDIENTE("Pendiente de revisión"),
    EN_PROCESO("En proceso de atención"),
    ASIGNADA("Asignada a dupla/profesional"),
    CONTACTO_REALIZADO("Contacto realizado"),
    CITA_PROGRAMADA("Cita programada"),
    EN_ATENCION("En atención"),
    COMPLETADA("Completada"),
    DESISTIDA("Desistida por la persona"),
    CERRADA("Cerrada");
    
    private final String descripcion;
    
    EstadoSolicitud(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}
