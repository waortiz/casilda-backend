package co.edu.udea.casilda.model.enums;

import lombok.Getter;

/**
 * Enumeración para tipos de solicitud de acompañamiento
 */
@Getter
public enum TipoSolicitud {
    DIRECTA(1, "Solicitud Directa"),
    INDIRECTA(2, "Solicitud Indirecta");

    private final Integer id;
    private final String descripcion;

    TipoSolicitud(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el tipo de solicitud por su ID
     * @param id ID del tipo de solicitud
     * @return TipoSolicitud correspondiente
     * @throws IllegalArgumentException si el ID no es válido
     */
    public static TipoSolicitud fromId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID del tipo de solicitud no puede ser nulo");
        }
        for (TipoSolicitud tipo : values()) {
            if (tipo.id.equals(id)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de solicitud inválido: " + id);
    }

    /**
     * Verifica si el ID corresponde a una solicitud indirecta
     * @param id ID del tipo de solicitud
     * @return true si es indirecta, false en caso contrario
     */
    public static boolean esIndirecta(Integer id) {
        return INDIRECTA.id.equals(id);
    }
}
