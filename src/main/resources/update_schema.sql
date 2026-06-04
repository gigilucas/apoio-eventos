-- Adicionar campo Password à tabela Participante
ALTER TABLE Participante ADD COLUMN Password VARCHAR(255) NULL;

-- Adicionar campo Role à tabela Participante
ALTER TABLE Participante ADD COLUMN Role VARCHAR(20) DEFAULT 'USER';

-- Adicionar constraint unique ao Email
ALTER TABLE Participante MODIFY COLUMN Email VARCHAR(255) UNIQUE;

-- Atualizar registros existentes com senhas padrão (depois devem ser alteradas pelos utilizadores)
UPDATE Participante SET Password = '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi' WHERE Password IS NULL;
-- Senha padrão: '123456' (BCrypt)
