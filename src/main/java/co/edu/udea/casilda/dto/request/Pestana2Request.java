package co.edu.udea.casilda.dto.request;

import lombok.Data;

@Data
public class Pestana2Request {
    private Long citaId;
    private Long idAtencion;
    private AtencionContextoRequest atencionContexto;
    private String observacionesTelefono;
    private String observacionesCorreo;
}
