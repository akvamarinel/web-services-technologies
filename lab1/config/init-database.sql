CREATE TABLE users
  (
      id            BIGSERIAL PRIMARY KEY,
      name          VARCHAR(255),
      age           INTEGER,
      city          VARCHAR(255),
      average_scale REAL
  );

INSERT INTO users (name, age, city, average_scale) VALUES ('mashusik', 18, 'spb', 5);