CREATE DATABASE inmobiliaria;
USE inmobiliaria;

--Tabla de Propiedades
CREATE TABLE propiedades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dimension FLOAT NOT NULL,
    precio DECIMAL(12, 2) NOT NULL,
    ubicacion VARCHAR(255) NOT NULL
);

--Tabla de Clientes
CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    telefono VARCHAR(20), 
    direccion VARCHAR(255)
);

--Tabla de Usuarios (Personal del sistema)
CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(150) UNIQUE NOT NULL, --Unique evita correos duplicados
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(50) NOT NULL
);

-- Tabla de Ventas (Con llaves foráneas)
CREATE TABLE venta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    precio_final DECIMAL(12, 2) NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    id_cliente INT,
    id_propiedad INT,
    id_usuario INT,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id),
    FOREIGN KEY (id_propiedad) REFERENCES propiedades(id),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);