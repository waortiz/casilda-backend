#!/usr/bin/env bash
set -euo pipefail

APP_NAME="casilda-backend"
APP_USER="casilda"
APP_GROUP="casilda"
INSTALL_DIR="/opt/${APP_NAME}"
CONFIG_DIR="/etc/${APP_NAME}"
LOG_DIR="/var/log/${APP_NAME}"
SERVICE_NAME="${APP_NAME}.service"

SERVER_NAME="${SERVER_NAME:-casilda.local}"
BACKEND_PORT="${BACKEND_PORT:-8080}"
CONTEXT_PATH="${CONTEXT_PATH:-/api/v1}"
APACHE_SITE_NAME="${APACHE_SITE_NAME:-${APP_NAME}.conf}"

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ENV_TEMPLATE="${SCRIPT_DIR}/casilda-backend.env.template"
SERVICE_TEMPLATE="${SCRIPT_DIR}/casilda-backend.service.template"
APACHE_TEMPLATE="${SCRIPT_DIR}/apache-casilda.conf.template"

require_root() {
  if [[ "${EUID}" -ne 0 ]]; then
    echo "Este instalador requiere permisos de root."
    echo "Ejecuta: sudo bash deploy/apache/install-backend-apache.sh"
    exit 1
  fi
}

detect_jar() {
  if [[ -n "${JAR_PATH:-}" ]]; then
    if [[ ! -f "${JAR_PATH}" ]]; then
      echo "No existe el JAR indicado en JAR_PATH: ${JAR_PATH}"
      exit 1
    fi
    echo "${JAR_PATH}"
    return
  fi

  if [[ -f "target/${APP_NAME}-1.0.0.jar" ]]; then
    echo "target/${APP_NAME}-1.0.0.jar"
    return
  fi

  local first_jar
  first_jar=$(find target -maxdepth 1 -type f -name '*.jar' ! -name '*original*.jar' | head -n 1 || true)
  if [[ -n "${first_jar}" ]]; then
    echo "${first_jar}"
    return
  fi

  echo "No se encontró JAR en target/."
  echo "Compila primero con: mvn clean package -DskipTests"
  exit 1
}

install_packages_debian() {
  export DEBIAN_FRONTEND=noninteractive
  apt-get update
  apt-get install -y apache2 openjdk-21-jre-headless
  a2enmod proxy proxy_http headers rewrite ssl
}

install_packages_rhel() {
  if command -v dnf >/dev/null 2>&1; then
    dnf install -y httpd java-21-openjdk-headless
  else
    yum install -y httpd java-21-openjdk-headless
  fi
}

create_user_group() {
  if ! getent group "${APP_GROUP}" >/dev/null; then
    groupadd --system "${APP_GROUP}"
  fi

  if ! id -u "${APP_USER}" >/dev/null 2>&1; then
    useradd --system --home "${INSTALL_DIR}" --shell /usr/sbin/nologin --gid "${APP_GROUP}" "${APP_USER}"
  fi
}

prepare_directories() {
  mkdir -p "${INSTALL_DIR}" "${CONFIG_DIR}" "${LOG_DIR}"
  chown -R "${APP_USER}:${APP_GROUP}" "${INSTALL_DIR}" "${LOG_DIR}"
}

install_env_file() {
  if [[ ! -f "${CONFIG_DIR}/${APP_NAME}.env" ]]; then
    cp "${ENV_TEMPLATE}" "${CONFIG_DIR}/${APP_NAME}.env"
  fi
  chown root:root "${CONFIG_DIR}/${APP_NAME}.env"
  chmod 600 "${CONFIG_DIR}/${APP_NAME}.env"
}

