CREATE TABLE IF NOT EXISTS instrutor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS curso (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    duracao_horas DOUBLE NOT NULL,
    instrutor_id BIGINT,
    FOREIGN KEY (instrutor_id) REFERENCES instrutor(id),
    INDEX idx_instrutor_id (instrutor_id)
);

CREATE TABLE IF NOT EXISTS log_curso (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    curso_id BIGINT NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);