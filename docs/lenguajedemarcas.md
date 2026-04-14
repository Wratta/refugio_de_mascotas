# Lenguaje de marcas
Empezamos con lo básico un html, con un css para la web de usuario de la web. Que directamente va a ser la protectora. Otro usuario podría ser el veterinario.

Documentamos esta primera aproximación de la web.
Responsividad: Se ha utilizado grid-template-columns con un media query para asegurar que el formulario sea usable en dispositivos móviles (los voluntarios suelen usar tablets en el refugio).

Accesibilidad: Cada input está vinculado a un label y se han usado iconos de FontAwesome para facilitar la identificación visual rápida de los campos.

Validación de Identidad: El atributo pattern en el campo microchip garantiza la integridad de los datos antes de que lleguen a Java, reduciendo errores en la base de datos MySQL.

Añadimos un script en Javascript, para garantizar la Integridad de los datos.

preventDefault(): Esencial en aplicaciones modernas para evitar que la web parpadee o se recargue, permitiendo una experiencia de usuario más fluida.

Validación de Capas: Explicamos que validamos el microchip en el HTML (con pattern), en el JS (con length) y finalmente en el Controller/DAO (con try-catch). -Defensa en Profundidad-.

Conversión de Tipos: Esamos parseFloat() para el peso. Es vital asegurar que el dato sea numérico antes de que llegue al double de Animal.java.

El Flujo de Identificación

Para tu manual, es fundamental explicar cómo esta funcionalidad cumple con los requisitos técnicos de una aplicación multimedia de gestión:

Validación de Longitud: Se restringe la búsqueda a 15 caracteres exactos en el cliente para evitar consultas innecesarias a la base de datos MySQL que sabemos que fallarán.

Estado de la Interfaz: Si el usuario borra el contenido del buscador, la aplicación debe ser capaz de reestablecer la vista completa (llamando de nuevo al listado general), lo cual mejora la usabilidad.

Eficiencia: Al buscar por un campo con restricción UNIQUE (el microchip), MySQL localiza el registro de forma casi instantánea mediante índices B-Tree, sin tener que leer toda la tabla.

Guardado en Base de datos
Estamos usando una "base de datos temporal", es decir, no hay conexion real con MySQL, porque todavia no está implementada, asi que usamos un parche temporal para luego implementarlo
Persistencia Temporal: Ahora, cuando rellenas el formulario y pulsas "Guardar", verás que el animal aparece inmediatamente en la tabla de abajo.

Búsqueda Funcional: El botón "Buscar" ahora recorre el array censoAnimales. Si pones el microchip de un animal que acabas de registrar, la tabla se filtrará para mostrártelo.

Prevención de Duplicados: He añadido una validación extra. Si intentas registrar dos veces el mismo microchip, el sistema te avisará (emulando la restricción UNIQUE de tu base de datos MySQL).

"Gestión de Bajas en el Censo":

Identificador Único: El borrado se realiza basándose en el microchip. Al ser un campo UNIQUE en la base de datos, garantizamos que nunca borraremos al animal equivocado.

Confirmación de Usuario: Se implementa un paso de verificación (confirm) para prevenir eliminaciones accidentales, una práctica estándar en el diseño de interfaces de gestión de datos.

Refresco Dinámico: La tabla se vuelve a renderizar inmediatamente después de modificar el array, proporcionando una respuesta instantánea al usuario sin necesidad de recargar la página.