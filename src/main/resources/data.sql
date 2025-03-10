-- Members Table
CREATE TABLE members (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         email VARCHAR(255) NOT NULL UNIQUE,
                         password VARCHAR(255) NOT NULL,
                         nickname VARCHAR(50),
                         phone VARCHAR(20),
                         address VARCHAR(255),
                         terms_agreed BOOLEAN NOT NULL
);

INSERT INTO members (email, password, nickname, phone, address, terms_agreed)
VALUES
    ('test@example.com', '$2a$10$E8aTftVnZLLoN3F2ZBOOPOLnrw4Up.dJAwIo.zH6SEPVHSLlfyVXi', 'TestUser', '010-1234-5678', '서울시 강남구', true);

-- Inquiry Table
CREATE TABLE inquiry (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         title VARCHAR(255) NOT NULL,
                         content TEXT NOT NULL,
                         user_nickname VARCHAR(255) NOT NULL,
                         status VARCHAR(50) NOT NULL DEFAULT 'Pending',
                         created_at TIMESTAMP NOT NULL
);

INSERT INTO inquiry (title, content, user_nickname, status, created_at)
VALUES ('테스트 문의 제목', '테스트 문의 내용', '테스트 사용자', 'Pending', CURRENT_TIMESTAMP);

-- Review Table
DROP TABLE IF EXISTS review;

CREATE TABLE review (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        product_id BIGINT NOT NULL,
                        content VARCHAR(1000) NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);