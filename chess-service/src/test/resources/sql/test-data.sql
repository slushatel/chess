INSERT INTO USERS (ID, EMAIL, PASSWORD, BIRTHDATE, USERACCESSGROUP_ID) VALUES
  (1, 'serdyukov@javamonkeys.com', '12345', null, 1),
  (2, 'sirosh@javamonkeys.com', '12345', null, 1),
  (3, 'filippov@javamonkeys.com', '12345', null, 2);

INSERT INTO USERACCESSGROUPS (ID, NAME, ISADMIN) VALUES
  (1, 'admin', TRUE),
  (2, 'user', FALSE);