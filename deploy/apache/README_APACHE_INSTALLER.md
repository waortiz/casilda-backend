# Instalador Backend + Apache

Este instalador despliega el backend CASILDA como servicio `systemd` y lo publica detrás de Apache HTTP Server con `mod_proxy`.

## Qué instala

- Java 21 runtime
- Apache HTTP Server
- Servicio `casilda-backend.service`
- Archivo de entorno en `/etc/casilda-backend/casilda-backend.env`
- Configuración Apache para publicar `/api/v1`

## Requisitos

1. Servidor Linux con `systemd`
2. Usuario con privilegios `sudo`
3. Proyecto compilado (`target/*.jar`)

## Paso 1: Compilar

```bash
mvn clean package -DskipTests
```

## Paso 2: Ejecutar instalador

Desde la raíz del backend:

```bash
sudo bash deploy/apache/install-backend-apache.sh
```

Opcionalmente puedes definir variables antes de ejecutar:

```bash
export SERVER_NAME=api.casilda.midominio.com
export BACKEND_PORT=8080
export CONTEXT_PATH=/api/v1
sudo bash deploy/apache/install-backend-apache.sh
```

Si el jar no está en `target/casilda-backend-1.0.0.jar`, define:

```bash
export JAR_PATH=target/tu-jar-generado.jar
sudo bash deploy/apache/install-backend-apache.sh
```

## Verificación

```bash
systemctl status casilda-backend.service
curl http://127.0.0.1:8080/api/v1/maestros/sexos
curl http://<SERVER_NAME>/api/v1/maestros/sexos
```

Swagger:

- `http://<SERVER_NAME>/api/v1/swagger-ui.html`
- `http://<SERVER_NAME>/api/v1/api-docs`

## Post-instalación obligatoria

Editar credenciales en:

- `/etc/casilda-backend/casilda-backend.env`

Luego reiniciar backend:

```bash
sudo systemctl restart casilda-backend.service
```

## Actualizar versión del backend

1. Copiar nuevo JAR a `target/`
2. Re-ejecutar instalador o reemplazar `/opt/casilda-backend/casilda-backend.jar`
3. Reiniciar:

```bash
sudo systemctl restart casilda-backend.service
```
