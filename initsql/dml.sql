##Username: admin, Password: 12345

INSERT INTO login_user
(id, created_by, is_active, is_logical_deleted, modified_by, comments, company, department, disabled, email, failed_count, first_name, last_login_ip, last_name, password, phone, salute, sex, user_name, version)
VALUES(0, 'sys', 1, 0, 'sys', '', '', '', 0, '', 0, '', '', '', '827CCB0EEA8A706C4C34A16891F84E7B', '', '', '', 'admin', 0);

INSERT INTO user_role
(id, created_by, is_active, is_logical_deleted, modified_by, role_id, role_name, user_id)
VALUES(0, 'sys', 1, 0, '', 0, 'admin', 0);

INSERT INTO user_role
(id, created_by, is_active, is_logical_deleted, modified_by, role_id, role_name, user_id)
VALUES(1, 'sys', 1, 0, '', 0, 'user', 0);

commit;