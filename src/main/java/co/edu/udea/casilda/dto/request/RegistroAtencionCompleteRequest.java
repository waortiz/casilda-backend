package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import lombok.Data;
import java.util.List;

/**
 * DTO para registrar una atención completa junto con datos de persona, caso, contexto y seguimientos.
 * Recibe los datos del formulario de registro de atención del componente registro-atencion
 */
@Data
public class RegistroAtencionCompleteRequest {
    
    // --- Datos de Atención (básicos) ---
    @Valid
    @NotNull
    private AtencionRegistroRequest atencion;
    
    // --- Contexto de Atención (dependencia, campus, programa, etc.) ---
    @Valid
    @NotNull
    private AtencionContextoRequest atencionContexto;
    
    // --- Actualización de Persona ---
    @Valid
    @NotNull
    private PersonaAtencionRequest persona;
    
    // --- Datos del Caso ---
    @Valid
    @NotNull
    private CasoAtencionRequest caso;
    
    // --- Seguimientos a registrar ---
    private List<SeguimientoAtencionRequest> seguimientos;

    // --- Hechos asociados al caso ---
    @Valid
    private List<HechoRequest> hechos;
    
    // --- Compromisos (opcional, si no se persisten antes) ---
    private CompromisosAtencionRequest compromisos;
}
