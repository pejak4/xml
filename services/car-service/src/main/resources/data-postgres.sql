insert into car (user_id, doors, description, image, brand, model, fuel_type, transmission, class_car, price, mileage, planned_mileage, cdw, capacity_seats, capacity_seats_for_kids, cubic_capacity, horse_power, usb, gps, fuel_tank_capacity, city_location)
 values (1, 5, 'Description1', 'bmwX5.jpg', 'BMW', 'X5', 'diesel', 'manuel', 'SUV', 220, 120000, 20000, true, 5, 0, 2200, 150, true, true, 70, 'Novi Sad');
 insert into car (user_id, doors, description, image, brand, model, fuel_type, transmission, class_car, price, mileage, planned_mileage, cdw, capacity_seats, capacity_seats_for_kids, cubic_capacity, horse_power, usb, gps, fuel_tank_capacity, city_location)
 values (1, 5, 'Description1', 'bmwX6.jpg', 'BMW', 'X6', 'gasoline', 'automatic', 'classic', 290, 90000, 10000, true, 6, 1, 3500, 300, true, true, 80, 'Beograd');
insert into car (user_id, doors, description, image, brand, model, fuel_type, transmission, class_car, price, mileage, planned_mileage, cdw, capacity_seats, capacity_seats_for_kids, cubic_capacity, horse_power, usb, gps, fuel_tank_capacity, city_location)
 values (1, 5, 'Description1', 'mercedesML.jpg', 'Mercedes', 'ML', 'diesel', 'manuel', 'SUV', 310, 170000, 8000, false, 3, 2, 2500, 170, false, true, 60, 'Negotin');
 insert into car (user_id, doors, description, image, brand, model, fuel_type, transmission, class_car, price, mileage, planned_mileage, cdw, capacity_seats, capacity_seats_for_kids, cubic_capacity, horse_power, usb, gps, fuel_tank_capacity, city_location)
 values (2, 5, 'Description1', 'mercedesGL.jpg', 'Mercedes', 'GL', 'gasoline', 'automatic', 'SUV', 199, 300000, 5000, false, 7, 0, 2000, 110, false, false, 62, 'Nis');
 insert into car (user_id, doors, description, image, brand, model, fuel_type, transmission, class_car, price, mileage, planned_mileage, cdw, capacity_seats, capacity_seats_for_kids, cubic_capacity, horse_power, usb, gps, fuel_tank_capacity, city_location)
 values (2, 3, 'Description1', 'golf5.jpg', 'Golf', '5', 'gasoline', 'manuel', 'classic', 140, 190000, 7000, false, 5, 0, 1600, 70, false, false, 50, 'Prizren');
 insert into car (user_id, doors, description, image, brand, model, fuel_type, transmission, class_car, price, mileage, planned_mileage, cdw, capacity_seats, capacity_seats_for_kids, cubic_capacity, horse_power, usb, gps, fuel_tank_capacity, city_location)
 values (2, 5, 'Description1', 'golf6.jpg', 'Golf', '6', 'gasoline', 'manuel', 'classic', 175, 40000, 20000, true, 4, 1, 1200, 110, true, true, 63, 'Kula');
 insert into car (user_id, doors, description, image, brand, model, fuel_type, transmission, class_car, price, mileage, planned_mileage, cdw, capacity_seats, capacity_seats_for_kids, cubic_capacity, horse_power, usb, gps, fuel_tank_capacity, city_location)
 values (3, 5, 'Description1', 'audiA6.jpg', 'Audi', 'A6', 'diesel', 'automatic', 'classic', 199, 50000, 18000, true, 5, 0, 1900, 150, true, false, 55, 'Zajecar');
 insert into car (user_id, doors, description, image, brand, model, fuel_type, transmission, class_car, price, mileage, planned_mileage, cdw, capacity_seats, capacity_seats_for_kids, cubic_capacity, horse_power, usb, gps, fuel_tank_capacity, city_location)
 values (3, 5, 'Description1', 'audiA5.jpg', 'Audi', 'A5', 'diesel', 'automatic', 'classic', 310, 10000, 30000, false, 4, 1, 2500, 200, true, true, 66, 'Novi Sad');
 insert into car (user_id, doors, description, image, brand, model, fuel_type, transmission, class_car, price, mileage, planned_mileage, cdw, capacity_seats, capacity_seats_for_kids, cubic_capacity, horse_power, usb, gps, fuel_tank_capacity, city_location)
 values (3, 3, 'Description1', 'audiA4.jpg', 'Audi', 'A4', 'gasoline', 'manuel', 'SUV', 210, 78000, 11000, false, 5, 1, 2000, 90, false, false, 59, 'Beograd');
 insert into car (user_id, doors, description, image, brand, model, fuel_type, transmission, class_car, price, mileage, planned_mileage, cdw, capacity_seats, capacity_seats_for_kids, cubic_capacity, horse_power, usb, gps, fuel_tank_capacity, city_location)
 values (4, 3, 'Description1', 'bmwM4.jpg', 'BMW', 'm4', 'gasoline', 'automatic', 'SUV', 400, 60000, 15000, true, 2, 0, 3000, 300, false, true, 40, 'Beograd');
 insert into car (user_id, doors, description, image, brand, model, fuel_type, transmission, class_car, price, mileage, planned_mileage, cdw, capacity_seats, capacity_seats_for_kids, cubic_capacity, horse_power, usb, gps, fuel_tank_capacity, city_location)
 values (4, 3, 'Description1', 'bmw3.jpg', 'BMW', '3', 'diesel', 'manuel', 'classic', 420, 87000, 12000, false, 4, 0, 3300, 250, true, false, 45, 'Novi Sad');

 insert into car_rental_date (start_date, end_date, car_id) values ('2020-01-01 04:09:00', '2020-02-01', 1);
 insert into car_rental_date (start_date, end_date, car_id) values ('2020-02-02 10:43:12', '2020-03-02', 2);

 insert into car_rental_request (start_date, end_date, rental_request_car_id, user_id) values ('2020-02-02 10:43:12', '2020-03-02', 1, 3);
 insert into car_rental_request (start_date, end_date, rental_request_car_id, user_id) values ('2020-02-02 10:43:12', '2020-03-02', 2, 3);
 insert into car_rental_request (start_date, end_date, rental_request_car_id, user_id) values ('2020-02-02 10:43:12', '2020-03-02', 5, 3);

alter sequence car_id_seq restart with 12;
alter sequence car_rental_date_id_seq restart with 3;