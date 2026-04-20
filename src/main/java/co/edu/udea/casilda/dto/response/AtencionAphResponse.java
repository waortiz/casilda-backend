package co.edu.udea.casilda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AtencionAphResponse {
    private Long id;
    private Integer idCanalAph;
    private String canalAph;
    private LocalDateTime fechaHora;
    private Integer idConvenioAph;
    private String convenioAph;
    private Integer idAmbitoAph;
    private String ambitoAph;
    private Integer idProtocoloAph;
    private String protocoloAph;
    private Boolean practicoTriage;
    private Integer idResultadoTriage;
    private String resultadoTriage;
    private String notaOMotivoTriage;
    private Boolean aceptaPsicologia;
    private Boolean requiereRemision;
}
