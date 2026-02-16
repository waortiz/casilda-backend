package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ListaItemRequest {
    @NotBlank
    private String valor;
}
