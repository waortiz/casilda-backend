package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import lombok.Data;
import java.util.List;

/**
 * DTO para registrar una atención completa junto con actualizaciones a persona, caso y seguimientos.
 * Recibe los datos del formulario de registro de atención del componente registro-atencion
 */
@Data
public class RegistroAtencionCompleteRequest {
    
    // --- Datos de Atención ---
    @Valid
    @NotNull
    private AtencionRegistroRequest atencion;
    
    // --- Actualización de Persona ---
    @Valid
    @NotNull
    private PersonaAtencionRequest persona;
    
    // --- Actualización de Caso ---
    @Valid
    @NotNull
    private CasoAtencionRequest caso;
    
    // --- Seguimientos a registrar ---
    private List<SeguimientoAtencionRequest> seguimientos;
    
    // --- Compromisos (opcional, si no se persisten antes) ---
    private CompromisosAtencionRequest compromisos;
}
