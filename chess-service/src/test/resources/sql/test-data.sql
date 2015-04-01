INSERT INTO USERS (ID, EMAIL, PASSWORD, BIRTHDATE, TOKEN, USERACCESSGROUP_ID) VALUES
  (1, 'serdyukov@javamonkeys.com', '12345', null, '067e6162-3b6f-4ae2-a171-2470b63dff00', 1),
  (2, 'sirosh@javamonkeys.com', '12345', null, '54947df8-0e9e-4471-a2f9-9af509fb5889', 1),
  (3, 'filippov@javamonkeys.com', '12345', null, '', 2),
  (4, 'UpdateUser@javamonkeys.com', '12345', null, '', 2),
  (5, 'DeleteUser@javamonkeys.com', '12345', null, '', 2);

INSERT INTO USERACCESSGROUPS (ID, NAME, ISADMIN) VALUES
  (1, 'admin', TRUE),
  (2, 'user', FALSE),
  (3, 'groupForDelete', FALSE);

INSERT INTO GAMES (ID, MATCHDATE, AUTHOR_ID, WHITE_ID, BLACK_ID, RESULT, MOVETEXT) VALUES
  (1, null, 1, 2, 1, '0.5-0.5','1.e4 e5'),
  (2, null, 1, 1, 3, '','1.e4 e5'),
  (3, null, 2, 2, 3, '','1.e4 e5');
