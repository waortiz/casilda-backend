package co.edu.udea.casilda.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ListaEditItemRequest {
    @NotBlank
    private String original;

    @NotBlank
    private String nuevo;
}
