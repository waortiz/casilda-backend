package co.edu.udea.casilda.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class PestanaPresuntoAgresorRequest {
    private List<AgresorVictimaRequest> agresores;
    private Long idCaso;
}
