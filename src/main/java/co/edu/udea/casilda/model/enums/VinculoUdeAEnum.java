package co.edu.udea.casilda.model.enums;

import lombok.Getter;

/**
 * Enumeración para vínculos con la UdeA
 */
@Getter
public enum VinculoUdeAEnum {
    ESTUDIANTE_PREGRADO(1, "Estudiante de Pregrado"),
    PERSONAL_ADMINISTRATIVO(2, "Personal Administrativo"),
    DOCENTE_VINCULADO(3, "Docente Vinculado"),
    EGRESADO(4, "Egresado"),
    DOCENTE_OCASIONAL(5, "Docente Ocasional"),
    DOCENTE_DE_CATEDRA(6, "Docente de Cátedra"),
    CONTRATISTA(7, "Contratista"),
    OTRO_TIPO_DE_VINCULO(8, "Otro tipo de vínculo"),
    ESTUDIANTE_DE_TECNOLOGIA(9, "Estudiante de Tecnología"),
    ESTUDIANTE_DE_POSGRADO(10, "Estudiante de Posgrado"),
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
            if (t.id.equals(id)) return t;
        }
        return null;
    }
}
