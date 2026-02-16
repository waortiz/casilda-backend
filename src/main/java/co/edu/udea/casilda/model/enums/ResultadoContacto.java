package co.edu.udea.casilda.model.enums;

public enum ResultadoContacto {
    CONTESTA_Y_CONCRETA_CITA("Contesta y se concreta cita"),
    CONTESTA_Y_NO_CONCRETA_CITA("Contesta y no se concreta cita"),
    CONTESTA_Y_DESISTE("Contesta y desiste"),
    NO_CONTESTA("No contesta"),
    NUMERO_ERRADO("Número errado");
    
    private final String descripcion;
    
    ResultadoContacto(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}
