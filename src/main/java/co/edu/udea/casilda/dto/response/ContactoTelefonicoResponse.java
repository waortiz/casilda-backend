package co.edu.udea.casilda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactoTelefonicoResponse {
    private String fecha;
    private String hora;
    private String jornada;
    private String resultado;
    private String observacion;
}
