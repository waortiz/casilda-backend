package co.edu.udea.casilda.model.enums;

import lombok.Getter;

/**
 * Enumeración para estados de solicitud de atención
 */
@Getter
public enum EstadoSolicitud {
    SIN_ASIGNAR(1, "Sin asignar"),
    ASIGNADA(2, "Asignada");

    private final Integer id;
    private final String nombre;

    EstadoSolicitud(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Obtiene el estado de solicitud por su ID
     * @param id ID del estado
     * @return EstadoSolicitud correspondiente
     * @throws IllegalArgumentException si el ID no es válido
     */
    public static EstadoSolicitud fromId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID del estado de solicitud no puede ser nulo");
        }
        for (EstadoSolicitud estado : values()) {
            if (estado.id.equals(id)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Estado de solicitud inválido: " + id);
    }

    /**
     * Obtiene el estado de solicitud por su nombre
     * @param nombre Nombre del estado
     * @return EstadoSolicitud correspondiente
     * @throws IllegalArgumentException si el nombre no es válido
     */
    public static EstadoSolicitud fromNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del estado no puede ser nulo o vacío");
        }
        for (EstadoSolicitud estado : values()) {
            if (estado.nombre.equalsIgnoreCase(nombre.trim())) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Estado de solicitud inválido: " + nombre);
    }
}
