INSERT INTO users (id, name, email, "emailVerified", image, username)
VALUES (100001, 'Hans Müller', 'hans@test.com', null, null, 'Hansi'),
       (100002, 'Berta Roberts', 'berti@test.com', null, null, 'Berti');

INSERT INTO team (id, name, current_sprint_id, config_id, code, created_by, created_at, active)
VALUES (998, 'Team Alpha', null, null, 'ALF1', 100001, '2024-07-13 15:19:11.164000 +00:00', true),
       (999, 'Team Beta', null, null, 'BET2', 100001, '2024-07-13 15:20:22.162000 +00:00', true);

INSERT INTO team_member (id, user_id, team_id, role, active)
VALUES (990, 100001, 998, 'ADMIN', true);
INSERT INTO team_member (id, user_id, team_id, role, active)
VALUES (999, 100002, 999, 'MEMBER', true);

INSERT INTO team_config (id, happiness_survey, work_kind_survey, emotion_survey)
VALUES (1, true, true, true);

INSERT INTO work_kind (name)
VALUES ('Coding'),
       ('Meeting'),
       ('Daily Standup'),
       ('Planning'),
       ('Review'),
       ('Retrospective');

INSERT INTO work_kind (name, team_id)
VALUES ('Team Huddle', 998),
       ('Bowling', 998),
       ('Team Huddle', 999),
       ('Bowling', 999);

INSERT INTO emotion (name)
VALUES ('Angry'),
       ('Bored'),
       ('Busy'),
       ('Disappointed'),
       ('Energetic'),
       ('Exhausted'),
       ('Frustrated'),
       ('Excited'),
       ('Motivated'),
       ('Nervous'),
       ('Overwhelmed'),
       ('Pessimistic'),
       ('Relaxed'),
       ('Inspired'),
       ('Stressed');
