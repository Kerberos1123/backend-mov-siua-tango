
/** Priority **/
INSERT INTO public.priority (id, label) VALUES (1, 'High');
INSERT INTO public.priority (id, label) VALUES (2, 'Medium');
INSERT INTO public.priority (id, label) VALUES (3, 'Low');

/** Status **/
INSERT INTO public.status (id, label) VALUES (1, 'Pending');
INSERT INTO public.status (id, label) VALUES (2, 'In Progress');
INSERT INTO public.status(id, label) VALUES (3, 'Done');

/** Privilege **/
INSERT INTO public.privilege (id, name) VALUES (1, 'WRITE_PRIVILEGE');
INSERT INTO public.privilege (id, name) VALUES (2, 'READ_PRIVILEGE');

/** Role **/
INSERT INTO public.role (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.role (id, name) VALUES (2, 'ROLE_STAFF');
INSERT INTO public.role (id, name) VALUES (3, 'ROLE_USER');

/** Role-Privilege **/
INSERT INTO public.role_privilege (role_id, privilege_id) VALUES (1, 1);
INSERT INTO public.role_privilege (role_id, privilege_id) VALUES (1, 2);
INSERT INTO public.role_privilege (role_id, privilege_id) VALUES (2, 2);
INSERT INTO public.role_privilege (role_id, privilege_id) VALUES (3, 2);

/** Users **/
INSERT INTO public.users (id, create_date, email, enabled, first_name, last_name, password, token_expired) VALUES (1, '2020-10-10 18:23:52.000000', 'admin@gmail.com', true, 'Daniel', 'Vargas', '$2a$10$6hg/QTw8Th1EmYtg9/5HhOmRdg320A6J8DumNUqx.4AltXZAjp0VK', false);
INSERT INTO public.users (id, create_date, email, enabled, first_name, last_name, password, token_expired) VALUES (2, '2022-05-17 08:23:25.000000', 'staff@gmail.com', true, 'Jonathan', 'Aragon', '$2a$10$6hg/QTw8Th1EmYtg9/5HhOmRdg320A6J8DumNUqx.4AltXZAjp0VK', false);
INSERT INTO public.users (id, create_date, email, enabled, first_name, last_name, password, token_expired) VALUES (3, '2022-02-08 08:24:48.000000', 'user@gmail.com', true, 'Jose', 'Valverde', '$2a$10$6hg/QTw8Th1EmYtg9/5HhOmRdg320A6J8DumNUqx.4AltXZAjp0VK', false);

/** User-Role **/
INSERT INTO public.user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO public.user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO public.user_role (user_id, role_id) VALUES (3, 3);

/** Tasks **/
INSERT INTO public.task (id, create_date, due_date, notes, title, priority_id, status_id, user_id) VALUES (1, '2020-08-24', '2020-09-15', 'Different Notes', 'Evaluate Students', 1, 1, 1);
INSERT INTO public.task (id, create_date, due_date, notes, title, priority_id, status_id, user_id) VALUES (2, '2020-08-24', '2020-08-27', 'More Notes', 'Coordinate with professors', 2, 1, 1);
ALTER SEQUENCE hibernate_sequence RESTART WITH 3;

/** Classes **/
INSERT INTO public.class (id, name, id_classroom,id_teacher) VALUES (1,'Programacion IV',0,2);
INSERT INTO public.class (id, name, id_classroom,id_teacher) VALUES (2,'Calculo 1',2,1);


/**Asset Type**/
INSERT INTO public.asset_type(name, id) VALUES ('Computadora',001);
INSERT INTO public.asset_type(name, id) VALUES ('Proyector',002);

/**Ticket Reason**/
INSERT INTO public.ticket_reason(name, id) VALUES ('Material perdido',01);
INSERT INTO public.ticket_reason(name, id) VALUES ('Material con daños',02);

/** Tickets **/
INSERT INTO public.ticket(id,user_id,asset_type_id,ticket_reason_id,detail) VALUES (1,1,001,02, 'Screen blacks out at times');
