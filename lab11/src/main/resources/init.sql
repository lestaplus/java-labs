create table if not exists department
(
    department_id int generated always as identity primary key,
    department_name char(30) not null,
    phone_number char(15)
);

create table if not exists employee
(
    employee_id int generated always as identity primary key,
    first_name varchar(10) not null,
    last_name varchar(20) not null,
    job_title varchar(20),
    department_id int not null references department(department_id)
);

create table if not exists task
(
    task_id int generated always as identity primary key,
    description varchar(50) not null,
    employee_id int not null references employee(employee_id) on delete cascade
);

truncate table task, employee, department restart identity cascade;

insert into department (department_name, phone_number)
values
    ('Відділ розробки', '+380441112233'),
    ('Відділ тестування', '+380442223344'),
    ('Відділ кадрів', null);

insert into employee (first_name, last_name, job_title, department_id)
values
    ('Олександр', 'Коваленко', 'Backend Developer', 1),
    ('Ірина', 'Мельник', 'Frontend Developer', 1),
    ('Максим', 'Шевченко', 'QA Engineer', 2),
    ('Олена', 'Бондаренко', null, 3);

insert into task (description, employee_id)
values
    ('Налаштувати БД PostgreSQL', 1),
    ('Реалізувати REST API', 1),
    ('Зверстати головну сторінку', 2),
    ('Написати інтеграційні тести', 3),
    ('Перевірити релізну збірку', 3);