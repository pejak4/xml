insert into users (enabled, role, first_name, last_name, email, password, first_time_logged, add_number) values (true, 'ADMIN', 'Jovan', 'Jenjic', 'jovanjenjic@gmail.com', '$2a$12$lUkTv0gLVYkR.1Tc9gfvHuIikDppNCeLCjaj0QgYPx0ryfNl7xPbG', true, 0);
insert into users (enabled, role, first_name, last_name, email, password, first_time_logged, add_number) values (true, 'ADMIN', 'Stefan', 'Pejakovic', 'stefanpejakovic@gmail.com', '$2a$12$m.fpFh4i/Q3NhpPaoI2sJemIHV.9pmrLyBbJ6w38exWeWCuyliDGe', true, 0);
insert into users (enabled, role, first_name, last_name, email, password, first_time_logged, add_number) values (true, 'USER', 'Aleksandar', 'Kosic', 'aleksandarkosic@gmail.com', '$2a$12$f8//CXhoYyrwZleXg3i8De6VT1tmLw4i/9efcElu615VyeUlJstPG', true, 0);

insert into role (role) values ('ADMIN');
insert into role (role) values ('USER');

alter sequence users_id_seq restart with 4;