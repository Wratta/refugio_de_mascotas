CREATE DATABASE IF NOT EXISTS refugio_mascotas;
USE refugio_mascotas;

-- 1. Tabla de Adoptantes (Primero esta, porque Animales depende de ella)(Con requisitos Ley 2023)
CREATE TABLE adoptantes (
    dni VARCHAR(9) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    direccion TEXT,
    telefono VARCHAR(15),
    email VARCHAR(100),
    curso_competencias BOOLEAN DEFAULT FALSE, -- Requisito Ley 2023
    contrato_firmado BOOLEAN DEFAULT FALSE
    CONSTRAINT check_dni_format CHECK (dni REGEXP '^[0-9]{8}[A-Z]$')
);

-- 2. Tabla de Animales
CREATE TABLE animales (
    id_animal INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    microchip VARCHAR(15) UNIQUE,
    especie ENUM('PERRO', 'GATO') NOT NULL,
    peso DECIMAL(5,2),
    fecha_nacimiento DATE,
    id_adoptante VARCHAR(12), -- Clave Foránea
    CONSTRAINT fk_adoptante FOREIGN KEY (id_adoptante) 
        REFERENCES adoptantes(dni) ON DELETE SET NULL
);

-- 3. Tabla de Vacunas (Para las alertas de Rabia)
CREATE TABLE vacunas (
    id_vacuna INT AUTO_INCREMENT PRIMARY KEY,
    id_animal INT,
    tipo_vacuna VARCHAR(50), -- Ej: 'Rabia', 'Polivalente'
    fecha_aplicacion DATE,
    proxima_dosis DATE,
    CONSTRAINT fk_animal_vacuna FOREIGN KEY (id_animal) 
        REFERENCES animales(id_animal) ON DELETE CASCADE
);

-- 4. Añadido usuarios de la app con sus roles (Para permisos)
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL, -- Siempre encriptada en el futuro
    nombre VARCHAR(100),
    rol ENUM('VOLUNTARIO', 'VETERINARIO', 'DUENO') NOT NULL
);
-- 5. Modificación de la Tabla animales con nuevos datos (Para decesos)
ALTER TABLE animales 
ADD COLUMN estado ENUM('ACTIVO', 'ADOPTADO', 'FALLECIDO') DEFAULT 'ACTIVO',
ADD COLUMN causa_baja TEXT, 
ADD COLUMN fecha_baja DATE,
ADD COLUMN veterinario_id INT,
ADD FOREIGN KEY (veterinario_id) REFERENCES usuarios(id);

-- 6. Primero creamos la tabla si no se ha creado
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nombre VARCHAR(100),
    rol ENUM('VOLUNTARIO', 'VETERINARIO', 'DUENO') NOT NULL
);

-- Insertamos un Veterinario
INSERT INTO usuarios (username, password, nombre, rol) 
VALUES ('pedro_vet', '1234', 'Dr. Pedro Sánchez', 'VETERINARIO');

-- Insertamos un Voluntario
INSERT INTO usuarios (username, password, nombre, rol) 
VALUES ('ana_vol', '1234', 'Ana García', 'VOLUNTARIO');

-- Insertamos al Dueño/Administrador
INSERT INTO usuarios (username, password, nombre, rol) 
VALUES ('admin', 'admin123', 'Dueño de la Protectora', 'DUENO');

-- 7. Añado los campos de salud para que el Veterinario tenga trabajo que hacer
ALTER TABLE animales 
ADD COLUMN chip_numero VARCHAR(50),
ADD COLUMN esterilizado BOOLEAN DEFAULT FALSE,
ADD COLUMN vacunas_al_dia BOOLEAN DEFAULT FALSE,
ADD COLUMN observaciones_veterinarias TEXT;

-- 8. Guardamos la query en Views, para temas legales.
SQL
CREATE VIEW reporte_bajas_legales AS
SELECT 
    a.id_animal AS animal_id,
    a.nombre AS mascota,
    a.causa_baja, 
    a.fecha_baja, 
    u.nombre AS veterinario_responsable
FROM animales a
JOIN usuarios u ON a.veterinario_id = u.id
WHERE a.estado = 'FALLECIDO';

-- 9. Guardamos otra query en Views, para temas legales. (reportes mensuales de bajas, también para temas estadísticos si hiciera falta)

DELIMITER //
CREATE PROCEDURE obtenerBajasPorMes(IN mes INT, IN anio INT)
BEGIN
    SELECT * FROM reporte_bajas_legales 
    WHERE MONTH(fecha_baja) = mes AND YEAR(fecha_baja) = anio;
END //
DELIMITER ;

--10. Añadimos datos ficticios para test posteriores
-- 1. Insertar un animal ACTIVO (Para comparar)
INSERT INTO animales (nombre, especie, raza, fecha_ingreso, estado, vacunas_al_dia) 
VALUES ('Rex', 'Perro', 'Pastor Alemán', '2026-01-10', 'ACTIVO', 1);

-- 2. Insertar una baja por enfermedad (Causa legal)
INSERT INTO animales (nombre, especie, raza, fecha_ingreso, estado, causa_baja, fecha_baja, veterinario_id) 
VALUES ('Thor', 'Perro', 'Boxer', '2025-05-20', 'FALLECIDO', 'Parvovirus severo - Fallo multiorgánico', '2026-04-15', 1);

-- 3. Insertar una baja por vejez
INSERT INTO animales (nombre, especie, raza, fecha_ingreso, estado, causa_baja, fecha_baja, veterinario_id) 
VALUES ('Luna', 'Gato', 'Común Europeo', '2020-02-10', 'FALLECIDO', 'Paro cardíaco por edad avanzada (18 años)', '2026-04-22', 1);

--11. Creamos la tabla de Veterinarios
CREATE TABLE veterinarios (
    id_vet INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20)
);

--Insertamos los datos ficticios que hemos creado en el XML y XSD
-- Insertar Personal Veterinario
INSERT INTO veterinarios (id_vet, nombre, telefono) VALUES 
(3, 'Dr. Javier López', '600111221'),
(7, 'Dra. Elena Martínez', '600111222');

-- Insertar Clientes / Adoptantes
INSERT INTO adoptantes (nombre, apellidos, dni, email, direccion, telefono, curso_competencias, contrato_firmado) 
VALUES ('Carlos', 'García Ruiz', '12345678Z', 'carlos.garcia@email.com', 'Calle Mayor 15, Madrid', '655999888', TRUE, TRUE);

-- Insertar Animales
INSERT INTO animales (
    id_animal, nombre, microchip, peso, esterilizado, 
    especie, raza, fecha_ingreso, 
    estado, causa_baja, fecha_baja, 
    vacunas_al_dia, veterinario_id
) VALUES 
(101, 'Luna', '985121000123456', 12.5, TRUE, 
 '2025-10-15', 'Perro', 'Border Collie', 
 '12345678Z', 'Adoptado', 'Proceso de adopción finalizado satisfactoriamente', '2026-03-20', 
 TRUE, 7),

(204, 'Bigotes', '985121000987654', 4.2, TRUE, 
 'Gato', 'Común Europeo', '2023-11-12', 
 NULL, 'Fallecido', 'Edad avanzada', '2026-04-01', 
 TRUE, 3);