package co.edu.udea.casilda.dto.request;

import lombok.Data;

import java.util.List;

/**
 * DTO con los compromisos asociados al registro de atencion.
 */
@Data
public class CompromisosAtencionRequest {

    private List<CompromisoPersonaAtendidaRequest> persona;
    private List<CompromisoProfesionalRequest> profesional;
}