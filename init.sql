create table car (
  id serial primary key,
  brand text,
  model text
);

insert into car (brand, model) values ('VW', 'Golf');
insert into car (brand, model) values ('Ford', 'Fiesta');
insert into car (brand, model) values ('GM', 'Cruze');
insert into car (brand, model) values ('GM', 'Cobalt');
insert into car (brand, model) values ('GM', 'Cobalt');
insert into car (brand, model) values ('Fiat', 'Palio');
insert into car (brand, model) values ('GVW', 'Up');

select * from car;

create table cav (
  id serial primary key,
  name text
);

insert into cav (name) values ('Botafogo');
insert into cav (name) values ('Barra da Tijuca');
insert into cav (name) values ('Norte Shopping');

select * from cav;

create table visit (
  id serial primary key,
  time timestamp,
  car_id integer references car(id),
  cav_id integer references cav(id)
);

insert into visit (time, car_id, cav_id) values (
  TIMESTAMP '2019-07-17 11:00:00', 1, 1);

insert into visit (time, car_id, cav_id) values (
  TIMESTAMP '2019-07-17 14:00:00', 7, 1);

insert into visit (time, car_id, cav_id) values (
  TIMESTAMP '2019-07-17 11:00:00', 3, 3);

insert into visit (time, car_id, cav_id) values (
  TIMESTAMP '2019-07-18 10:00:00', 2, 2);

insert into visit (time, car_id, cav_id) values (
  TIMESTAMP '2019-07-18 14:00:00', 2, 2);

insert into visit (time, car_id, cav_id) values (
  TIMESTAMP '2019-09-3 14:00:00', 2, 1);

create table inspection (
  id serial primary key,
  time timestamp,
  car_id integer references car(id),
  cav_id integer references cav(id)
);

insert into inspection (time, car_id, cav_id) values (
  TIMESTAMP '2019-07-17 11:00:00', 7, 1);

insert into inspection (time, car_id, cav_id) values (
  TIMESTAMP '2019-07-17 11:00:00', 2, 2);

insert into inspection (time, car_id, cav_id) values (
  TIMESTAMP '2019-07-17 10:00:00', 3, 3);

insert into inspection (time, car_id, cav_id) values (
  TIMESTAMP '2019-07-17 11:00:00', 4, 3);

insert into inspection (time, car_id, cav_id) values (
  TIMESTAMP '2019-07-17 12:00:00', 5, 3);

insert into inspection (time, car_id, cav_id) values (
  TIMESTAMP '2019-09-3 11:00:00', 2, 1);
