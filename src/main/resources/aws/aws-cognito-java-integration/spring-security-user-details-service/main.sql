CREATE TABLE comp_users (
	username VARCHAR(50) NOT NULL,
	password VARCHAR(300) NOT NULL,
        authority VARCHAR(50) NOT NULL,
	enabled TINYINT NOT NULL,
	PRIMARY KEY (username)
);

INSERT INTO comp_users (username, password, authority, enabled) VALUES
  ('krishna', '$2a$10$nnu2.EBSnJUQZmOv5hbD8.3C8dlifeLi26AWpoKN31FqjNXrijQMq', 'ROLE_ADMIN', 1),
  ('surya', '$2a$10$DPzHnInANVY1utbuRfe0eOojtE02k23TGB5Q0L6mIHOBJQhKU7DTi', 'ROLE_USER', 1); 
  
  