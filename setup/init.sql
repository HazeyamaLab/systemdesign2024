-- `students`テーブルが既に存在している場合は削除する。
drop table if exists students;

-- `students`テーブルを作成する。
create table students (
  `id` varchar(8) primary key,
  `name` varchar(64) not null
);
