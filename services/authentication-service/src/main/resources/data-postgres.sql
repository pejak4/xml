insert into users (role, first_name, last_name, email, password, first_time_logged) values ('ADMIN', 'Jovan', 'Jenjic', 'jovanjenjic@gmail.com', '$2a$10$67WhFHSky5EtaPlkTj1nqOIWRTHGNbWqJxtpJTUZMBQA.2am2yKqe', true);
insert into users (role, first_name, last_name, email, password, first_time_logged) values ('ADMIN', 'Stefan', 'Pejakovic', 'stefanpejakovic@gmail.com', '$2a$10$67WhFHSky5EtaPlkTj1nqOIWRTHGNbWqJxtpJTUZMBQA.2am2yKqe', true);
insert into users (role, first_name, last_name, email, password, first_time_logged) values ('USER', 'Aleksandar', 'Kosic', 'aleksandarkosic@gmail.com', '$2a$10$67WhFHSky5EtaPlkTj1nqOIWRTHGNbWqJxtpJTUZMBQA.2am2yKqe', true);

insert into role (role) values ('ADMIN');
insert into role (role) values ('USER');

alter sequence users_id_seq restart with 4;