install_systemd_service() {
  sed \
    -e "s|__APP_NAME__|${APP_NAME}|g" \
    -e "s|__APP_USER__|${APP_USER}|g" \
    -e "s|__APP_GROUP__|${APP_GROUP}|g" \
    -e "s|__INSTALL_DIR__|${INSTALL_DIR}|g" \
    -e "s|__CONFIG_DIR__|${CONFIG_DIR}|g" \
    -e "s|__LOG_DIR__|${LOG_DIR}|g" \
    "${SERVICE_TEMPLATE}" > "/etc/systemd/system/${SERVICE_NAME}"

  systemctl daemon-reload
  systemctl enable "${SERVICE_NAME}"
}

install_jar() {
  local jar_path="$1"
  cp "${jar_path}" "${INSTALL_DIR}/${APP_NAME}.jar"
  chown "${APP_USER}:${APP_GROUP}" "${INSTALL_DIR}/${APP_NAME}.jar"
  chmod 750 "${INSTALL_DIR}/${APP_NAME}.jar"
}

install_apache_conf_debian() {
  sed \
    -e "s|__SERVER_NAME__|${SERVER_NAME}|g" \
    -e "s|__BACKEND_PORT__|${BACKEND_PORT}|g" \
    -e "s|__CONTEXT_PATH__|${CONTEXT_PATH}|g" \
    -e "s|__APP_NAME__|${APP_NAME}|g" \
    "${APACHE_TEMPLATE}" > "/etc/apache2/sites-available/${APACHE_SITE_NAME}"

  a2ensite "${APACHE_SITE_NAME}"
  systemctl enable apache2
  systemctl restart apache2
}

install_apache_conf_rhel() {
  sed \
    -e "s|__SERVER_NAME__|${SERVER_NAME}|g" \
    -e "s|__BACKEND_PORT__|${BACKEND_PORT}|g" \
    -e "s|__CONTEXT_PATH__|${CONTEXT_PATH}|g" \
    -e "s|__APP_NAME__|${APP_NAME}|g" \
    "${APACHE_TEMPLATE}" > "/etc/httpd/conf.d/${APACHE_SITE_NAME}"

  systemctl enable httpd
  systemctl restart httpd
}

start_backend() {
  systemctl restart "${SERVICE_NAME}"
  systemctl status "${SERVICE_NAME}" --no-pager -l || true
}

print_summary() {
  cat <<EOF

Instalación completada.

Servicio backend:
  systemctl status ${SERVICE_NAME}

Archivo de entorno:
  ${CONFIG_DIR}/${APP_NAME}.env

Backend local:
  http://127.0.0.1:${BACKEND_PORT}${CONTEXT_PATH}

Rutas detrás de Apache:
  http://${SERVER_NAME}${CONTEXT_PATH}
  http://${SERVER_NAME}${CONTEXT_PATH}/swagger-ui.html
  http://${SERVER_NAME}${CONTEXT_PATH}/api-docs

Si cambias variables del .env, reinicia:
  systemctl restart ${SERVICE_NAME}
EOF
}

main() {
  require_root

  if [[ ! -f "${ENV_TEMPLATE}" || ! -f "${SERVICE_TEMPLATE}" || ! -f "${APACHE_TEMPLATE}" ]]; then
    echo "Faltan plantillas en deploy/apache/."
    exit 1
  fi

  local jar_path
  jar_path="$(detect_jar)"
  echo "JAR detectado: ${jar_path}"

  if command -v apt-get >/dev/null 2>&1; then
    install_packages_debian
    create_user_group
    prepare_directories
    install_env_file
    install_jar "${jar_path}"
    install_systemd_service
    install_apache_conf_debian
  elif command -v dnf >/dev/null 2>&1 || command -v yum >/dev/null 2>&1; then
    install_packages_rhel
    create_user_group
    prepare_directories
    install_env_file
    install_jar "${jar_path}"
    install_systemd_service
    install_apache_conf_rhel
  else
    echo "Sistema no soportado automáticamente."
    echo "Soportado: Debian/Ubuntu (apt) y RHEL/Rocky/Alma (dnf/yum)."
    exit 1
  fi

  start_backend
  print_summary
}

main "$@"
