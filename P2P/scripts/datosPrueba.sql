insert into sucursales(direccion, fecha_apertura) values('Plaza Roja 5, Santiago de Compostela', '2009-01-01');
insert into sucursales(direccion, fecha_apertura) values('Calle del Arenal 25, Vigo', '2005-02-24');
insert into sucursales(direccion, fecha_apertura) values('Calle Real 30, A Coruña', '2003-12-15');

insert into socios(dni, nombre, telefono, puntos, categoria) values('12345678E', 'Pedro Arias Pérez', '653202012', 0, 'bronce');
insert into socios(dni, nombre, telefono, puntos, categoria) values('56324578O', 'María Ponte García', '699784512', 0, 'bronce');
insert into socios(dni, nombre, telefono, puntos, categoria) values('45230010I', 'Paula Quintana Rey', '633427591', 0, 'bronce');

insert into almacenes(nombre, direccion) values('Almacen general', 'Polígono industrial, Santiago de Compostela');
insert into almacenes(nombre,direccion) values('As Termas','Calle de la Industria 21, Polígono de O Ceao, Lugo');

insert into articulos(modelo, talla, color, genero, precio_base) values('Camiseta', 'S', 'Blanco', 'M', 10);
insert into articulos(modelo, talla, color, genero, precio_base) values('Camiseta', 'M', 'Blanco', 'M', 10);
insert into articulos(modelo, talla, color, genero, precio_base) values('Camiseta', 'M', 'Azul', 'M', 10);
insert into articulos(modelo, talla, color, genero, precio_base) values('Camiseta', 'S', 'Blanco', 'F', 10);
insert into articulos(modelo, talla, color, genero, precio_base) values('Camiseta', 'S', 'Azul', 'F', 10);
insert into articulos(modelo, talla, color, genero, precio_base) values('Camiseta', 'S', 'Negro', 'F', 10);

insert into articulos(modelo, talla, color, genero, precio_base) values('Camisa', 'S', 'Blanco', 'M', 15);
insert into articulos(modelo, talla, color, genero, precio_base) values('Camisa', 'M', 'Blanco', 'M', 15);
insert into articulos(modelo, talla, color, genero, precio_base) values('Camisa', 'M', 'Azul', 'M', 15);
insert into articulos(modelo, talla, color, genero, precio_base) values('Camisa', 'S', 'Blanco', 'F', 15);
insert into articulos(modelo, talla, color, genero, precio_base) values('Camisa', 'S', 'Azul', 'F', 15);
insert into articulos(modelo, talla, color, genero, precio_base) values('Camisa', 'S', 'Negro', 'F', 15);

insert into articulos(modelo, talla, color, genero, precio_base) values('Sudadera', 'S', 'Blanco', 'M', 19.99);
insert into articulos(modelo, talla, color, genero, precio_base) values('Sudadera', 'M', 'Blanco', 'M', 19.99);
insert into articulos(modelo, talla, color, genero, precio_base) values('Sudadera', 'M', 'Azul', 'M', 19.99);
insert into articulos(modelo, talla, color, genero, precio_base) values('Sudadera', 'S', 'Blanco', 'F', 19.99);
insert into articulos(modelo, talla, color, genero, precio_base) values('Sudadera', 'S', 'Azul', 'F', 19.99);
insert into articulos(modelo, talla, color, genero, precio_base) values('Sudadera', 'S', 'Negro', 'F', 19.99);


insert into articulos(modelo, talla, color, genero, precio_base) values('Pantalon', '38', 'Blanco', 'M', 22.99);
insert into articulos(modelo, talla, color, genero, precio_base) values('Pantalon', '40', 'Blanco', 'M', 22.99);
insert into articulos(modelo, talla, color, genero, precio_base) values('Pantalon', '44', 'Azul', 'M', 22.99);
insert into articulos(modelo, talla, color, genero, precio_base) values('Pantalon', '36', 'Blanco', 'F', 22.99);
insert into articulos(modelo, talla, color, genero, precio_base) values('Pantalon', '40', 'Azul', 'F', 22.99);
insert into articulos(modelo, talla, color, genero, precio_base) values('Pantalon', '38', 'Negro', 'F', 22.99);

