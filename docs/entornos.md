# Entornos de desarrollo
Campo,Tipo de Dato,Restricciones,Descripción
id_animal,INT,"PRIMARY KEY, AUTO_INCREMENT",Identificador único.
nombre,VARCHAR(50),NOT NULL,Nombre de la mascota.
fecha_ingreso,DATE,DEFAULT CURRENT_DATE,Cuándo llegó al refugio.
fk_especie,INT,FOREIGN KEY,Relación con la tabla Especies.

1. Diseño del Modelo de Datos (MySQL)

-> Entidades y Atributos Principales:

Especies: Para categorizar (Perro, Gato, otros).

Animales: La tabla central.

id_animal (PK), nombre, fecha_nacimiento, genero, estado (Disponible, Adoptado, En tratamiento), fk_especie.

Adoptantes: Información de contacto.

dni (PK), nombre, apellidos, telefono, direccion.

Adopciones: La tabla que une a las dos anteriores (Relación N:M simplificada).

id_adopcion (PK), fk_animal, fk_adoptante, fecha_adopcion, seguimiento (booleano).

A. Relación Adoptantes - Animales (1:N)

Cardinalidad: Un Adoptante puede tener cero o muchos Animales. Un Animal puede estar vinculado a cero o un Adoptante.

Participación: Es opcional por ambos lados inicialmente. Un animal puede estar en la protectora sin dueño (id_adoptante es NULL), y una persona puede estar registrada pero aún no haber adoptado.

Clave Foránea: El dni viaja a la tabla animales como id_adoptante.

B. Relación Animales - Vacunas (1:N)

Cardinalidad: Un Animal puede tener muchas Vacunas. Una Vacuna pertenece de forma unívoca a un solo Animal.

Participación: Total para la vacuna (no puede existir una vacuna en la base de datos sin un animal asociado).

Clave Foránea: El id_animal viaja a la tabla vacunas.

Profundización del Modelo E-R: El Factor Legal
El diseño debe garantizar que el software no solo guarde datos, sino que valide procesos legales. Aquí detallamos la lógica de cada tabla bajo el prisma de la Ley 2023:

1. Tabla adoptantes: El Filtro Legal

La ley exige que los adoptantes sean aptos. En tu base de datos, esto se traduce en campos de control:

curso_competencias (BOOLEAN): Documentamos que este campo es un requisito previo para la firma de la adopción.

contrato_firmado (BOOLEAN): Específicamente sobre la cláusula de castración (obligatoria para animales entregados en adopción si no tienen edad para ello en el momento de la entrega).

2. Tabla animales: Identificación Inequívoca

microchip (UNIQUE): Es el "DNI" del animal. La ley obliga a que todos estén identificados. En el modelo E-R, esto es una Candidate Key (clave candidata). Su restricción UNIQUE en SQL asegura que no existan duplicados.

id_adoptante: Permite la trazabilidad. La ley exige que la administración pueda saber quién es el responsable de un animal en todo momento.

3. Tabla vacunas: Salud Pública y Alertas

tipo_vacuna (Rabia, Polivalente): Diferenciamos entre obligatorias por ley y recomendadas.

proxima_dosis (DATE): Este campo es crítico para el desarrollo multimedia posterior. En Java o JS, calcularemos la diferencia entre la fecha actual y esta fecha para generar alertas visuales (colores rojos o notificaciones).

Representación Visual del Flujo de Datos

-Entrada: Se registra un Animal con su microchip.

-Mantenimiento: Se registran Vacunas asociadas a su id_animal.

-Salida (Adopción): Se verifica al Adoptante (curso y contrato). Si es OK, se actualiza el id_adoptante en la tabla animales.

Diccionario de Datos: Proyecto Protectora de Animales

Tabla: adoptantes

