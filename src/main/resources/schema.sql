DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS authority;
DROP TABLE IF EXISTS user_authority;

CREATE TABLE member (
    mem_idx INT auto_increment PRIMARY KEY,
    mem_id VARCHAR(20) NOT NULL,
    mem_pw VARCHAR(200) NOT NULL,
    mem_name VARCHAR(20) NOT NULL,
    activated BOOLEAN NOT NULL,
    created_at DATETIME NOT NULL,
    recent_at DATETIME NOT NULL
);

CREATE TABLE authority
(
  authority_name VARCHAR(10) NOT NULL
);

CREATE TABLE user_authority
(
  mem_idx INT NOT NULL,
  authority_name VARCHAR(10) NOT NULL
);