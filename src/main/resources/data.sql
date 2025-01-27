CREATE TABLE IF NOT EXISTS tb_roles (
    role_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
    );

INSERT INTO tb_roles (role_id, name) VALUES (1, 'admin')
    ON CONFLICT (role_id) DO NOTHING;

INSERT INTO tb_roles (role_id, name) VALUES (2, 'basic')
    ON CONFLICT (role_id) DO NOTHING;
