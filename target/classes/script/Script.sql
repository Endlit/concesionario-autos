-- Opcion

-- GET /opciones → SELECT de todas las opciones
SELECT * FROM opcion;

-- POST /opciones/save → INSERT nueva opción
INSERT INTO opcion (nombre, precio) VALUES (?, ?);

-- GET /opciones/delete/{id} → DELETE por id
DELETE FROM opcion WHERE id = ?;

-- Usuarios

-- GET /usuarios → SELECT todos los usuarios
SELECT * FROM usuario;

-- POST /usuarios/save → INSERT o UPDATE según exista DNI
INSERT INTO
    usuario (
        dni,
        nombre,
        apellido,
        rol,
        correo
    )
VALUES (?, ?, ?, ?, ?)
ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre),
    apellido = VALUES(apellido),
    rol = VALUES(rol),
    correo = VALUES(correo);

-- GET /usuarios/delete/{dni} → DELETE usuario
DELETE FROM usuario WHERE dni = ?;

-- Vehiculo
-- GET /vehiculos → lista de vehículos + usuarios disponibles
SELECT * FROM vehiculo;

SELECT * FROM usuario;

-- POST /vehiculos/save (cliente opcional)
-- Si *trae clienteDni*
UPDATE vehiculo
SET
    marca = ?,
    modelo = ?,
    precio = ?,
    cliente_dni = ?
WHERE
    matricula = ?;

-- Si *NO trae cliente (null)*
UPDATE vehiculo
SET
    marca = ?,
    modelo = ?,
    precio = ?,
    cliente_dni = NULL
WHERE
    matricula = ?;

-- Nuevo registro (si no existe)
INSERT INTO
    vehiculo (
        matricula,
        marca,
        modelo,
        precio,
        cliente_dni
    )
VALUES (?, ?, ?, ?, ?)
ON DUPLICATE KEY UPDATE
    marca = VALUES(marca),
    modelo = VALUES(modelo),
    precio = VALUES(precio),
    cliente_dni = VALUES(cliente_dni);

-- GET /vehiculos/delete/{matricula}
DELETE FROM vehiculo WHERE matricula = ?;

-- Venta
-- GET /ventas → lista de ventas + vehículos + usuarios + opciones
SELECT * FROM venta;

SELECT * FROM vehiculo;

SELECT * FROM usuario;

SELECT * FROM opcion;

-- POST /ventas/save (opciones opcionales)
-- 1. Insertar venta
INSERT INTO
    venta (
        fecha,
        precio_venta,
        vendedor_dni,
        vehiculo_matricula
    )
VALUES (CURDATE(), ?, ?, ?);

-- 2. Si trae vehiculo usado (NO MODIFICA pero podría)
-- Podría almacenarse si tu modelo lo permite:
UPDATE venta SET vehiculo_usado = ? WHERE id = LAST_INSERT_ID();

-- 3. Si trae opciones
-- Por cada id en opcionIds:
INSERT INTO
    venta_opcion (
        venta_id,
        opcion_id,
        precio_aplicado
    )
VALUES (LAST_INSERT_ID(), ?, 0.0);

-- GET  /ventas/delete/{id}
-- Primero eliminar registros puente
DELETE FROM venta_opcion WHERE venta_id = ?;

-- Luego borrar la venta
DELETE FROM venta WHERE id = ?;