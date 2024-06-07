drop table if exists students;

create table students (
  `id` varchar(8) primary key,
  `name` varchar(64) not null
);
