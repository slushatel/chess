INSERT INTO USERS (ID, EMAIL, PASSWORD, BIRTHDATE, USERACCESSGROUP_ID) VALUES
  (1, 'serdyukov@javamonkeys.com', '12345', null, 1),
  (2, 'sirosh@javamonkeys.com', '12345', null, 1),
  (3, 'filippov@javamonkeys.com', '12345', null, 2);

INSERT INTO USERACCESSGROUPS (ID, NAME, ISADMIN) VALUES
  (1, 'admin', TRUE),
  (2, 'user', FALSE);

INSERT INTO GAMES (ID, MATCHDATE, AUTHOR_ID, WHITE_ID, BLACK_ID, RESULT, MOVETEXT) VALUES
  (1, null, 1, 2, 1, '0.5-0.5','1.e4 e5'),
  (2, null, 1, 1, 3, '','1.e4 e5'),
  (3, null, 2, 2, 3, '','1.e4 e5');
