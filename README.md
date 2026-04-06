# refugio_de_mascotas
1. Definición del Proyecto: "Refugio de Mascotas
   
● Qué aplicación voy a hacer

Un sistema híbrido compuesto por:

Web Pública (Landing Page): Donde los usuarios pueden ver el catálogo de animales disponibles, filtrar por especie/edad y leer historias de éxito de animales adoptados.

Panel de Gestión (ERP Interno): Una herramienta privada para que el personal de la protectora gestione la ficha clínica, el inventario de recursos (comida/medicinas) y el estado de las adopciones.

● Para qué sirve

Para centralizar toda la información que hoy en día muchas protectoras llevan en libretas o Excels separados. Permite llevar un control riguroso de la salud del animal y optimizar los recursos (comida y medicación) para que nunca falte nada.

● A quién va dirigida

Adoptantes: Personas que buscan un nuevo integrante para su familia.

Cuidadores: Para saber qué animal necesita comer qué y cuánto.

Veterinarios: Para registrar vacunas y tratamientos activos.

Administradores: Para gestionar altas, bajas y estadísticas de ocupación.

● Qué problema resuelve

Descontrol sanitario: Evita que un animal pierda una dosis de su tratamiento o vacuna.

Gestión de stock: Calcula automáticamente cuántos kilos de pienso se necesitan al mes según el peso y número de animales actuales.

Visibilidad: Ayuda a que los animales lleven menos tiempo en la protectora al tener una web atractiva que facilite la adopción.


2. Conexión con los Módulos (Tu defensa del proyecto)
🐾 Programación (Java / Web)

Aquí es donde aplicas lo que hemos visto:

Patrón Strategy: Para calcular la "Estimación de comida". No come lo mismo un cachorro de Mastín que un gato senior. Puedes tener diferentes estrategias de cálculo nutricional.

Estructura: Clases como Animal, Perro, Gato, Tratamiento, Adoptante.

🗄️ Bases de Datos (SQL)

Este es el corazón del proyecto. Necesitarás tablas normalizadas (3FN):

Animales (ID, nombre, fecha_entrada, peso, estado_adopcion).

Historial_Medico (ID_Animal, ID_Vacuna, fecha, observaciones).

Stock_Comida (Tipo_Pienso, cantidad_actual, alerta_minimo).

📈 Itinerario de Empleabilidad (Estadísticas y Futuro)

La "Estimación de entradas": usar una lógica de programación que analice los datos históricos (ej: en verano suelen entrar más animales) para prever gastos de comida y espacio.

3. ¿Cómo lo dividimos el proyecto?

MVP (Producto Mínimo Viable): Primero programra que se pueda añadir un animal y ver su ficha.

Segunda Iteración: Añadir la gestión de vacunas y tratamientos.

Tercera Iteración: Añadir el cálculo automático de comida (la parte de "gestión").

Final: Maquetar la web pública usando lo que ya sabes de HTML/CSS/Grid.

Para comenzar con el proyecto, empezamos planteando los requisitos iniciales

1. Requisitos Funcionales (Lo que la App "HACE")
Para que no te pierdas, vamos a dividirlo por los "actores" (perfiles de usuario) que mencionaste:

A. Perfil Público (Adoptante)

Visualización de catálogo: Ver fotos y fichas básicas de animales (nombre, edad aproximada, especie, sexo).

Filtros de búsqueda: Buscar por tamaño (pequeño/mediano/grande) o edad (cachorro/adulto/senior).

Formulario de interés: Enviar una solicitud de contacto para un animal específico.

B. Perfil Cuidador/Veterinario (Gestión Interna)

Gestión de Fichas: Alta, baja y modificación de animales (incluyendo peso y fecha de entrada).

Control Sanitario: Registro de vacunas puestas y calendario de próximas dosis.

Tratamientos activos: Listado de animales que necesitan medicación hoy (ej: "Tobby - 1 pastilla de desparasitante").

Cálculo de suministros: Pantalla que sume el peso de todos los perros y calcule los kg de pienso necesarios (ej: PesoTotal×0.02=KgDiarios).

C. Perfil Administrador

Gestión de Usuarios: Crear cuentas para nuevos voluntarios o veterinarios.

Estadísticas: Gráficas de "Animales adoptados vs. Animales en espera" y estimación de ocupación para los próximos meses.

