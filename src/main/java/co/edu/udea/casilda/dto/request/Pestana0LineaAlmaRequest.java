package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Pestana0LineaAlmaRequest {
    private Long id;
    private Long idPersona;

    @NotNull(message = "El tipo de reporte es obligatorio")
    private Integer idTipoReporte;

    @NotNull(message = "El canal de contacto es obligatorio")
    private Integer idCanalContacto;

    private Integer idQuienRemite;

    @NotNull(message = "La fecha y hora de atención es obligatoria")
    private LocalDateTime fechaHoraAtencion;

    @NotNull(message = "La persona que atiende es obligatoria")
    private Long idPersonaAtiende;

    @NotNull(message = "El tipo de servicio es obligatorio")
    private Integer idTipoServicio;

    @NotNull(message = "La persona que registra es obligatoria")
    private Long idPersonaRegistra;
}