Nombre del Campo,Tipo de Dato,Nulo,Atributos,Descripción y Reglas de Negocio
dni,VARCHAR(12),No,PK,Documento de identidad (DNI/NIE/Pasaporte). Identificador único.
nombre,VARCHAR(50),No,-,Nombre de pila del adoptante.
apellidos,VARCHAR(100),No,-,Apellidos completos del adoptante.
telefono,VARCHAR(15),Sí,-,Formato internacional (ej: +34600000000).
email,VARCHAR(100),Sí,UNIQUE,Correo electrónico para notificaciones legales y seguimiento.
curso_competencias,BOOLEAN,No,DEFAULT 0,Ley 2023: Indica si ha superado el test de aptitud para la tenencia.
contrato_firmado,BOOLEAN,No,DEFAULT 0,Ley 2023: Compromiso de castración y cuidados básicos.

Tabla: animales

Nombre del Campo,Tipo de Dato,Nulo,Atributos,Descripción y Reglas de Negocio
id_animal,INT,No,"PK, AI",Identificador interno autonumérico.
nombre,VARCHAR(50),No,-,Nombre asignado al animal.
microchip,VARCHAR(15),No,UNIQUE,Ley 2023: Código único de 15 dígitos del transpondedor.
especie,ENUM,No,"('PERRO', 'GATO')",Restricción de dominio para control de tipos.
peso,"DECIMAL(5,2)",Sí,-,Peso en kg (ej: 12.50). Importante para dosificación médica.
fecha_nacimiento,DATE,Sí,-,Fecha estimada o real para cálculo de edad (cachorro/adulto).
id_adoptante,VARCHAR(12),Sí,FK,"Referencia al DNI del adoptante. Si es NULL, está en la protectora."

Tabla: vacunas

Nombre del Campo,Tipo de Dato,Nulo,Atributos,Descripción y Reglas de Negocio
id_vacuna,INT,No,"PK, AI",Identificador único del registro de vacunación.
id_animal,INT,No,FK,Vinculación directa con el animal. ON DELETE CASCADE.
tipo_vacuna,VARCHAR(50),No,-,"Ej: 'Rabia', 'Polivalente', 'Leucemia'."
fecha_aplicacion,DATE,No,-,Fecha en la que se administró la dosis.
proxima_dosis,DATE,Sí,-,Alerta: Fecha de vencimiento para la siguiente revacunación.

Restricciones de Integridad (Constraints)

Restricción de Microchip: animales.microchip debe ser UNIQUE. No pueden existir dos animales con el mismo ID legal.

Restricción de Edad: Aunque en MySQL el campo es DATE, en la documentación debemos especificar que fecha_nacimiento <= CURRENT_DATE.

Integridad de Borrado: * Si borramos una Vacuna, no pasa nada.

Si borramos un Animal, deben borrarse sus vacunas (ON DELETE CASCADE).

Si borramos un Adoptante, el animal queda huérfano (ON DELETE SET NULL), no se borra.

Estructura de Proyecto

PROYECTO_PROTECTORA/
├── src/
│   ├── model/         
│   ├── dao/           
│   └── controller/         
└── docs/
    └── diccionario_datos.md
    ├── der_diagram.md
    └── script_creacion.sql

Integridad Referencial

Protección de Datos: Se impide la eliminación de registros "huérfanos". Si un animal está vinculado a un adoptante, borrarlo causaría un error de consistencia en el historial de la protectora.

Lógica de Negocio: El sistema diferencia entre "Baja por error de entrada" (permitida) y "Baja administrativa" (restringida si hay contratos activos).

Simulación de SQL: Este comportamiento imita la cláusula ON DELETE RESTRICT que definiríamos en una base de datos MySQL profesional.

Modelo de Datos 

Al registrar un animal, el sistema sigue ahora este flujo lógico:

Instanciación: El objeto nace con idAdoptante: null.

Renderizado: La función actualizarTabla detecta el valor nulo y asigna la clase status-available.

Restricción: El botón "Eliminar" comprueba que el valor sigue siendo nulo antes de permitir el borrado.

Actualización: Al pulsar "Adoptar", el valor deja de ser nulo, lo que cambia automáticamente la interfaz (aparece el ID del adoptante, cambia el color a azul y se bloquea la eliminación).