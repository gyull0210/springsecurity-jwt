-- insert into member (mem_id, mem_pw, mem_name, activated, created_at, recent_at) values ('admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 1, now(), now());
-- insert into member (mem_id, mem_pw, mem_name, activated, created_at, recent_at) values ('user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 1, now(), now());
insert into member (mem_id, mem_pw, mem_name, activated, created_at, recent_at) values ('admin', '$argon2id$v=19$m=65536,t=3,p=4$9w3pbF/+/sWPtwDaVQJJtQ$1DAFSQ4OhKFfvWX/SAHQwUN0MuXIqGWFrNE/w9JsxHE', 'admin', 1, now(), now());
insert into member (mem_id, mem_pw, mem_name, activated, created_at, recent_at) values ('user', '$argon2id$v=19$m=65536,t=3,p=4$0fM/7dz8G4rF9HuqIZHHiQ$Ku99SzrWGdGhAzGHZ/SAqIcjDMaYvzst5zCVrpzXBGA', 'user', 1, now(), now());

insert into authority (authority_name) values ('ROLE_USER');
insert into authority (authority_name) values ('ROLE_ADMIN');

insert into user_authority (mem_idx, authority_name) values (1, 'ROLE_USER');
insert into user_authority (mem_idx, authority_name) values (1, 'ROLE_ADMIN');
insert into user_authority (mem_idx, authority_name) values (2, 'ROLE_USER');