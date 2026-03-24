package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO para crear un seguimiento de atención
 */
@Data
public class SeguimientoAtencionRequest {
    
    @NotNull
    private Long idAtencion;
    
    @NotNull
    private Integer idTipoSeguimiento;
    
    @NotNull
    private LocalDateTime fecha;
    
    @NotNull
    private Integer idAccion;
    
    @NotNull
    private Integer idActividad;
    
    @NotBlank
    private String descripcion;
    
    @NotNull
    private Integer idEstadoSeguimiento;
    
    @NotNull
    private Integer idMotivoEstadoSeguimiento;
    
    // --- Archivo del seguimiento (opcional) ---
    private String archivoNombre; // Nombre del archivo
    private String archivoTipo; // MIME type (ej: application/pdf)
    private String archivoContenido; // Base64 encoded file content
}
