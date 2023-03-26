
INSERT INTO users (user_id, name, password, email, role)
VALUES (1, 'admin', '{bcrypt}$2a$10$TNYmoKQTIcJwUVb67pQEF./SoEqJYHqFK1zkX9Q4DbHQmZiQCIlKC','admin@gmail.com',  'ADMIN');

ALTER SEQUENCE user_seq RESTART WITH 2;