2. Requisitos No Funcionales (Cómo es la App)
Persistencia: Los datos deben guardarse en una base de datos SQL (no se pueden perder si se apaga el PC).

Seguridad: El panel de gestión debe requerir usuario y contraseña. El adoptante solo ve la web pública.

Interfaz: Debe ser intuitiva (usabilidad) para que un voluntario que no sepa de informática pueda usarla rápido.

3. Diagrama de Casos de Uso (Simplificado)
Este diagrama es el que presentarás en tu documentación para explicar quién interactúa con qué.

Actor Adoptante → Consultar Animales, Enviar Solicitud.

Actor Veterinario → Gestionar Vacunas, Actualizar Peso, Registrar Tratamiento.

Actor Administrador → Generar Informes de Comida, Gestionar Personal.

4. Gestión de Datos: El primer "Boceto" de la BD
Para tu entrega de Bases de Datos, ya puedes ir pensando en estas entidades principales:

Tabla ANIMAL: (ID, Nombre, Especie, Raza, Peso, Fecha_Entrada, Estado).

Tabla TRATAMIENTO: (ID, Nombre_Medicina, Dosis, Frecuencia).

Tabla FICHA_MEDICA: (ID_Animal, ID_Tratamiento, Fecha_Inicio, Fecha_Fin).

Tabla ADOPCIONES: (ID_Animal, Nombre_Adoptante, Fecha_Adopcion, Telefono).

En España, con la entrada en vigor de la Ley 7/2023 de Protección de los Derechos y el Bienestar de los Animales (la famosa Ley de Bienestar Animal).

Actualización de los requisitos integrando la ley española:

1. Nuevos Requisitos Funcionales (Legales)

A. Gestión de Esterilización (Obligatorio por Ley)

La ley exige que todos los animales que se entreguen en adopción estén esterilizados (o con compromiso de ello si son cachorros).

Campo en BBDD: estado_esterilizacion (Booleano) y fecha_intervencion.

Alerta: El sistema no debe permitir marcar un animal como "Listo para adoptar" si no tiene check en esterilización.

B. Seguro de Responsabilidad Civil

Aunque el seguro obligatorio para todos los perros está en fase de desarrollo reglamentario, la ley lo exige.

Gestión: La ficha del animal debe incluir si el perro (especialmente si es de manejo especial o según su peso/raza) requiere que el adoptante presente el seguro de responsabilidad civil antes de la firma.

Documentación: Espacio para subir el PDF de la póliza asociada al microchip.

C. Identificación y Microchip

Obligatorio: Ningún animal puede salir sin identificación. El sistema debe validar que el campo num_microchip tiene el formato correcto (15 dígitos en España).

2. Actualización del Perfil de Usuario: El Adoptante

La ley introduce el concepto de "Curso de formación para la tenencia de perros".

Requisito de Perfil: En el formulario de adopción de la web, ahora debemos incluir un check o un campo para adjuntar el certificado del curso de formación (cuando sea plenamente vigente).

Validación: El sistema debe comprobar que el adoptante es mayor de edad y no tiene antecedentes por maltrato animal (declaración responsable).

3. Impacto en la Base de Datos (Tablas Legales)

Añadimos estos campos por imperativo legal:

Tabla	Campo Nuevo	Razón Legal
Animales	es_potencialmente_peligroso	Gestión de licencias específicas (aunque la ley nacional tienda a unificarlos, sigue habiendo registros autonómicos).
Animales	fecha_esterilizacion	Art. 26 de la Ley 7/2023 (Obligatorio).
Adopciones	dni_titular	Identificación en el Registro Nacional de Animales de Compañía.
Adopciones	contrato_adopcion_pdf	Documento legal obligatorio con cláusulas de no maltrato.

Empezamos a trabajar en Java, para programar las clases Animal y TipoAnimal, que son las bases de nuestro proyecto.

Hemos añadido la clase adoptante, el AnimalDao (que se encarga de gestionar la BBDD junto con la ConexionDB, que tiene el usuario y contraseña), y mejoramos la clase Main con un Menú.

En la clase Main, trabajo ahora en ver un listado de los animales en la BBDD.

En la siguiente fase, registraremos un Adoptante, y mejoraremos esta clase.
