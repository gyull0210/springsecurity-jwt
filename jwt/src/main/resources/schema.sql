DROP TABLE IF EXISTS Member;

CREATE TABLE member 
(
  mem_idx IDENTIFY PRIMARY KEY
  mem_id VARCHAR(20) NOT NULL
  mem_pw VARCHAR(20) NOT NULL
  mem_name VARCHAR(20) NOT NULL
  is_activated BOOLEAN NOT NULL
  created_at DATETIME NOT NULL
  recent_at DATETIME NOT NULL
);

CREATE TABLE authority
(
  authority_name VARCHAR(10) NOT NULL
);

CREATE TABLE user_authority
(
  mem_idx INT NOT NULL
  authority_name VARCHAR(10) NOT NULL
);