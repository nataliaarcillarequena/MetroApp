create database MetroSystem;
use MetroSystem;

-- user
create table user(
firstName varchar(30),
surname varchar(30),
userName varchar(30) primary key,
password varchar(30), 
balance double);

-- insert records into user
insert into user
values("Na", "Arc", "Natar", "pass1", 100);

select * from user;
delete from user where userName = "shatar";

-- Journey
create table journey(
stations varchar(30),
orders int primary key
);

insert into journey
values("Grays", 1),("Ockendon", 2),("Upminster", 3),("Barking", 4),("West Ham", 5);

select * from journey;

create table userMetro(
userName varchar(30) primary key,
balance double,
swipeIn varchar(30),
swipeOut varchar(30),
totalFare double);

insert into userMetro
values("Natar", 100, "Grays", "Upminster", 10);
insert into userMetro values("Chatar", 19, "Upminster", null, 0);

select * from userMetro;
insert into userMetro values("shatar", 100, "Grays", null, 0);
delete from userMetro where userName = "shatar";