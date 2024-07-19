INSERT INTO public.users (id, name, email, "emailVerified", image, username)
VALUES (1, 'Hans Müller', 'hans@test.com', null, null, 'Hansi'),
       (2, 'Berta Roberts', 'berti@test.com', null, null, 'Berti');

INSERT INTO public.team (id, name, current_sprint_id, config_id, code, created_by, created_at, active)
VALUES (1, 'Team Alpha', null, null, 'ALF1', 1, '2024-07-13 15:19:11.164000 +00:00', true);
INSERT INTO public.team (id, name, current_sprint_id, config_id, code, created_by, created_at, active)
VALUES (2, 'Team Beta', null, null, 'BET2', 1, '2024-07-13 15:20:22.162000 +00:00', true);

INSERT INTO public.team_member (id, user_id, team_id, role, active)
VALUES (1, 1, 1, 'ADMIN', true);
INSERT INTO public.team_member (id, user_id, team_id, role, active)
VALUES (2, 2, 2, 'MEMBER', true);

INSERT INTO public.team_config (id, work_kinds)
VALUES (1, '{CODING,MEETING}');

INSERT INTO work_kind (name)
VALUES ('Coding'),
       ('Meeting'),
       ('Daily Standup'),
       ('Planning'),
       ('Review'),
       ('Retrospective');
INSERT INTO work_kind (name, team_id)
VALUES ('Team Huddle', 1),
       ('Bowling', 2);
INSERT INTO emotion (name)
VALUES ('Relaxed'),
       ('Frustrated'),
       ('Busy'),
       ('Stressed'),
       ('Exhausted'),
       ('Sick');
INSERT INTO happiness_survey (user_id, score)
VALUES (1, 4),
       (1, 2),
       (1, 3),
       (2, 1);
INSERT INTO work_kind_survey (user_id, score, work_kind_id)
VALUES (1, 6, 1),
       (2, 7, 2);
INSERT INTO emotion_survey (user_id, score, emotion_id)
VALUES (1, 9, 1),
       (2, 5, 2);
