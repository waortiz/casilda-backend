package co.edu.udea.casilda.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class PestanaDatosComplementariosRequest {
    private Long idCaso;
    private Integer idvinculoudea;
    private String otrovinculo;
    private Integer idprograma;
    private Integer idunidadacademica;
    private Integer idunidadadministrativa;
    private Integer idcampus;
    private String observacionesTelefono;
    private String observacionesCorreo;
    private List<CorreoSolicitanteRequest> correos;
    private List<TelefonoSolicitanteRequest> telefonos;
}
