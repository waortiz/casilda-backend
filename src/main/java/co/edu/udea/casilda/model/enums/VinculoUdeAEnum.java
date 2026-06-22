package co.edu.udea.casilda.model.enums;

import lombok.Getter;

/**
 * Enumeración para vínculos con la UdeA
 */
@Getter
public enum VinculoUdeAEnum {
    ESTUDIANTE(1, "Estudiante"),
    PERSONAL_ADMINISTRATIVO(2, "Personal administrativo"),
    DOCENTE(3, "Docente"),
    EGRESADO(4, "Egresado"),
    PERSONA_EMPLEADA_ASEO_UDEA(5, "Persona empleada aseo UdeA"),
    CONTRATISTA_FUNDACION_UDEA(6, "Contratista Fundación UdeA"),
    CONTRATISTA_CIS(7, "Contratista CIS"),
    OTRAS(8, "Otras");

    private final Integer id;
    private final String nombre;

    VinculoUdeAEnum(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public static VinculoUdeAEnum fromId(Integer id) {
        for (VinculoUdeAEnum t : values()) {
            if (t.id.equals(id)) return t;
        }
        return null;
    }
}
