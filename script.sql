create table usuario (id serial not null, nombre varchar(100) not null, correo varchar(100) not null, contraseña varchar(100) not null, direccion varchar(100), telefono varchar(100), cargo varchar(100), isCliente boolean, isPersonal boolean, isAdministrador boolean, primary key(id));


create table producto(id serial not null, nombre varchar(100) not null, imagen varchar() not null, tamaño varchar(100) not null, precio float not null, cantidad int not null, descripcion varchar() not null, categoria varchar(100) not null, primary key(id));


create table proveedor(id serial not null, nombre varchar(100) not null, correo varchar(100) not null, telefono varchar(100) not null, direccion varchar(100) not null, nit varchar(100) not null, primary key(id));


create table ingreso(id serial not null, fecha varchar(15) not null, hora varchar(15) not null, motivo varchar(100) not null, primary key(id));


create table ingreso_detalle(id serial not null, cantidad int not null, ingreso_id int not null, producto_id int not null,
                             foreign key(ingreso_id) references ingreso(id),
                             foreign key(producto_id) references producto(id), primary key(id));


create table salida (id serial not null, fecha varchar(15) not null, hora varchar(15) not null, motivo varchar(100) not null, primary key(id));


create table salida_detalle(id serial not null, cantidad int not null, salida_id int not null, producto_id int not null,
                            foreign key(salida_id) references salida(id),
                            foreign key(producto_id) references producto(id), primary key(id));


create table compra (id serial not null, fecha varchar(15) not null, hora varchar(15) not null, monto_total float not null, proveedor_id int not null,
                     foreign key(proveedor_id) references proveedor(id), primary key(id));


create table compra_detalle (id serial not null, cantidad int not null, precio float not null, compra_id int not null, producto_id int not null,
                             foreign key(compra_id) references compra(id),
                             foreign key(producto_id) references producto(id), primary key(id));


create table pedido (id serial not null, fecha varchar(15) not null, hora varchar(15) not null, monto_total float not null, estado varchar(100) not null, usuario_id int not null,
                     foreign key(usuario_id) references usuario(id), primary key(id));


create table pedido_detalle (id serial not null, cantidad int not null, precio float not null, pedido_id int not null, producto_id int not null,
                             foreign key(pedido_id) references pedido(id),
                             foreign key(producto_id) references producto(id), primary key(id));


create table carrito (id serial not null, monto_total float not null, fecha varchar(15) not null, hora varchar(15) not null, usuario_id int not null,
                      foreign key(usuario_id) references usuario(id), primary key(id));


create table carrito_detalle (id serial not null, cantidad int not null, precio float not null, carrito_id int not null, producto_id int not null,
                              foreign key(carrito_id) references carrito(id),
                              foreign key(producto_id) references producto(id), primary key(id));

-- seeders
-- crear 3 usuarios

INSERT INTO usuario (nombre, correo, contraseña, direccion, telefono, cargo, es_cliente, es_personal, es_administrador)
VALUES ('Nahuel Zalazar',
        'zvnahuel43@gmail.com',
        '3cdaa8ddeb4b7e89161e6e0438c39746d9d5e182e044b058ee2d66ea0fdc2024',
        'Calle 24 de Septiembre',
        '123456789',
        '',
        true,
        false,
        false);


INSERT INTO usuario (nombre, correo, contraseña, direccion, telefono, cargo, es_cliente, es_personal, es_administrador)
VALUES ('Nahuel Zalazr',
        'nahuel.zalazar@nahuelxzv.pro',
        '3cdaa8ddeb4b7e89161e6e0438c39746d9d5e182e044b058ee2d66ea0fdc2024',
        'Calle 24 de Septiembre',
        '123456789',
        'Almacenista',
        false,
        true,
        false);


-- crear 6 productos

INSERT INTO producto (nombre, imagen, tamaño, precio, cantidad, descripcion, categoria)
VALUES ('Amoladora Angular 4 1/2',
        'https://galba.com.bo/wp-content/uploads/2021/09/BO0012.jpg',
        'Estandar',
        731,
        10,
        'Amoladora Angular 4 1/2″ 710W 11000 RPM',
        'herramientas');


