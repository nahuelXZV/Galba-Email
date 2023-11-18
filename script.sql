create table usuario (
    id serial not null,
    nombre varchar(100) not null,
    correo varchar(100) not null,
    contraseña varchar(100) not null,
    direccion varchar(100),
    telefono varchar(100),
    cargo varchar(100),
    isCliente boolean,
    isPersonal boolean,
    isAdministrador boolean,
    primary key(id)
);

create table producto(
    id serial not null,
    nombre varchar(100) not null,
    imagen varchar() not null,
    tamaño varchar(100) not null,
    precio float not null,
    cantidad int not null,
    descripcion varchar() not null,
    categoria varchar(100) not null,
    primary key(id)
);

create table proveedor(
    id serial not null,
    nombre varchar(100) not null,
    correo varchar(100) not null,
    telefono varchar(100) not null,
    direccion varchar(100) not null,
    nit varchar(100) not null,
    primary key(id)
);


create table ingreso(
    id serial not null,
    fecha varchar(15) not null,
    hora varchar(15) not null,
    motivo varchar(100) not null,
    primary key(id)
);

create table ingreso_detalle(
    id serial not null,
    cantidad int not null,
    ingreso_id int not null,
    producto_id int not null,
    foreign key(ingreso_id) references ingreso(id),
    foreign key(producto_id) references producto(id),
    primary key(id)
);

create table salida (
    id serial not null,
    fecha varchar(15) not null,
    hora varchar(15) not null,
    motivo varchar(100) not null,
    primary key(id)
);

create table salida_detalle(
    id serial not null,
    cantidad int not null,
    salida_id int not null,
    producto_id int not null,
    foreign key(salida_id) references salida(id),
    foreign key(producto_id) references producto(id),
    primary key(id)
);

create table compra (
    id serial not null,
    fecha varchar(15) not null,
    hora varchar(15) not null,
    monto_total float not null,
    proveedor_id int not null,
    foreign key(proveedor_id) references proveedor(id),
    primary key(id)
);

create table compra_detalle (
    id serial not null,
    cantidad int not null,
    precio float not null,
    compra_id int not null,
    producto_id int not null,
    foreign key(compra_id) references compra(id),
    foreign key(producto_id) references producto(id),
    primary key(id)
);

create table pedido (
    id serial not null,
    fecha varchar(15) not null,
    hora varchar(15) not null,
    monto_total float not null,
    estado varchar(100) not null,
    usuario_id int not null,
    foreign key(usuario_id) references usuario(id),
    primary key(id)
);

create table pedido_detalle (
    id serial not null,
    cantidad int not null,
    precio float not null,
    pedido_id int not null,
    producto_id int not null,
    foreign key(pedido_id) references pedido(id),
    foreign key(producto_id) references producto(id),
    primary key(id)
);

create table carrito (
    id serial not null,
    monto_total float not null,
    fecha varchar(15) not null,
    hora varchar(15) not null,
    usuario_id int not null,
    foreign key(usuario_id) references usuario(id),
    primary key(id)
);

create table carrito_detalle (
    id serial not null,
    cantidad int not null,
    precio float not null,
    carrito_id int not null,
    producto_id int not null,
    foreign key(carrito_id) references carrito(id),
    foreign key(producto_id) references producto(id),
    primary key(id)
);