insert into articulos(modelo, talla, color, genero, precio_base) values('Falda', '38', 'Blanco', 'F', 16);
insert into articulos(modelo, talla, color, genero, precio_base) values('Falda', '40', 'Rosa', 'F', 16);
insert into articulos(modelo, talla, color, genero, precio_base) values('Falda', '44', 'Rojo', 'F', 16);
insert into articulos(modelo, talla, color, genero, precio_base) values('Falda', '36', 'Blanco', 'F', 16);
insert into articulos(modelo, talla, color, genero, precio_base) values('Falda', '40', 'Azul', 'F', 16);
insert into articulos(modelo, talla, color, genero, precio_base) values('Falda', '38', 'Morado', 'F', 16);


--Contraseñas: 1234
insert into gerentes(sal, hash_contraseña, nombre, direccion, correo, fecha_nacimiento, telefono)
values ('\xc66595d2c26a801fb946e2c646c64bb29bc7a85c3573c927c76c7fdf82900e16', '\x7f67034caa8fda956ca137d144fba837482074160d560b09cc9fa5505f4ab9b70f1b3254124c8b7781a297831dc79a525c18a57a99c6d192cc4f8aec60703f0b', 'Gerente', 'Avenida de la Hispanidad, 34, Vigo', 'gerenecia@forelsket.com', '1995-11-23', '612343445');

insert into empleados(sal, hash_contraseña, nombre, direccion, correo, fecha_nacimiento, telefono, sucursal)
values ('\x0eb5462fa442ebc0d34ee050d4a8be30def0a761b7fbaac77457807be6d5d646', '\x75156491711dfc13acd84121a43fdfa4cf0f3d7ea76c3204b7d0e722488ad6c8ab43c7d21b9a5a1e418d2f5cb16a7de4f20e8f6fae1de4c03214bef2a86f6fee', 'Empleado 1', 'Avenida de Europa, 45, Vigo', 'empleado1@forelsket.com', '1998-1-12', '622346579', 1);

insert into empleados(sal, hash_contraseña, nombre, direccion, correo, fecha_nacimiento, telefono, sucursal)
values ('\x17e4a89d2ffb8ae04d045976204b2a5820570272c4af3a5edc2536be2fca34a2', '\xaa5c868f63df2176292c72a169d14b4e8cb253b9eebe33e5e71e04c1c6fb85d22c6d31b0d6d5af09fe4b4db278fc54f09ba687c49a2aa303b4b83c0b8ed38674', 'Empleado 2', 'Avenida de Ramón Nieto, 84, Vigo', 'empleado2@forelsket.com', '1987-4-5', '633244579', 2);


insert into contratos(empleado, fecha_inicio, fecha_fin, sueldo, horas_semana) 
values (428871, '2020-01-14', '2021-01-14', 1200, 40);

insert into contratos(empleado, fecha_inicio, fecha_fin, sueldo, horas_semana) 
values (428944, '2019-07-22', '2020-10-03', 950, 20);

insert into contratos(empleado, fecha_inicio, fecha_fin, sueldo, horas_semana) 
values (428944, '2018-11-30', '2019-06-20', 950, 10);

insert into dias_trabajados(empleado, fecha, hora_entrada, hora_salida) 
values (428944, '2020-05-01', '09:01', '13:55');
insert into dias_trabajados(empleado, fecha, hora_entrada, hora_salida) 
values (428944, '2020-05-02', '09:03', '14:01');
insert into dias_trabajados(empleado, fecha, hora_entrada, hora_salida) 
values (428944, '2020-05-03', '09:02', '14:00');
insert into dias_trabajados(empleado, fecha, hora_entrada, hora_salida) 
values (428944, '2020-05-04', '08:58', '13:59');
insert into dias_trabajados(empleado, fecha, hora_entrada, hora_salida) 
values (428944, '2020-05-05', '08:56', '14:05');

insert into dias_trabajados(empleado, fecha, hora_entrada, hora_salida) 
values (428871, '2020-05-01', '16:01', '20:05');
insert into dias_trabajados(empleado, fecha, hora_entrada, hora_salida) 
values (428871, '2020-05-02', '16:10', '20:01');
insert into dias_trabajados(empleado, fecha, hora_entrada, hora_salida) 
values (428871, '2020-05-03', '15:58', '20:02');
insert into dias_trabajados(empleado, fecha, hora_entrada, hora_salida) 
values (428871, '2020-05-04', '16:01', '19:57');
insert into dias_trabajados(empleado, fecha, hora_entrada, hora_salida) 
values (428871, '2020-05-05', '15:55', '20:02');

