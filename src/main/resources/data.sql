CREATE TABLE IF NOT EXISTS tb_roles (
    role_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
    );

INSERT INTO tb_roles (role_id, name) VALUES (1, 'admin')
    ON CONFLICT (role_id) DO NOTHING;

INSERT INTO tb_roles (role_id, name) VALUES (2, 'basic')
    ON CONFLICT (role_id) DO NOTHING;

CREATE TABLE IF NOT EXISTS tb_users (
                                        user_id UUID PRIMARY KEY,
                                        username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS tb_users_roles (
                                              user_id UUID NOT NULL,
                                              role_id BIGINT NOT NULL,
                                              PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES tb_users(user_id),
    FOREIGN KEY (role_id) REFERENCES tb_roles(role_id)
    );

INSERT INTO tb_users (user_id, username, password)
SELECT gen_random_uuid(), 'admin', '$2a$10$RjW0Srs90A4NeUbN3lsCiu4O9hyEsUeZ2VNbeUBKldjH72frH0ktm'
WHERE NOT EXISTS (SELECT 1 FROM tb_users WHERE username = 'admin');