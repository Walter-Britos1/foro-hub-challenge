CREATE TABLE responses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    deleted BIT(1) NOT NULL,
    date_creating DATETIME NOT NULL,
    message VARCHAR(255) NOT NULL,
    solutions BIT(1) NOT NULL,
    last_updating DATETIME NOT NULL,
    topic_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (topic_id) REFERENCES topics(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);