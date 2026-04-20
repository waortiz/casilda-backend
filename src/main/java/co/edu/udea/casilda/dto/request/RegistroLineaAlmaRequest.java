package co.edu.udea.casilda.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RegistroLineaAlmaRequest {

    @NotNull
    private Long idPersona;

    @NotNull
    private Integer idTipoReporte;

    @NotNull
    private Integer idCanalContacto;

    private String quienRemite;

    @NotNull
    private LocalDateTime fechaHoraAtencion;

    @NotNull
    private Long idPersonaAtiende;

    @NotNull
    private Integer idTipoServicio;

    @NotNull
    private Long idPersonaRegistra;

    private Integer idFormaEntrevista;

    @NotNull
    private Integer idIdentidadGenero;

    private Integer idOrientacionSexual;
    private Integer idEtnia;
    private Integer idCiudadResidencia;
    private String direccionResidencia;
    private Integer idVinculoUdeA;
    private Integer idSubVinculoUdeA;
    private Integer idFacultad;
    private Integer idPrograma;
    private Integer idDependencia;
    private Integer idCampus;

    @Valid
    private AtencionAphRequest atencionAph;

    @Valid
    private List<RemisionRegistroAlmaRequest> remisiones;
}
