insert into usuarios(nombre,contraseña) values('carla','1234');
insert into usuarios(nombre,contraseña) values('anton','1234');
insert into usuarios(nombre,contraseña) values('maria','5678');
insert into usuarios(nombre,contraseña) values('juan','5678');

insert into solicitar_amistad(solicitante, solicitado,estado) values('carla','anton','aceptado');
insert into solicitar_amistad(solicitante, solicitado,estado) values('maria','carla','aceptado');
insert into solicitar_amistad(solicitante, solicitado,estado) values('juan','carla','pendiente');
