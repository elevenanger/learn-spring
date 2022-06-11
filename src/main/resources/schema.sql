create table if not exists taco_order (
    id serial primary key,
    delivery_name varchar(50) not null,
    delivery_street varchar(50) not null,
    delivery_city varchar(50) not null,
    delivery_state varchar(20) not null,
    delivery_zip varchar(10) not null,
    cc_number varchar(16) not null,
    cc_expiration varchar(20) not null,
    cc_cvv varchar(3) not null,
    created_date timestamp not null
);

create table if not exists Taco (
    id serial PRIMARY KEY,
    name varchar(50) not null,
    taco_order bigint not null,
    taco_order_key bigint not null,
    created_date timestamp not null
);

create table if not exists ingredient_ref (
    ingredient varchar(4) not null,
    taco bigint not null,
    taco_key bigint not null
);

create table if not exists ingredient (
    id varchar(4) not null primary key,
    name varchar(25) not null,
    type varchar(10) not null
);

create table if not exists user (
	id serial PRIMARY KEY,
	username VARCHAR(20) NOTNULL,
	password VARCHAR(255) NOTNULL,
	fullname VARCHAR(30) NOTNULL,
	street VARCHAR(50) NOTNULL,
	city VARCHAR(50) NOTNULL,
	STATE VARCHAR(50) NOTNULL,
	zip VARCHAR(20) NOTNULL,
	phonenumber VARCHAR(20) NOTNULL
);


--alter table taco add foreign key (taco_order) references taco_order(id);
--alter table ingredient_ref add foreign key (ingredient) references ingredient(id);
alter table Taco
    add foreign key (taco_order) references Taco_Order(id);
alter table Ingredient_Ref
    add foreign key (ingredient) references Ingredient(id);