insert into tiene(sucursal,articulo,cantidad) values(2,192882310,15);
insert into tiene(sucursal,articulo,cantidad) values(2,192963031,15);
insert into tiene(sucursal,articulo,cantidad) values(2,192855403,15);
insert into tiene(sucursal,articulo,cantidad) values(2,192837465,15);
insert into tiene(sucursal,articulo,cantidad) values(2,193079628,15);

insert into tiene(sucursal,articulo,cantidad) values(1,193079628,15);
insert into tiene(sucursal,articulo,cantidad) values(1,192855403,15);
insert into tiene(sucursal,articulo,cantidad) values(1,192837465,15);

insert into ventas(socio, empleado, fecha) values('45230010I',428944, '2010-02-07');
insert into venta_formada_por(venta, articulo, precio_venta) values(1, 192882310, 40);
insert into venta_formada_por(venta, articulo, precio_venta) values(1, 192963031, 20);
insert into venta_formada_por(venta, articulo, precio_venta) values(1, 192855403, 10);

insert into ventas(socio, empleado, fecha) values('56324578O',428871, '2015-12-08');
insert into venta_formada_por(venta, articulo, precio_venta) values(2, 193079628, 40);
insert into venta_formada_por(venta, articulo, precio_venta) values(2, 192855403, 20);
insert into venta_formada_por(venta, articulo, precio_venta) values(2, 192837465, 10);

insert into ventas(socio, empleado, fecha) values('12345678E',428944, '2020-01-07');
insert into venta_formada_por(venta, articulo, precio_venta) values(3, 192855403, 40);
insert into venta_formada_por(venta, articulo, precio_venta) values(3, 192963031, 20);
insert into venta_formada_por(venta, articulo, precio_venta) values(3, 192837465, 10);
insert into venta_formada_por(venta, articulo, precio_venta) values(3, 192882310, 15);
insert into venta_formada_por(venta, articulo, precio_venta) values(3, 193079628, 9.99);

insert into ofertas(nombre,descuento,fecha_inicio,duracion) values('Rebajas',0.5,'2020-01-08',23);
insert into oferta_aplica(articulo,oferta) values(192837465,1);
insert into oferta_aplica(articulo,oferta) values(192855403,1);

insert into ofertas(nombre,descuento,fecha_inicio,duracion) values('Cuarentena',0.25,'2020-03-13',60);
insert into oferta_aplica(articulo,oferta) values(192855403,2); 
insert into oferta_aplica(articulo,oferta) values(192837465,2);
insert into oferta_aplica(articulo,oferta) values(193079628,2);

insert into ofertas(nombre,descuento,fecha_inicio,duracion) values('Desescalada',0.3,'2020-05-8',60); 
insert into oferta_aplica(articulo,oferta) values(192837465,3);
insert into oferta_aplica(articulo,oferta) values(193079628,3);
insert into oferta_aplica(articulo,oferta) values(192855403,3);

insert into ofertas(nombre,descuento,fecha_inicio,duracion) values('Primavera 2020',0.5,'2020-05-9',60);
insert into oferta_aplica(articulo,oferta) values(192855403,4); 
insert into oferta_aplica(articulo,oferta) values(192837465,4);

insert into envia(almacen,sucursal,fecha_hora) values('As Termas',1,'2020-05-10 16:58:41.85701');
insert into pertenecer_envio(articulo,almacen,sucursal,fecha_hora,cantidad) values (192855403,'As Termas',1,'2020-05-10 16:58:41.85701',5);
insert into pertenecer_envio(articulo,almacen,sucursal,fecha_hora,cantidad) values (193079628,'As Termas',1,'2020-05-10 16:58:41.85701',5);

insert into envia(almacen,sucursal,fecha_hora) values('Almacen general',2,'2020-05-10 17:02:55.848725');
insert into pertenecer_envio(articulo,almacen,sucursal,fecha_hora,cantidad) values (192837465,'Almacen general',2,'2020-05-10 17:02:55.848725',5);
insert into pertenecer_envio(articulo,almacen,sucursal,fecha_hora,cantidad) values (192963031,'Almacen general',2,'2020-05-10 17:02:55.848725',5);
