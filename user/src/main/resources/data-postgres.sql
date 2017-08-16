INSERT INTO platform_user_user (account_non_locked, email, enabled, password, phone, user_name, name) VALUES
  (TRUE, '15309861499@163.com', TRUE, '$2a$10$POTZsVX.VBAtJUtjYGJrc.L5OGsV8.13qS4ubw6bT9gj5ztc/sBgS', '15309861499',
   'admin', 'admin');
INSERT INTO platform_user_role (name, authority, level) VALUES ('超级管理员', 'ROLE_super_admin', 0);
INSERT INTO platform_user_role (name, authority, level) VALUES ('普通管理员', 'ROLE_admin', 1);
INSERT INTO platform_user_role (name, authority, level) VALUES ('一般用户', 'ROLE_common_user', 2);
INSERT INTO platform_user_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO platform_user_client (authorized_grant_types, client_name, client_id, client_secret, scoped, secret_required)
VALUES
  ('["client_credentials","password","authorization_code","refresh_token"]', '用户管理平台', 'user_admin_client', 'secret', TRUE, TRUE);
INSERT INTO platform_user_client (authorized_grant_types, client_name, client_id, client_secret, scoped, secret_required)
VALUES
  ('["client_credentials","password","authorization_code","refresh_token"]', '测试平台', 'test_client', 'secret', TRUE, TRUE);
INSERT INTO platform_user_client_role (name, authority, level) VALUES ('内部平台', 'internal_client', 0);
INSERT INTO platform_user_client_role (name, authority, level) VALUES ('外部平台', 'outer_client', 1);
INSERT INTO platform_user_client_role (name, authority, level) VALUES ('用户管理平台', 'user_admin_client', 2);
INSERT INTO platform_user_client_client_role (client_id, role_id) VALUES (1, 1);
INSERT INTO platform_user_client_client_role (client_id, role_id) VALUES (1, 3);
INSERT INTO platform_user_client_client_role (client_id, role_id) VALUES (2, 1);
INSERT INTO platform_user_resource_scope (name, scope_id) VALUES ('内部应用', 'internal_client');
INSERT INTO platform_user_resource_scope (name, scope_id) VALUES ('外部应用', 'outer_client');
INSERT INTO platform_user_client_resource_scope (client_id, scope_id, auto_approve) VALUES ('1', '1', TRUE);
INSERT INTO platform_user_client_resource_scope (client_id, scope_id, auto_approve) VALUES ('2', '1', FALSE);



