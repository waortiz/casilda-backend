package co.edu.udea.casilda.model.enums;

public enum TipoDocumento {
    CC("Cédula de Ciudadanía"),
    TI("Tarjeta de Identidad"),
    PASAPORTE("Pasaporte"),
    CE("Cédula de Extranjería"),
    OTRO("Otro");
    
    private final String descripcion;
    
    TipoDocumento(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}
