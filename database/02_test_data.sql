INSERT INTO public.users (id, name, email, "emailVerified", image, username)
VALUES (1, 'Hans Müller', 'hans@test.com', null, null, 'Hansi');

INSERT INTO public.team (id, name, current_sprint_id, config_id, code, created_by, created_at, active)
VALUES (1, 'Team Alpha', null, null, 'ALF1', 1, '2024-07-13 15:19:11.164000 +00:00', true);
INSERT INTO public.team (id, name, current_sprint_id, config_id, code, created_by, created_at, active)
VALUES (2, 'Team Beta', null, null, 'BET2', 1, '2024-07-13 15:20:22.162000 +00:00', true);

INSERT INTO public.team_member (id, user_id, team_id, role, active)
VALUES (1, 1, 1, 'ADMIN', true);
INSERT INTO public.team_member (id, user_id, team_id, role, active)
VALUES (2, 1, 2, 'MEMBER', true);

INSERT INTO public.team_config (id, work_kinds)
VALUES (1, '{CODING,MEETING}');
