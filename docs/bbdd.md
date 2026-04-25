# bases de datos
1. Creamos la base de datos, para luego conectarla a java.
CREATE DATABASE IF NOT EXISTS refugio_mascotas;
USE refugio_mascotas;

-- 1. Tabla de Adoptantes
CREATE TABLE adoptantes (
    dni VARCHAR(12) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    email VARCHAR(100),
    curso_competencias BOOLEAN DEFAULT FALSE, -- Requisito Ley 2023
    contrato_firmado BOOLEAN DEFAULT FALSE
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
Añadimos perfiles de usuario a nuestra base de datos, teniendo en cuenta lo siguientes permisos (que más tarde implementaremos).

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL, -- Siempre encriptada en el futuro
    nombre VARCHAR(100),
    rol ENUM('VOLUNTARIO', 'VETERINARIO', 'DUENO') NOT NULL
);

ALTER TABLE animales 
ADD COLUMN estado ENUM('ACTIVO', 'ADOPTADO', 'FALLECIDO') DEFAULT 'ACTIVO',
ADD COLUMN causa_baja TEXT, 
ADD COLUMN fecha_baja DATE,
ADD COLUMN veterinario_id INT,
ADD FOREIGN KEY (veterinario_id) REFERENCES usuarios(id);

Acción,                 Voluntario,         Veterinario,          Dueño
Ver lista de animales,      ✅,                 ✅,                 ✅
Editar peso/nombre,         ❌,                 ✅,                 ✅
Vacunas / Chip / Castración,❌,                 ✅,                 ❌
Dar de baja (Defunción),    ❌,                 ✅,                 ✅
Gestionar usuarios,         ❌,                 ❌,                 ✅