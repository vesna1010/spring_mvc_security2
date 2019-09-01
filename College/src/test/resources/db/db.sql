delete from users_roles;
delete from users;
delete from exams;
delete from lectures;
delete from professors;
delete from students;
delete from subjects;
delete from study_programs;
delete from departments;

insert into departments(id, title, date_of_creation) values(1, 'Department A', '2017-07-10');
insert into departments(id, title, date_of_creation) values(2, 'Department B', '2017-07-11');

insert into study_programs(id, title, date_of_creation, duration_of_study, department_id)
values(1, 'Study Program A', '2017-07-10', 3, 1);

insert into study_programs(id, title, date_of_creation, duration_of_study, department_id)
values(2, 'Study Program B', '2017-07-11', 3, 2);

insert into study_programs(id, title, date_of_creation, duration_of_study, department_id)
values(3, 'Study Program C', '2017-07-10', 3, 1);

insert into subjects(id, title, study_program_id) values(1, 'Subject A', 1);
insert into subjects(id, title, study_program_id) values(2, 'Subject B', 2);
insert into subjects(id, title, study_program_id) values(3, 'Subject C', 1);

insert into students(id, full_name, father_name, date_of_birth, email, telephone, gender, image,
address_street, address_state, address_city, address_zip_code, date_of_entry, year_of_study, study_program_id)
values(1, 'Student NameA', 'Father NameA', '2000-10-12', 'studentA@gmail.com', '065-111-345', 'MALE', 
LOAD_FILE('C:\\ProgramData\\MySQL\\MySQL Server 5.7\\Uploads\\image.jpg'), 'Street A',
'State A', 'City A', '74000', '2017-09-01', 1, 1);

insert into students(id, full_name, father_name, date_of_birth, email, telephone, gender, image,
address_street, address_state, address_city, address_zip_code, date_of_entry, year_of_study, study_program_id)
values(2, 'Student NameB', 'Father NameB', '2000-10-13', 'studentB@gmail.com', '065-100-345', 'MALE', 
LOAD_FILE('C:\\ProgramData\\MySQL\\MySQL Server 5.7\\Uploads\\image.jpg'), 'Street B',
'State B', 'City B', '74000', '2017-09-01', 1, 2);

insert into students(id, full_name, father_name, date_of_birth, email, telephone, gender, image,
address_street, address_state, address_city, address_zip_code, date_of_entry, year_of_study, study_program_id)
values(3, 'Student NameC', 'Father NameC', '2000-10-14', 'studentC@gmail.com', '065-106-345', 'MALE', 
LOAD_FILE('C:\\ProgramData\\MySQL\\MySQL Server 5.7\\Uploads\\image.jpg'), 'Street C',
'State C', 'City C', '74000', '2017-09-01', 1, 1);

insert into professors(id, full_name, father_name, date_of_birth, email, telephone, gender, image,
address_street, address_state, address_city, address_zip_code, date_of_employment, title)
values(4, 'Professor NameA', 'Father NameA', '1972-12-12', 'professorA@gmail.com', '065-123-456', 'MALE', 
LOAD_FILE('C:\\ProgramData\\MySQL\\MySQL Server 5.7\\Uploads\\image.jpg'), 'Street A',
'State A', 'City A', '74000', '2017-09-01', 'Title A');

insert into professors(id, full_name, father_name, date_of_birth, email, telephone, gender, image,
address_street, address_state, address_city, address_zip_code, date_of_employment, title)
values(5, 'Professor NameB', 'Father NameB', '1955-12-20', 'professorB@gmail.com', '065-123-458', 'MALE', 
LOAD_FILE('C:\\ProgramData\\MySQL\\MySQL Server 5.7\\Uploads\\image.jpg'), 'Street B',
'State B', 'City B', '74000', '2017-09-01', 'Title B');

insert into professors(id, full_name, father_name, date_of_birth, email, telephone, gender, image,
address_street, address_state, address_city, address_zip_code, date_of_employment, title)
values(6, 'Professor NameC', 'Father NameC', '1978-09-12', 'professorC@gmail.com', '065-123-111', 'MALE', 
LOAD_FILE('C:\\ProgramData\\MySQL\\MySQL Server 5.7\\Uploads\\image.jpg'), 'Street C',
'State C', 'City C', '74000', '2017-09-01', 'Title C');

insert into lectures(professor_id, subject_id, hours) values(4, 1, 2);
insert into lectures(professor_id, subject_id, hours) values(4, 3, 3);
insert into lectures(professor_id, subject_id, hours) values(5, 1, 3);
insert into lectures(professor_id, subject_id, hours) values(5, 2, 3);

insert into exams(date, student_id, professor_id, subject_id, score) values('2018-02-01', 1, 4, 1, 9);
insert into exams(date, student_id, professor_id, subject_id, score) values('2018-02-02', 3, 4, 1, 8);
insert into exams(date, student_id, professor_id, subject_id, score) values('2018-02-01', 1, 4, 3, 8);
insert into exams(date, student_id, professor_id, subject_id, score) values('2018-02-01', 2, 5, 2, 8);

insert into users(id, name, email, enabled, password) values(1, 'Full NameA', 'userA@gmail.com', true,
'ad93cecacfd707a0ed4111b64acd76e244057b05790ae13a4db197e4bfabcd6b0a09d9c2ac2cd57b');

insert into users(id, name, email, enabled, password) values(2, 'Full NameB', 'userB@gmail.com', true,
'4d195095e4f25a4b62e4ea6f669860cddf95625f3c6b55f23ed0f4ec3f64541cdb738ebf993cd2f9');

insert into users(id, name, email, enabled, password) values(3, 'Full NameC', 'userC@gmail.com', true,
'2becf544ffe62f43a2c1d924fdbe4d1d5d0f21aad8fcb10caa398757291da5eb024d07089317c012');

insert into users_roles(user_id, role) value(1, 'PROFESSOR');
insert into users_roles(user_id, role) value(2, 'PROFESSOR');
insert into users_roles(user_id, role) value(2, 'USER');
insert into users_roles(user_id, role) value(3, 'ADMIN');

