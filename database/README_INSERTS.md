# Scripts de Inserción de Datos Maestros - Sistema CASILDA

Este directorio contiene los scripts SQL para poblar las tablas maestras del Sistema CASILDA - FNSP.

## 📋 Archivos Disponibles

### 1. `er_model_schema.sql`
Esquema de la base de datos con todas las tablas necesarias. **Ejecutar primero**.

### 2. `insert_datos_maestros.sql`
Contiene los datos básicos para las siguientes tablas:
- Tipos de correo
- Sexos
- Tipos de identificación
- Etnias
- Identidades de género
- Orientaciones sexuales
- Departamentos y Municipios
- Tipos de discapacidad
- Tipos de teléfono
- Campus universitarios
- Dependencias
- Facultades, Escuelas e Institutos
- Vínculos agresor-víctima
- Vínculos con UdeA
- Modalidades de violencia
- Modalidades de violencia sexual
- Tipos de solicitud
- Cargos
- Jornadas
- Resultados de contacto telefónico
- Régimen de salud
- EPS
- Roles de usuario

### 3. `insert_programas_paises.sql`
Contiene:
- **124 programas académicos** de la Universidad de Antioquia
- **240 países** con códigos ISO 3166-1 alfa-3

### 4. `insert_usuarios_prueba.sql`
Contiene **13 usuarios de prueba** distribuidos en los 5 roles del sistema:
- 2 Administradores
- 2 Coordinadores
- 6 Profesionales (Duplas psicojurídicas y otros)
- 2 Revisores
- 3 Usuarios regulares

**Contraseña por defecto:** `Casilda2024!`

### 5. `ejecutar_inserts.sql`
Script maestro que ejecuta todos los scripts anteriores en el orden correcto.

## 🚀 Instrucciones de Uso

### Opción 1: Ejecución Manual (Paso a paso)

```bash
# 1. Crear el esquema de la base de datos
psql -U postgres -d casilda_db -f er_model_schema.sql

# 2. Insertar datos maestros básicos
psql -U postgres -d casilda_db -f insert_datos_maestros.sql

# 3. Insertar programas y países
psql -U postgres -d casilda_db -f insert_programas_paises.sql

# 4. Insertar usuarios de prueba
psql -U postgres -d casilda_db -f insert_usuarios_prueba.sql
```

### Opción 2: Ejecución Automática (Todo en uno)

```bash
# Ejecutar el script maestro
psql -U postgres -d casilda_db -f ejecutar_inserts.sql
```

### Opción 3: Desde pgAdmin o DBeaver

1. Conectarse a la base de datos `casilda_db`
2. Abrir y ejecutar `er_model_schema.sql`
3. Abrir y ejecutar `ejecutar_inserts.sql`

## 👥 Usuarios de Prueba

| Email | Rol | Nombre |
|-------|-----|--------|
| admin@udea.edu.co | ADMIN | Laura Estella Pineda Corcho |
| admin2@udea.edu.co | ADMIN | Isabel Cristina Jaramillo González |
| coordinador.atencion@udea.edu.co | COORDINADOR | Laura Estella Pineda Corcho |
| coordinador.prevencion@udea.edu.co | COORDINADOR | Isabel Cristina Jaramillo González |
| andrea.salazar@udea.edu.co | PROFESIONAL | Andrea Salazar Morales |
| carmen.sanchez@udea.edu.co | PROFESIONAL | Carmen Andrea Sánchez Hernández |
| diana.sanchez@udea.edu.co | PROFESIONAL | Diana Cristina Sánchez Pérez |
| manuela.morales@udea.edu.co | PROFESIONAL | Manuela Morales Duque |
| laura.valencia@udea.edu.co | PROFESIONAL | Laura Valencia Ruíz |
| lina.rodas@udea.edu.co | PROFESIONAL | Lina María Rodas |
| revisor1@udea.edu.co | REVISOR | Revisor Administrativo Uno |
| revisor2@udea.edu.co | REVISOR | Revisor Administrativo Dos |
| usuario.estudiante@udea.edu.co | USUARIO | María Camila Gómez Pérez |

**Todos los usuarios tienen la misma contraseña:** `Casilda2024!`

## 🔧 Configuración de Contraseñas

Las contraseñas están hasheadas con BCrypt. Para generar nuevos hashes:

### Node.js
```javascript
const bcrypt = require('bcryptjs');
const hash = bcrypt.hashSync('TuContraseña123', 10);
console.log(hash);
```

### Java (Spring Security)
```java
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
String hash = encoder.encode("TuContraseña123");
System.out.println(hash);
```

### Online
También puedes usar: https://bcrypt-generator.com/ con 10 rounds

## 📊 Resumen de Datos Insertados

- **Catálogos básicos:** 23 tablas
- **Programas académicos:** 124 registros
- **Países:** 240 registros
- **Usuarios de prueba:** 13 registros
- **Total aproximado:** ~600 registros

## 🔒 Seguridad

⚠️ **IMPORTANTE:** 
- Los usuarios de prueba son solo para desarrollo
- NUNCA usar estos usuarios en producción
- Cambiar todas las contraseñas antes de desplegar en producción
- Eliminar o desactivar usuarios de prueba en producción

## 📝 Notas Adicionales

1. Los datos fueron extraídos del archivo `EV-I-02-25 D1.xlsm` proporcionado
2. Los códigos ISO de países siguen el estándar ISO 3166-1 alfa-3
3. Los programas académicos corresponden a la oferta de la Universidad de Antioquia
4. Las modalidades de violencia están basadas en la normativa colombiana y protocolos internos de la UdeA
5. Los municipios incluidos son principalmente de Antioquia (124 municipios)

## 🆘 Solución de Problemas

### Error: "relation does not exist"
- Ejecutar primero `er_model_schema.sql`

### Error: "duplicate key value violates unique constraint"
- La base de datos ya tiene datos. Limpiar las tablas o usar una base de datos nueva:
```sql
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
```

### Error: "column does not exist"
- Verificar que el esquema esté actualizado con la versión corregida (referencia a `profesional.idpersona`)

## 📧 Contacto

Para preguntas o problemas con los scripts, contactar al equipo de desarrollo de CASILDA-FNSP.

---
**Última actualización:** Febrero 2026
**Versión de Scripts:** 1.0
