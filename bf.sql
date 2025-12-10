-- Crear base de datos
CREATE DATABASE IF NOT EXISTS inventario_equipos;
USE inventario_equipos;

-- Crear tabla equipos
CREATE TABLE equipos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    tipo_equipo VARCHAR(100) NOT NULL,
    marca VARCHAR(100) NOT NULL,
    modelo VARCHAR(150) NOT NULL,
    sistema VARCHAR(100) NOT NULL,
    almacenamiento VARCHAR(50) NOT NULL,
    ram VARCHAR(50) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    fecha_mantenimiento DATE NOT NULL,
    fecha_registro DATE NOT NULL,
    imagen VARCHAR(255)
);

USE inventario_equipos;

CREATE OR REPLACE VIEW vista_equipos AS
SELECT
    id,
    codigo,
    tipo_equipo,
    marca,
    modelo,
    sistema,
    almacenamiento,
    ram,
    estado,
    DATE_FORMAT(fecha_mantenimiento, '%Y-%m-%d') AS fecha_mantenimiento,
    DATE_FORMAT(fecha_registro, '%Y-%m-%d') AS fecha_registro,
    imagen
FROM equipos;
USE inventario_equipos;

INSERT INTO equipos
(codigo, tipo_equipo, marca, modelo, sistema, almacenamiento, ram, estado, fecha_mantenimiento, fecha_registro, imagen)
VALUES
('EQP001', 'Laptop', 'HP', 'Pavilion 15', 'Windows 10', '512GB SSD', '8GB', 'Operativo', '2025-02-01', '2025-02-10', 'EQP001_20250210120100.jpg'),
('EQP002', 'PC Escritorio', 'Dell', 'OptiPlex 7080', 'Windows 11', '1TB HDD', '16GB', 'Operativo', '2025-01-15', '2025-02-08', 'EQP002_20250208152321.jpg'),
('EQP003', 'Laptop', 'Lenovo', 'ThinkPad T480', 'Windows 10', '256GB SSD', '8GB', 'En mantenimiento', '2025-03-01', '2025-02-05', 'EQP003_20250205110455.jpg'),
('EQP004', 'Tablet', 'Samsung', 'Galaxy Tab A7', 'Android 13', '64GB', '4GB', 'Operativo', '2025-02-20', '2025-02-09', 'EQP004_20250209091211.jpg'),
('EQP005', 'PC Escritorio', 'Acer', 'Veriton M4660G', 'Windows 10', '500GB HDD', '8GB', 'Dado de baja', '2024-12-10', '2025-02-04', 'EQP005_20250204145533.jpg'),
('EQP006', 'Laptop', 'Asus', 'VivoBook X515', 'Windows 11', '512GB SSD', '12GB', 'Operativo', '2025-02-05', '2025-02-07', 'EQP006_20250207130521.jpg'),
('EQP007', 'Tablet', 'Apple', 'iPad 9th Gen', 'iPadOS 17', '128GB', '3GB', 'Operativo', '2025-01-30', '2025-02-06', 'EQP007_20250206180044.jpg'),
('EQP008', 'Laptop', 'HP', 'EliteBook 840', 'Windows 11', '256GB SSD', '16GB', 'En mantenimiento', '2025-02-18', '2025-02-03', 'EQP008_20250203111222.jpg'),
('EQP009', 'PC Escritorio', 'Lenovo', 'IdeaCentre 3', 'Windows 10', '1TB SSD', '16GB', 'Operativo', '2025-02-02', '2025-02-02', 'EQP009_20250202110000.jpg'),
('EQP010', 'Laptop', 'Dell', 'Latitude 5420', 'Windows 11', '512GB SSD', '8GB', 'Operativo', '2025-02-14', '2025-02-01', 'EQP010_20250201143055.jpg');


SELECT * FROM vista_equipos;
