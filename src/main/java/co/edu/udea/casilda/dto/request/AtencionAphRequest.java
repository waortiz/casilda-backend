package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AtencionAphRequest {

    @NotNull
    private LocalDateTime fechaHora;

    @NotNull
    private Integer idProtocoloAph;

    @NotNull
    private Boolean practicoTriage;

    private Integer idResultadoTriage;

    private String notaAph;

    private String motivoNoTriage;

    @NotNull
    private Boolean aceptaPsicologia;

    @NotNull
    private Boolean requiereRemision;
}
