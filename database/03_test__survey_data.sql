-- -- DEAR-98 add happiness survey data
-- -- User id = 1
--
-- DO $$
--     DECLARE
--         start_date DATE := '2024-07-07';
--         end_date DATE := CURRENT_DATE;
--         current_date DATE;
--         user_id INTEGER := 1;
--         num_entries INTEGER;
--         i INTEGER;
--         scores INTEGER[] := ARRAY[2, 8, 14, 20];
--         score INTEGER;
--     BEGIN
--         -- Loop through each date in the range
--                 current_date := start_date;
--         WHILE current_date <= end_date LOOP
--                 -- Randomly decide the number of entries for the current date (0 to 6)
--                 num_entries := floor(random() * 2)::INTEGER;
--
--                 RAISE NOTICE 'Date: %, Entries: %', current_date, num_entries;
--
--                 -- Insert the entries for the current date at the same time
--                 FOR i IN 1..num_entries LOOP
--                         score := scores[floor(random() * 4)::INTEGER + 1];
--                         INSERT INTO happiness_survey (user_id, submitted, score)
--                         VALUES (user_id, current_date + interval '12 hours', score);
--                     END LOOP;
--
--                 -- Move to the next date
--                         current_date := current_date + interval '1 day';
--             END LOOP;
--     END $$;

-- DEAR-98 add happiness survey data

INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-01 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-02 12:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-03 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-04 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-05 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-06 12:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-07 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-08 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-09 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-10 12:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-11 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-12 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-13 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-14 12:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-15 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-16 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-17 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-18 12:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-19 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-20 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-21 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-22 12:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-23 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-24 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-25 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-26 12:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-27 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-28 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-29 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-30 12:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-03-31 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-01 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-02 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-03 12:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-04 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-05 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-06 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-07 12:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-08 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-09 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-10 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-11 12:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-12 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-13 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-14 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-15 12:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-16 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-17 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-18 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-18 15:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-19 12:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-20 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-21 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-22 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-22 17:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-23 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-24 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-24 18:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-25 12:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-25 15:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-26 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-27 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-27 16:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-28 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-29 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-30 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-04-30 18:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-01 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-02 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-03 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-03 17:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-04 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-05 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-06 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-06 15:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-07 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-08 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-09 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-09 17:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-10 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-11 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-12 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-12 18:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-13 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-14 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-15 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-15 15:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-16 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-17 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-18 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-18 18:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-19 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-20 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-21 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-21 17:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-22 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (1, '2024-05-23 12:00:00', 20);

--user id = 100002
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-05-24 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-05-24 15:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-05-25 12:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-05-26 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-05-27 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-05-28 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-05-28 17:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-05-29 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-05-30 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-05-30 18:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-05-31 12:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-05-31 15:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-01 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-02 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-02 16:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-03 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-04 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-05 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-05 18:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-06 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-07 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-08 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-08 17:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-09 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-10 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-11 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-11 15:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-12 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-13 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-14 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-14 17:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-15 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-16 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-17 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-17 18:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-18 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-19 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-20 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-20 15:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-21 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-22 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-23 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-23 18:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-24 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-25 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-26 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-26 15:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-27 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-28 12:00:00', 20);

-- last weeks
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (2, '2024-06-14 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (2, '2024-06-14 17:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (2, '2024-06-15 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (2, '2024-06-16 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (2, '2024-06-17 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (2, '2024-06-17 18:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (2, '2024-06-18 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (2, '2024-06-19 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (2, '2024-06-20 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (2, '2024-06-20 15:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (2, '2024-06-21 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (2, '2024-06-22 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (2, '2024-06-23 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (2, '2024-06-23 18:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (2, '2024-06-24 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (2, '2024-06-25 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (2, '2024-06-26 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (2, '2024-06-26 15:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (2, '2024-06-27 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (2, '2024-06-28 12:00:00', 20);

INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-14 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-14 17:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-15 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-16 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-17 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-17 18:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-18 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-19 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-20 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-20 15:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-21 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-22 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-23 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-23 18:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-24 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-25 12:00:00', 20);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-26 12:00:00', 2);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-26 15:00:00', 8);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-27 12:00:00', 14);
INSERT INTO happiness_survey (user_id, submitted, score) VALUES (100002, '2024-06-28 12:00:00', 20);
