create table if not exists job (
  id int identity primary key,
  url varchar,
  title varchar,
  salary varchar,
  company_name varchar,
  location varchar,
  create_date TIMESTAMP
)