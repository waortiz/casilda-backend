-- Insertar tipos de asignación
INSERT INTO tipoasignacion (id, nombre) VALUES
    (1, 'Prioritaria'),
    (2, 'Ordinaria'),
    (3, 'Seguimiento')
ON CONFLICT (id) DO NOTHING;

-- Insertar tipos de servicio
INSERT INTO tiposervicio (id, nombre) VALUES
    (1, 'Psicología'),
    (2, 'Asesoría Jurídica'),
    (3, 'Trabajo Social'),
    (4, 'Dupla Psicosocial')
ON CONFLICT (id) DO NOTHING;
