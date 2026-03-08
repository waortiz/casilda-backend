package co.edu.udea.casilda.model.enums;

import lombok.Getter;

/**
 * Enumeración para tipos de correo electrónico
 */
@Getter
public enum TipoCorreoEnum {
    INSTITUCIONAL(1, "Correo Institucional"),
    PERSONAL(2, "Correo Personal");

    private final Integer id;
    private final String nombre;

    TipoCorreoEnum(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public static TipoCorreoEnum fromId(Integer id) {
        for (TipoCorreoEnum t : values()) {
            if (t.id.equals(id)) return t;
        }
        return null;
    }
}
