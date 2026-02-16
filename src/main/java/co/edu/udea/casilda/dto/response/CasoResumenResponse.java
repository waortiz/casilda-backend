package co.edu.udea.casilda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CasoResumenResponse {
    private String id;
    private String nombre;
    private String documento;
    private String fecha;
    private String dependencia;
    private String profesional;
    private String estado;
    private String tipoSolicitud;
    private String facultad;
    private String campus;
    private String genero;
    private Integer edad;
    private String celular;
    private String cargo;
    private String telefono;
    private String correoInst;
    private String correoPers;
    private String remitentePrimerNombre;
    private String remitenteSegundoNombre;
    private String remitentePrimerApellido;
    private String remitenteSegundoApellido;
    private String pacientePrimerNombre;
    private String pacienteSegundoNombre;
    private String pacientePrimerApellido;
    private String pacienteSegundoApellido;
    private String observaciones;
    private String prioridad;
    private String ultimaAccion;
}
