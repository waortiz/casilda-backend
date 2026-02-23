package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para capturar datos del solicitante
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatosSolicitanteRequest {

    @NotBlank(message = "El primer nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El primer nombre debe tener entre 2 y 100 caracteres")
    private String primerNombre;

    @Size(max = 100, message = "El segundo nombre no puede exceder 100 caracteres")
    private String segundoNombre;

    @NotBlank(message = "El primer apellido es obligatorio")
    @Size(min = 2, max = 100, message = "El primer apellido debe tener entre 2 y 100 caracteres")
    private String primerApellido;

    @Size(max = 100, message = "El segundo apellido no puede exceder 100 caracteres")
    private String segundoApellido;

    @NotNull(message = "El campus es obligatorio")
    private Integer campusId;

    @NotNull(message = "La dependencia es obligatoria")
    private Integer dependenciaId;

    @NotNull(message = "La facultad es obligatoria")
    private Integer facultadId;

    @NotNull(message = "El tipo de documento es obligatorio")
    private Integer tipoDocumentoId;

    @NotBlank(message = "El número de documento es obligatorio")
    @Pattern(regexp = "^[0-9]{6,15}$", message = "El número de documento debe contener entre 6 y 15 dígitos")
    private String numeroDocumento;

    @NotNull(message = "La identidad de género es obligatoria")
    private Integer identidadGeneroId;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 1, message = "La edad debe ser mayor a 0")
    @Max(value = 120, message = "La edad no puede ser mayor a 120")
    private Integer edad;

    @NotBlank(message = "El celular es obligatorio")
    @Pattern(regexp = "^[0-9]{10}$", message = "El celular debe tener 10 dígitos")
    private String celular;

    @NotBlank(message = "El celular alterno es obligatorio")
    @Pattern(regexp = "^[0-9]{10}$", message = "El celular alterno debe tener 10 dígitos")
    private String celularAlterno;

    @NotBlank(message = "El correo institucional es obligatorio")
    @Email(message = "El correo institucional debe ser válido")
    private String correoInstitucional;

    @NotBlank(message = "El correo personal es obligatorio")
    @Email(message = "El correo personal debe ser válido")
    private String correoPersonal;
}
