package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AtencionAphRequest {

    @NotNull
    private Integer idCanalAph;

    @NotNull
    private LocalDateTime fechaHora;

    @NotNull
    private Integer idConvenioAph;

    @NotNull
    private Integer idAmbitoAph;

    @NotNull
    private Integer idProtocoloAph;

    @NotNull
    private Boolean practicoTriage;

    private Integer idResultadoTriage;

    private String notaOMotivoTriage;

    @NotNull
    private Boolean aceptaPsicologia;

    @NotNull
    private Boolean requiereRemision;
}
