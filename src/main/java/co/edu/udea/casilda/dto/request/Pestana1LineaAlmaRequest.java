package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Pestana1LineaAlmaRequest {
    @NotNull(message = "El ID del registro es obligatorio")
    private Long id;
    
    private Long idPersona;

    @NotBlank(message = "El primer nombre es obligatorio")
    private String primerNombre;
    
    private String segundoNombre;

    @NotBlank(message = "El primer apellido es obligatorio")
    private String primerApellido;
    
    private String segundoApellido;

    @NotBlank(message = "El número de documento es obligatorio")
    private String numeroDocumento;

    @NotNull(message = "El tipo de identificación es obligatorio")
    private Integer idTipoIdentificacion;

    @NotNull(message = "La ciudad de nacimiento es obligatoria")
    private Integer idCiudadNacimiento;

    private LocalDateTime fechaNacimiento;

    @NotNull(message = "La identidad de género es obligatoria")
    private Integer idIdentidadGenero;

    private Integer idOrientacionSexual;
    private Integer idEtnia;
    private Integer idCiudadResidencia;
    private String direccionResidencia;
}
