# Diagrama Entidad-Relación (DER)
1. Creamos la base de datos, para luego conectarla a java.
CREATE DATABASE IF NOT EXISTS refugio_mascotas;
USE refugio_mascotas;

1. Lógica de Relaciones:

Adoptantes ↔ Animales: Relación 1:N (Identificadora débil). Un adoptante puede ser responsable de múltiples animales. La clave foránea id_adoptante en la tabla animales es opcional (NULL), permitiendo que el animal exista en el sistema antes de ser adoptado.

Animales ↔ Vacunas: Relación 1:N. Un animal posee un historial clínico único. La integridad se mantiene mediante ON DELETE CASCADE.

2. Validaciones Legales Integradas:

Integridad de Datos: El campo microchip está marcado como UNIQUE para evitar duplicidad de identidades animales.

Control de Aptitud: Los campos curso_competencias y contrato_firmado actúan como "flags" de control para los procesos de negocio en Java.

3. Tipos de Datos Críticos:

Uso de ENUM para especie para forzar la consistencia de datos.

Uso de BOOLEAN (TINYINT 1) para los requisitos legales.

Uso de DATE para el seguimiento temporal de vacunas y alertas de próximas dosis