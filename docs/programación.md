# programación
Estructura de Clases y Modelado (POJO)
Hemos definido objetos que representan la realidad de la protectora. El uso de POJOs permite encapsular los datos y transportarlos fácilmente entre capas.
Clase Animal, la principal. Almacena no solo datos básicos (nombre, especie), sino también el estado legal y sanitario exigido por la normativa.

Clase Usuario.java y Rol.java (Enum): Para definir los tipos de usuario sin usar simples textos, lo que da seguridad al código. Para implementar la seguridad. El Enum restringe los valores posibles del Rol (VETERINARIO, VOLUNTARIO, DUENO), evitando errores de escritura y facilitando comparaciones lógicas.
Clase Usuario: Para que el sistema reconozca a las personas que trabajan en la protectora.

Capa de Persistencia (Patrón DAO)
Separamos el código SQL del código de control para que el programa sea mantenible.
Clase UsuarioDAO: Para la lógica de "logueo" (el método validar).
Contienen los métodos CRUD.

UsuarioDAO.java. Este componente es crucial porque será el encargado de preguntarle a la base de datos: "¿Existe este usuario y su contraseña es correcta? Y si es así, ¿qué rol tiene?".
Creamos el LoginServlet.java para comprobar las credenciales de Usuario y le abre sesión.
Lógica de Sesión en los Servlets: Usar HttpSession para que el servidor recuerde quién eres y qué rol tienes.
Añadimos la clase BajaAnimalServlet para que un solo veterinario pueda dar de baja por deceso de un animal.

Empiezo con la depuración de fallos haciendo pruebas con el menú nuevo, recién actualizado.
Registro un animal, y me doy cuenta que al pedir Ver todos, no aparece.
No he tenido en cuenta los fallos al meter datos erróneos al registrar animal, corrijo.

Definimos los roles VET, VOL y DUENO (ADMIN) y diferentes opciones de menu según el rol. Se bloquean las opciones no disponibles para el Rol logueado.