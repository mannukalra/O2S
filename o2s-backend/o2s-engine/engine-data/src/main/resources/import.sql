INSERT INTO known_fruits(id, name) VALUES (1, 'Cherry');
INSERT INTO known_fruits(id, name) VALUES (2, 'Apple');
INSERT INTO known_fruits(id, name) VALUES (3, 'Banana');
ALTER SEQUENCE known_fruits_id_seq RESTART WITH 4;

insert into Environment(id, name) values (1, 'DevEnv');
insert into Environment(id, name) values (2, 'TestEnv');
ALTER SEQUENCE env_id_seq RESTART WITH 3;

insert into Device(host, alias, env_id) values ('WindowsH', 'windows', 1);
insert into Device(host, alias, env_id) values ('LinuxH', 'linux', 1);
insert into Device(host, alias, env_id) values ('UnixH', 'unix', 2);
