package co.edu.udea.casilda.model.enums;

import lombok.Getter;

/**
 * Enumeración para tipos de teléfono
 */
@Getter
public enum TipoTelefonoEnum {
    CELULAR(1, "Celular"),
    FIJO(2, "Teléfono Fijo"),
    WHATSAPP(3, "WhatsApp"),
    OFICINA(4, "Oficina");

    private final Integer id;
    private final String nombre;

    TipoTelefonoEnum(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public static TipoTelefonoEnum fromId(Integer id) {
        for (TipoTelefonoEnum t : values()) {
            if (t.id.equals(id)) return t;
        }
        return null;
    }
}
