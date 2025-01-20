CREATE TABLE topics (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
     status ENUM('OPEN', 'CLOSED', 'DELETED') NOT NULL,
     date_creation DATETIME NOT NULL,
     message VARCHAR(255) NOT NULL,
     title VARCHAR(255) NOT NULL,
     last_updating DATETIME NOT NULL,
     course_id BIGINT NOT NULL,
     user_id BIGINT NOT NULL,
     FOREIGN KEY (course_id) REFERENCES courses(id),
     FOREIGN KEY (user_id) REFERENCES users(id)
);