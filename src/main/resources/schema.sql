create table if not exists purchase(
    id serial primary key,
    status varchar
);

create table if not exists item(
    id serial primary key,
    name varchar,
    price numeric,
    discounted_price numeric,
    purchase integer references purchase
)