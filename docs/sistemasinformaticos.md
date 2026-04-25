# sistemas informáticos

1. Qué Sistema necesita (Stack Tecnológico)
Para que esta aplicación funcione, necesitas un stack de software conocido como WAMP (en desarrollo) o una variante de servidor de aplicaciones Java:

Entorno de Ejecución (Runtime): Java Runtime Environment (JRE) o Java Development Kit (JDK) 21. Es el motor que traduce tu código a lenguaje de máquina.

Servidor de Aplicaciones: Jetty 9.4. Es el contenedor de Servlets que gestiona las peticiones HTTP y mantiene las sesiones de usuario activas.

Gestor de Base de Datos: MySQL 8.0 (vía MariaDB en XAMPP). Necesita un motor relacional para asegurar la integridad de los datos de las mascotas y los logs de auditoría.

Conector: Driver JDBC (Java Database Connectivity) para que Java y MySQL hablen el mismo idioma.

2. Qué Sistema Operativo
La ventaja de Java es que es multiplataforma (Write Once, Run Anywhere), pero para el despliegue debemos definir uno:

En Desarrollo: Windows 10/11 (donde estás usando XAMPP e IntelliJ).

En Producción (Recomendado): Linux (Ubuntu Server 22.04 LTS). Es el estándar en sistemas informáticos por su estabilidad, seguridad y bajo consumo de recursos (no tiene interfaz gráfica, se gestiona por terminal).

En despliegue, para los usuarios, 
A. Sistemas Operativos del Cliente (Compatibilidad)

Dado que la interfaz es HTML/JS servida por Jetty, el sistema funcionará en prácticamente cualquier dispositivo moderno:

Escritorio: Windows (10/11), macOS, GNU/Linux (Ubuntu, Fedora, etc.).

Móviles/Tablets: Android e iOS (muy útil para que el veterinario use una tablet a pie de jaula).

B. Requisitos de Software del Usuario (El Navegador)

El único software que necesita el usuario final es un Navegador Web (Web Browser) moderno que soporte el estándar ES6 (para los fetch y la lógica que usamos).

Recomendados: Google Chrome, Mozilla Firefox, Microsoft Edge o Safari (versiones actualizadas).

Sin dependencias: El usuario no necesita tener Java instalado en su ordenador. Java solo corre en el servidor (Jetty). Esto reduce drásticamente los problemas de soporte técnico.


3. Qué Recursos necesita (Hardware/Specs)
Como es una aplicación de gestión para una protectora (no va a tener millones de visitas simultáneas), los requisitos son modestos:

Procesador (CPU): Mínimo 2 núcleos (vCPU). Java consume hilos para cada petición al Servlet.

Memoria RAM: Mínimo 2 GB.

Por qué: La Máquina Virtual de Java (JVM) reserva una parte (Heap Memory) para los objetos (Animales, Usuarios) y otra parte la consume la base de datos MySQL.

Almacenamiento: 10 GB de SSD. El código ocupa poco, pero la base de datos crecerá conforme se guarden fotos o historiales clínicos de los animales.

Red: Conexión estable a internet y apertura de los puertos 8080 (para Jetty) y 3306 (si la base de datos fuera externa).

4. Dónde va a correr la App (Entorno de Despliegue)

Opción A (Local): Un servidor físico en la propia oficina de la protectora (un PC dedicado conectado a la red local).

Opción B (Nube/Cloud): Un VPS (Virtual Private Server) en proveedores como AWS, Google Cloud o DigitalOcean