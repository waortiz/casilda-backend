package co.edu.udea.casilda.dto.request;

import lombok.Data;

@Data
public class Pestana10Request {
    private Long citaId;
    private Long idAtencion;
    private CompromisosAtencionRequest compromisos;
}
