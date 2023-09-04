INSERT INTO roles(name) VALUES
    ('ADMIN'),
    ('USER');
INSERT INTO users(name,password,username,point,email) VALUES
    ('Admin','$2a$10$Hl9oZlg/jt5vJxdtleGBken/JzaUArI9YLJDx4qErfZpJKbldTye.','admin',1000000,'admin@admin'),
    ('Justin','$2a$10$G96QyvPFhm6USBQabeCeEecMmGfUjG2KamkKgttozWA8Y7qnoV9B.','user',1000,'user@user');
INSERT INTO users_roles(user_id,role_id) VALUES
    (1,1),
    (1,2),
    (2,2);
INSERT INTO teams(city,name,stadium) VALUES
    ('Milan','Inter Milan','San Siro'),
    ('London','Chelsea','Stamford Bridge'),
    ('Manchester','Manchester United','Old Trafford'),
    ('Barcelona','Barcelona','Camp Nou'),
    ('Madrid','Real Madrid','Santiago Bernabeu'),
    ('Munich','Bayern Munich','Allianz Arena');
INSERT INTO matches(name,team1_id,team2_id,team1_score,team2_score) VALUES
    ('2012 UCL final',2,6,0,0),
    ('2023 EPL matchday 10',2,3,0,0),
    ('2023 UEL semi-final 1',1,4,0,0),
    ('2023 LaLiga matchday 3',4,5,0,0);