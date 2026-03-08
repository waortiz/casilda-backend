package co.edu.udea.casilda.model.enums;

import lombok.Getter;

/**
 * Enumeración para sexo biológico
 */
@Getter
public enum SexoEnum {
    MUJER(1, "Mujer"),
    HOMBRE(2, "Hombre"),
    NO_BINARIO(3, "No binario"),
    PREFIERE_NO_RESPONDER(4, "Prefiere no responder");

    private final Integer id;
    private final String nombre;

    SexoEnum(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public static SexoEnum fromId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de sexo no puede ser nulo");
        }
        for (SexoEnum sexo : values()) {
            if (sexo.id.equals(id)) {
                return sexo;
            }
        }
        throw new IllegalArgumentException("Sexo inválido con ID: " + id);
    }
}
