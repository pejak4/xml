insert into brand (brand) values ('BMW')
insert into brand (brand) values ('Audi')
insert into brand (brand) values ('Golf')
insert into brand (brand) values ('Mercedes')

insert into model (model, brand_id) values ('M5', 1)
insert into model (model, brand_id) values ('M4', 1)
insert into model (model, brand_id) values ('M3', 1)
insert into model (model, brand_id) values ('3', 1)
insert into model (model, brand_id) values ('4', 1)
insert into model (model, brand_id) values ('5', 1)
insert into model (model, brand_id) values ('X5', 1)
insert into model (model, brand_id) values ('X6', 1)
insert into model (model, brand_id) values ('A3', 2)
insert into model (model, brand_id) values ('A4', 2)
insert into model (model, brand_id) values ('A5', 2)
insert into model (model, brand_id) values ('A6', 2)
insert into model (model, brand_id) values ('4', 3)
insert into model (model, brand_id) values ('5', 3)
insert into model (model, brand_id) values ('ML', 4)
insert into model (model, brand_id) values ('GL', 4)

insert into transmission (transmission) values ('Automatic')
insert into transmission (transmission) values ('Manuel')

insert into fuel_type (fuel_type) values ('Diesel')
insert into fuel_type (fuel_type) values ('Gasoline')

alter sequence brand_id_seq restart with 5
alter sequence model_id_seq restart with 17
alter sequence fuel_type_id_seq restart with 3
alter sequence transmission_id_seq restart with 3