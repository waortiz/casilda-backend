package co.edu.udea.casilda.dto.request;

import lombok.Data;

@Data
public class Pestana1Request {
    private Long citaId;
    private Long idAtencion;
    private PersonaAtencionRequest persona;
    private Integer idRegimen;
    private Integer idEps;
}