INSERT INTO producto (nombre, imagen, tamaño, precio, cantidad, descripcion, categoria)
VALUES ('Atornillador 570W',
        'https://galba.com.bo/wp-content/uploads/2021/09/MA0006.jpg',
        'Estandar',
        1.380,
        5,
        'Atornillador 570W 0-4500 RPM',
        'herramientas');


INSERT INTO producto (nombre, imagen, tamaño, precio, cantidad, descripcion, categoria)
VALUES ('Broca HSS p/metal',
        'https://galba.com.bo/wp-content/uploads/2022/11/IW0001.jpg',
        'Pequeño',
        9,
        100,
        'Broca HSS p/metal 1/16″',
        'herramientas');


INSERT INTO producto (nombre, imagen, tamaño, precio, cantidad, descripcion, categoria)
VALUES ('Foco Led Tipo Ovni 18W',
        'https://galba.com.bo/wp-content/uploads/2022/09/TP0233.jpg',
        'Pequeño',
        55,
        20,
        'Foco Led Tipo Ovni 18W',
        'iluminacion');


INSERT INTO producto (nombre, imagen, tamaño, precio, cantidad, descripcion, categoria)
VALUES ('Escalera de Aluminio 4 Escalones',
        'https://galba.com.bo/wp-content/uploads/2021/09/TP0087.jpg',
        'Pequeño',
        1046,
        4,
        'Escalera de Aluminio 4 Escalones',
        'escaleras y carretillas');


INSERT INTO producto (nombre, imagen, tamaño, precio, cantidad, descripcion, categoria)
VALUES ('Pega Todo Ribecola',
        'https://galba.com.bo/wp-content/uploads/2021/10/RI0010.jpg',
        'Pequeño',
        45,
        70,
        'Pega Todo Ribecola 1/2 Galón',
        'pegado y sellado');


INSERT INTO producto (nombre, imagen, tamaño, precio, cantidad, descripcion, categoria)
VALUES ('Candado de Cable de Combinación',
        'https://galba.com.bo/wp-content/uploads/2022/11/TP0194.jpg',
        'Pequeño',
        53,
        12,
        'Candado de Cable de Combinación 1/4″',
        'seguridad industrial');

-- crear 3 proveedores

INSERT INTO proveedor (nombre, correo, telefono, direccion, nit)
VALUES ('Ferreteria La Paz',
        'lps@gmail.com',
        '56525225',
        'Av. 6 de Agosto',
        '123456789');


INSERT INTO proveedor (nombre, correo, telefono, direccion, nit)
VALUES ('Ferreteria Santa Cruz',
        'scz@gmail.com',
        '56525225',
        'Calle 24 de Septiembre',
        '123456789');

-- Compras

INSERT INTO compra (fecha, hora, monto_total, proveedor_id)
VALUES ('05/Jan/2024',
        '10:00',
        1000,
        1);


INSERT INTO compra (fecha, hora, monto_total, proveedor_id)
VALUES ('05/Mar/2024',
        '11:00',
        500,
        1);


INSERT INTO compra (fecha, hora, monto_total, proveedor_id)
VALUES ('05/Jul/2024',
        '15:00',
        700,
        1);


INSERT INTO compra (fecha, hora, monto_total, proveedor_id)
VALUES ('05/Aug/2024',
        '15:00',
        1400,
        2);


INSERT INTO compra (fecha, hora, monto_total, proveedor_id)
VALUES ('05/Nov/2024',
        '15:00',
        2500,
        2);

-- Pedidos

INSERT INTO pedido (fecha, hora, monto_total, estado, usuario_id)
VALUES ('05/Jan/2024',
        '10:00',
        1000,
        'Completado',
        2);


INSERT INTO pedido (fecha, hora, monto_total, estado, usuario_id)
VALUES ('05/Mar/2024',
        '10:00',
        1000,
        'Completado',
        2);


INSERT INTO pedido (fecha, hora, monto_total, estado, usuario_id)
VALUES ('05/Jun/2024',
        '10:00',
        1000,
        'Completado',
        2);


INSERT INTO pedido (fecha, hora, monto_total, estado, usuario_id)
VALUES ('05/Aug/2024',
        '10:00',
        1000,
        'Completado',
        2);

