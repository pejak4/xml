insert into car (second_id, rating, num_of_rating, user_id, doors, description, image, brand, model, fuel_type, transmission, class_car, price, mileage, planned_mileage, cdw, capacity_seats, capacity_seats_for_kids, cubic_capacity, horse_power, usb, gps, fuel_tank_capacity, city_location)
 values (1, 0, 0, 1, 5, 'Description1', 'bmwX5.jpg', 'BMW', 'X5', 'diesel', 'manuel', 'SUV', 220, 120000, 20000, true, 5, 0, 2200, 150, true, true, 70, 'Novi Sad');

insert into users (role, first_name, last_name, email, password, first_time_logged) values ('USER', 'Jovan', 'Jenjic', 'jovan.jenjic@gmail.com', '$2a$10$67WhFHSky5EtaPlkTj1nqOIWRTHGNbWqJxtpJTUZMBQA.2am2yKqe', true);
insert into users (role, first_name, last_name, email, password, first_time_logged) values ('ADMIN', 'Stefan', 'Pejakovic', 'stefan.pejakovic@gmail.com', '$2a$10$67WhFHSky5EtaPlkTj1nqOIWRTHGNbWqJxtpJTUZMBQA.2am2yKqe', true);
insert into users (role, first_name, last_name, email, password, first_time_logged) values ('USER', 'Aleksandar', 'Kosic', 'aleksandar.kosic@gmail.com', '$2a$10$67WhFHSky5EtaPlkTj1nqOIWRTHGNbWqJxtpJTUZMBQA.2am2yKqe', true);

insert into car_rental_request (check_mileage, second_id, role, create_date, start_date, end_date, rental_request_car_id, user_id, for_user_id) values (false, 3, 'CANCELED', '2020-06-11 10:43:12', '2020-02-02 10:43:12', '2020-03-02', 1, 3, 1);
insert into car_rental_request (check_mileage, second_id, role, create_date, start_date, end_date, rental_request_car_id, user_id, for_user_id) values (false, 4, 'PENDING', '2020-06-11 10:43:12', '2020-02-02 10:43:12', '2020-03-02', 1, 3, 1);
insert into car_rental_request (check_mileage, second_id, role, create_date, start_date, end_date, rental_request_car_id, user_id, for_user_id) values (false, 5, 'PAID', '2020-06-11 10:43:12', '2020-02-02 10:43:12', '2020-03-02', 1, 3, 1);

insert into comment (second_id, from_user_id, car_id, description_comment) values (1, 3, 1, 'Mnogo je brz');
insert into comment (second_id, from_user_id, car_id, description_comment) values (1, 3, 1, 'Super!');


insert into authority (name) values ('MANAGE_CERTIFICATE');
insert into authority (name) values ('MANAGE_USER');

insert into role (role) values ('ADMIN');
insert into role (role) values ('USER');

insert into role_authorities (role_name, authority_name) values ('ADMIN', 'MANAGE_CERTIFICATE');
insert into role_authorities (role_name, authority_name) values ('USER', 'MANAGE_USER');

alter sequence users_id_seq restart with 4;