CREATE TABLE IF NOT EXISTS tb_users (
    user_id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

INSERT INTO tb_users (user_id, username, password)
SELECT gen_random_uuid(), 'admin', '$2a$10$RjW0Srs90A4NeUbN3lsCiu4O9hyEsUeZ2VNbeUBKldjH72frH0ktm'
WHERE NOT EXISTS (SELECT 1 FROM tb_users WHERE username = 'admin');