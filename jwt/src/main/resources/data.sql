INSERT INTO member
( mem_id, mem_pw, mem_name, is_activated, created_at, recent_at) 
values ('admin', '$argon2id$v=19$m=65536,t=3,p=4$Oq62zKqO4W2sfnRjW1lRKg$vTzt6XsXVUW+PXJp5IaFJw== ', '김아무개',  1, now(),  now());

INSERT INTO member ( mem_id, mem_pw, mem_name, is_activated, created_at, recent_at ) 
VALUES ('user', '$argon2id$v=19$m=65536,t=3,p=4$euX7KO7p9PzUE0c1e5NlQg$r1L0NQGXqJw0+LxhT3P1Vw==', '박아무개', '1', now(), now());

INSERT INTO authority (authority_name) VALUES ('ROLE_USER');
INSERT INTO authority (authority_name) VALUES ('ROLE_ADMIN');

insert into user_authority (mem_idx, authority_name) values (1, 'ROLE_USER');
insert into user_authority (mem_idx, authority_name) values (1, 'ROLE_ADMIN');
insert into user_authority (mem_idx, authority_name) values (2, 'ROLE_USER');