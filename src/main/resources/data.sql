INSERT INTO roles(rolename) VALUES ('USER'), ('ADMIN');

INSERT INTO users (username_id, email, password, accountid)
SELECT 'SUPER-USER', 'admin@theexpowall.com', '$2a$10$7AnurydJYa2aC4nkTb./7uYkkfkS6TecyfFhzQwMlrKJNbL6qyPyi', '0';

INSERT INTO users_roles (users_username_id, roles_rolename) VALUES ('SUPER-USER', 'ADMIN');

INSERT INTO accounts (account_id, dob, first_name, last_name)
SELECT '0', '1990-01-01', 'SUPER', 'USER';
