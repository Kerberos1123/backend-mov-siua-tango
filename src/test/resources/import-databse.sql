
/** Priority **/
INSERT INTO public.priority (id, label) VALUES (1, 'High');
INSERT INTO public.priority (id, label) VALUES (2, 'Medium');
INSERT INTO public.priority (id, label) VALUES (3, 'Low');

/** Status **/
INSERT INTO public.status (id, label) VALUES (1, 'Pending');
INSERT INTO public.status (id, label) VALUES (2, 'In Progress');
INSERT INTO public.status (id, label) VALUES (3, 'Done');

/** Privilege **/
INSERT INTO public.privilege (id, name) VALUES (1, 'WRITE_PRIVILEGE');
INSERT INTO public.privilege (id, name) VALUES (2, 'READ_PRIVILEGE');



/** Role **/
INSERT INTO public.role (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.role (id, name) VALUES (2, 'ROLE_PROF');
INSERT INTO public.role (id, name) VALUES (3, 'ROLE_USER');

/** Tasks **/
INSERT INTO public.task (id, create_date, due_date, notes, title, priority_id, status_id, user_id) VALUES (1, '2020-08-24', '2020-09-15', 'Different Notes', 'Evaluate Students', 1, 1, 1);
INSERT INTO public.task (id, create_date, due_date, notes, title, priority_id, status_id, user_id) VALUES (2, '2020-08-24', '2020-08-27', 'More Notes', 'Coordinate with professors', 2, 1, 1);
ALTER SEQUENCE hibernate_sequence RESTART WITH 3;

/*

/** Role-Privilege **/
INSERT INTO public.role_privilege (role_id, privilege_id) VALUES (1, 1); /*Admin puede hacer read y write*/
INSERT INTO public.role_privilege (role_id, privilege_id) VALUES (1, 2);
INSERT INTO public.role_privilege (role_id, privilege_id) VALUES (2, 2);/*Prof solo read*/
INSERT INTO public.role_privilege (role_id, privilege_id) VALUES (3, 2);/*User solo read*/


/** Users **/
INSERT INTO public.users (id, create_date, email, enabled, first_name, last_name, password, token_expired) VALUES (1, '2020-08-30 18:23:52.000000', 'admin@guzmanalan.com', true, 'Maikol', 'Guzman', '$2a$10$6hg/QTw8Th1EmYtg9/5HhOmRdg320A6J8DumNUqx.4AltXZAjp0VK', false);
INSERT INTO public.users (id, create_date, email, enabled, first_name, last_name, password, token_expired) VALUES (2, '2022-04-16 08:23:25.000000', 'staff@guzmanalan.com', true, 'Staff', 'Guzman', '$2a$10$6hg/QTw8Th1EmYtg9/5HhOmRdg320A6J8DumNUqx.4AltXZAjp0VK', false);
INSERT INTO public.users (id, create_date, email, enabled, first_name, last_name, password, token_expired) VALUES (3, '2022-04-16 08:24:48.000000', 'user@guzmanalan.com', true, 'User', 'Guzman', '$2a$10$6hg/QTw8Th1EmYtg9/5HhOmRdg320A6J8DumNUqx.4AltXZAjp0VK', false);


*/