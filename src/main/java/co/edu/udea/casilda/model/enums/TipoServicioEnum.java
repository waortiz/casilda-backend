package co.edu.udea.casilda.model.enums;

import lombok.Getter;

/**
 * Enumeración para tipos de servicio.
 */
@Getter
public enum TipoServicioEnum {
    PSICOLOGIA(1, "Psicología"),
    ASESORIA_JURIDICA(2, "Asesoría Jurídica"),
    TRABAJO_SOCIAL(3, "Trabajo Social"),
    DUPLA_PSICOSOCIAL(4, "Dupla Psicosocial"),
    ATENCION_APH(5, "Atención APH");

    private final Integer id;
    private final String nombre;

    TipoServicioEnum(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Obtiene el tipo de servicio por su ID.
     *
     * @param id ID del tipo de servicio
     * @return TipoServicioEnum correspondiente
     * @throws IllegalArgumentException si el ID no es válido
     */
    public static TipoServicioEnum fromId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID del tipo de servicio no puede ser nulo");
        }
        for (TipoServicioEnum tipo : values()) {
            if (tipo.id.equals(id)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de servicio inválido: " + id);
    }
}