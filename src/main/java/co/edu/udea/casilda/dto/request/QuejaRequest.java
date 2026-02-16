package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QuejaRequest {
    @NotBlank
    private String perfil;

    @NotBlank
    private String descripcion;

    private Boolean aceptaPolitica;
    private String nombreVictima;
    private String apellidosVictima;
    private String correoVictima;
    private String generoVictima;
    private String cargoVictima;
    private String nombreVictimario;
    private String apellidosVictimario;
    private String deseaContacto;
    private String correoContacto;
    private String whatsappContacto;
}
