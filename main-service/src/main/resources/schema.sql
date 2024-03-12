DROP TABLE IF EXISTS participation_requests;
DROP TABLE IF EXISTS compilation_content;
DROP TABLE IF EXISTS compilations;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS categories;

CREATE TABLE IF NOT EXISTS users(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR NOT NULL,
    email VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS categories(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS events (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR NOT NULL UNIQUE,
    annotation VARCHAR NOT NULL,
    category_id BIGINT REFERENCES categories(id),
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    description VARCHAR NOT NULL,
    event_date TIMESTAMP NOT NULL,
    initiator_id BIGINT REFERENCES users(id),
    location_lat FLOAT,
    location_lon FLOAT,
    paid BOOLEAN NOT NULL,
    participant_limit INTEGER DEFAULT(0),
    published_on TIMESTAMP,
    request_moderation BOOLEAN NOT NULL,
    state VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS participation_requests (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    event_id BIGINT REFERENCES events(id),
    requester_id BIGINT REFERENCES users(id),
    status VARCHAR NOT NULL,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS compilations (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pinned BOOLEAN NOT NULL,
    title VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS compilation_content (
    compilation_id BIGINT REFERENCES compilations(id),
    event_id BIGINT REFERENCES events(id),
    PRIMARY KEY(compilation_id, event_id)
);