-- Creat tables for PG-Adapter Auth.js
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE verification_token
(
    identifier TEXT        NOT NULL,
    expires    TIMESTAMPTZ NOT NULL,
    token      TEXT        NOT NULL,

    PRIMARY KEY (identifier, token)
);

CREATE TABLE accounts
(
    id                  SERIAL,
    "userId"            INTEGER      NOT NULL,
    type                VARCHAR(255) NOT NULL,
    provider            VARCHAR(255) NOT NULL,
    "providerAccountId" VARCHAR(255) NOT NULL,
    refresh_token       TEXT,
    access_token        TEXT,
    expires_at          BIGINT,
    id_token            TEXT,
    scope               TEXT,
    session_state       TEXT,
    token_type          TEXT,

    PRIMARY KEY (id)
);

CREATE TABLE users
(
    id              SERIAL,
    name            VARCHAR(255),
    email           VARCHAR(255),
    "emailVerified" TIMESTAMPTZ,
    image           TEXT,
    github_user_name VARCHAR(255),

    PRIMARY KEY (id)
);

ALTER TABLE users
    ADD COLUMN username VARCHAR(32);

-- Create tables for DEAR-115-TEAM
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE team
(
    id                SERIAL,
    name              VARCHAR(32),
    current_sprint_id INTEGER,
    config_id         INTEGER,
    code              VARCHAR(4)           NOT NULL,
    created_by        INTEGER              NOT NULL,
    created_at        TIMESTAMPTZ          NOT NULL,
    active            BOOLEAN DEFAULT TRUE NOT NULL,

    CONSTRAINT pk_team PRIMARY KEY (id)
);

CREATE TABLE team_member
(
    id        SERIAL,
    user_id   INTEGER              NOT NULL,
    team_id   INTEGER              NOT NULL,
    joined_at TIMESTAMPTZ,
    role      VARCHAR(8)           NOT NULL,
    active    BOOLEAN DEFAULT TRUE NOT NULL,

    CONSTRAINT pk_team_member PRIMARY KEY (id),
    CONSTRAINT fk_team_member_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_team_member_team FOREIGN KEY (team_id) REFERENCES team (id),
    CONSTRAINT uq_team_member UNIQUE (user_id, team_id, role)
);

CREATE TABLE team_config
(
    id               SERIAL,
    happiness_survey BOOLEAN,
    work_kind_survey BOOLEAN,
    emotion_survey   BOOLEAN,

    PRIMARY KEY (id)
);

CREATE TABLE sprint_config
(
    id          SERIAL,
    sprint_name VARCHAR(32) NOT NULL,
    sprint_goal VARCHAR(255),
    team_id     INTEGER,
    start_date  DATE        NOT NULL,
    end_date    DATE        NOT NULL,
    created_at  TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    created_by  INTEGER     NOT NULL,
    status VARCHAR(20) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE team_config_work_kinds
(
    team_config_id INTEGER NOT NULL,
    work_kind_id   INTEGER NOT NULL,

    FOREIGN KEY (team_config_id) REFERENCES team_config (id)
);

-- Add constraints to the tables
ALTER TABLE team
    ADD CONSTRAINT fk_team_team_config FOREIGN KEY (config_id) REFERENCES team_config (id);

-- Create tables for DEAR-120-SURVEY DATA
------------------------------------------------------------------------------------------------------------------------

CREATE TABLE work_kind
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    team_id INTEGER,

    CONSTRAINT fk_team FOREIGN KEY (team_id) REFERENCES team (id)
);

CREATE TABLE emotion
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE happiness_survey
(
    id        SERIAL PRIMARY KEY,
    user_id   INTEGER NOT NULL,
    submitted TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    score     INTEGER NOT NULL,

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE work_kind_survey
(
    id           SERIAL PRIMARY KEY,
    user_id      INTEGER NOT NULL,
    submitted    TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    score        INTEGER NOT NULL,
    work_kind_id INTEGER NOT NULL,

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_work_kind FOREIGN KEY (work_kind_id) REFERENCES work_kind (id)
);

CREATE TABLE emotion_survey
(
    id         SERIAL PRIMARY KEY,
    user_id    INTEGER NOT NULL,
    submitted  TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    emotion_id INTEGER NOT NULL,

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_emotion FOREIGN KEY (emotion_id) REFERENCES emotion (id)
);
