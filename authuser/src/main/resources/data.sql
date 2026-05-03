INSERT INTO tb_roles (role_id, role_name) VALUES (gen_random_uuid(), 'ROLE_USER') ON CONFLICT (role_name) DO NOTHING;
INSERT INTO tb_roles (role_id, role_name) VALUES (gen_random_uuid(), 'ROLE_STUDENT') ON CONFLICT (role_name) DO NOTHING;
INSERT INTO tb_roles (role_id, role_name) VALUES (gen_random_uuid(), 'ROLE_INSTRUCTOR') ON CONFLICT (role_name) DO NOTHING;
INSERT INTO tb_roles (role_id, role_name) VALUES (gen_random_uuid(), 'ROLE_ADMIN') ON CONFLICT (role_name) DO NOTHING;
