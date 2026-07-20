package co.edu.udea.casilda.model.enums;

import lombok.Getter;

/**
 * Enumeración para vínculos con la UdeA
 */
@Getter
public enum VinculoUdeAEnum {
    ESTUDIANTE_PREGRADO(1, "Estudiante de Pregrado"),
    ESTUDIANTE_DE_POSGRADO(2, "Estudiante de Posgrado"),
    EGRESADO_PREGRADO(3, "Egresado Pregrado"),
    EGRESADO_POSGRADO(4, "Egresado Posgrado"),
    PERSONAL_ADMINISTRATIVO(5, "Personal Administrativo"),
    DOCENTE_VINCULADO(6, "Docente Vinculado"),
    DOCENTE_OCASIONAL(7, "Docente Ocasional"),
    DOCENTE_DE_CATEDRA(8, "Docente de Cátedra"),
    CONTRATISTA(9, "Contratista"),
    OTRO_TIPO_DE_VINCULO(10, "Otro tipo de vínculo"),
    DOCENTE_CATEDRA_50(11, "Docente Cátedra 50"),
    JUBILADO_PENSIONADO(12, "Jubilado / Pensionado"),
    PRESTADOR_DE_SERVICIOS(13, "Prestador de Servicios"),
    EXTERNO(14, "Externo");

    private final Integer id;
    private final String nombre;

    VinculoUdeAEnum(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public static VinculoUdeAEnum fromId(Integer id) {
        for (VinculoUdeAEnum t : values()) {
            if (t.id.equals(id))
                return t;
        }
        return null;
    }
}
