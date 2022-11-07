INSERT INTO roles(rolename) VALUES ('USER'), ('ADMIN');

INSERT INTO users (username_id, email, password)
SELECT 'BANAAN', 'admin@theexpowall.com', '$2a$10$7AnurydJYa2aC4nkTb./7uYkkfkS6TecyfFhzQwMlrKJNbL6qyPyi';

INSERT INTO users_roles (users_username_id, roles_rolename) VALUES ('BANAAN', 'ADMIN');


