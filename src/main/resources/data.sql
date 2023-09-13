INSERT INTO roles(name) VALUES
    ('ADMIN'),
    ('USER');
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