CREATE DATABASE bugtracker;
USE bugtracker;

-- users table
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100),
    role VARCHAR(20)
);


-- issues table
CREATE TABLE issues (
    issue_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200),
    description TEXT,
    priority VARCHAR(10),
    status VARCHAR(20),
    created_by INT,
    assigned_to INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users(user_id),
    FOREIGN KEY (assigned_to) REFERENCES users(user_id)
);


-- comments table
CREATE TABLE comments (
    comment_id INT PRIMARY KEY AUTO_INCREMENT,
    issue_id INT,
    user_id INT,
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (issue_id) REFERENCES issues(issue_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- insert users
INSERT INTO users(name, email, password, role)
VALUES 
('Emily Johnson', 'emily.johnaon@gmail.com', '1234', 'tester'),
('Michael Brown', 'michael.brown@gmail.com', '1234', 'admin'),
('Joe', 'joe.goldberg@gmail.com', '1234', 'developer');

-- insert issues
INSERT INTO issues(title, description, priority, status, created_by, assigned_to)
VALUES 
('Login Bug', 'Login button not working', 'high', 'open', 2, 1);

-- display data
SELECT * FROM users;
SELECT * FROM issues;
SELECT * FROM comments;

-- Retrieves issue details along with the names of the users who created and are assigned to each issue using JOIN
SELECT i.issue_id, i.title, i.priority, i.status,
u1.name AS created_by,
u2.name AS assigned_to
FROM issues i
JOIN users u1 ON i.created_by = u1.user_id
JOIN users u2 ON i.assigned_to = u2.user_id;

-- Retrieves comments along with the name of the user who posted each comment using JOIN
SELECT c.comment_id, c.comment, u.name, c.issue_id
FROM comments c
JOIN users u ON c.user_id = u.user_id;