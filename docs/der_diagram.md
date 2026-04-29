# Diagrama Entidad-Relación (DER)

1. Entidades y Atributos

Animales: Es el núcleo del sistema.

id_animal (PK, Int, AI)

nombre (Varchar)

microchip (Varchar, Unique)

especie (Enum: PERRO, GATO)

estado (Enum: ACTIVO, ADOPTADO, FALLECIDO)

id_adoptante (FK, Varchar - apunta a Adoptantes)

veterinario_id (FK, Int - apunta a Veterinarios)

Adoptantes:

dni (PK, Varchar)

nombre (Varchar)

apellidos (Varchar)

telefono (Varchar)

Veterinarios:

id_vet (PK, Int, AI)

nombre (Varchar)

Usuarios: (Entidad independiente para el login)

id (PK)

username, password, rol

2. Relaciones (Cardinalidad)

Adoptantes - Animales (1:N): Un adoptante puede tener varios animales, pero cada animal (en un momento dado) solo pertenece a un adoptante. La clave dni viaja a la tabla animales como id_adoptante.

Veterinarios - Animales (1:N): Un veterinario puede firmar la defunción de muchos animales, pero cada registro de fallecimiento es validado por un único veterinario.


A. Creamos la base de datos, para luego conectarla a java.
CREATE DATABASE IF NOT EXISTS refugio_mascotas;
USE refugio_mascotas;

1. Lógica de Relaciones:

Adoptantes ↔ Animales: Relación 1:N (Identificadora débil). Un adoptante puede ser responsable de múltiples animales. La clave foránea id_adoptante en la tabla animales es opcional (NULL), permitiendo que el animal exista en el sistema antes de ser adoptado.

Animales ↔ Vacunas: Relación 1:N. Un animal posee un historial clínico único. La integridad se mantiene mediante ON DELETE CASCADE.

B. Validaciones Legales Integradas:

Integridad de Datos: El campo microchip está marcado como UNIQUE para evitar duplicidad de identidades animales.

Control de Aptitud: Los campos curso_competencias y contrato_firmado actúan como "flags" de control para los procesos de negocio en Java.

C. Tipos de Datos Críticos:

Uso de ENUM para especie para forzar la consistencia de datos.

Uso de BOOLEAN (TINYINT 1) para los requisitos legales.

Uso de DATE para el seguimiento temporal de vacunas y alertas de próximas dosis