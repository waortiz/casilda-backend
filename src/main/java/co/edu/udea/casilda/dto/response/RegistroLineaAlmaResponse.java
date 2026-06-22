package co.edu.udea.casilda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroLineaAlmaResponse {
    private Long id;
    private Long idPersona;
    private Integer idTipoReporte;
    private String tipoReporte;
    private Integer idCanalContacto;
    private String canalContacto;
    private String quienRemite;
    private LocalDateTime fechaHoraAtencion;
    private Long idPersonaAtiende;
    private Integer idTipoServicio;
    private String tipoServicio;
    private Long idPersonaRegistra;
    private Integer idLugarEntrevista;
    private String lugarEntrevista;
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
    private AtencionAphResponse atencionAph;
    private List<RemisionRegistroAlmaResponse> remisiones;
    private LocalDateTime fechaCreacion;
    private Long idUsuarioCreacion;
    private LocalDateTime fechaActualizacion;
    private Long idUsuarioActualizacion;
}
