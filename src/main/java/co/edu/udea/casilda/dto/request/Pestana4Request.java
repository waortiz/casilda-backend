package co.edu.udea.casilda.dto.request;

import lombok.Data;

@Data
public class Pestana4Request {
    private Long citaId;
    private Long idAtencion;
    private CasoAtencionRequest caso;
}
