CREATE DATABASE IF NOT EXISTS codenexus;
USE codenexus;

CREATE TABLE Time (
    idTime INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(75),
    regiao VARCHAR(75),
    titulos INT
);

CREATE TABLE Usuario (
    idUsuario INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(75),
    senha VARCHAR(75),
    nickname VARCHAR(75),
    email VARCHAR(75),
    telefone VARCHAR(11),
    pais VARCHAR(75),
    funcao VARCHAR(75),
    cargo VARCHAR(75),
    fkTime INT,
    CONSTRAINT fkUsuarioTime
        FOREIGN KEY (fkTime) REFERENCES Time(idTime)
);


CREATE TABLE Log (
    idLog INT PRIMARY KEY AUTO_INCREMENT, 
    dataHora DATETIME,
    status VARCHAR(20),
    CONSTRAINT chkStatus 
		CHECK (status IN ('SUCESSO', 'ERRO', 'INFO')),
    evento VARCHAR(75), 
    servico VARCHAR(75),
    mensagemErro TEXT,
    stacktrace TEXT,
    fkUsuario INT,
    CONSTRAINT fkLogUsuario
        FOREIGN KEY (fkUsuario) REFERENCES Usuario(idUsuario)
);

CREATE TABLE PostagensForum (
    idPostagensForum INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(75),
    conteudo TEXT,
    data DATE,
    horario TIME,
    fkUsuario INT,
    CONSTRAINT fkPostUsuario
        FOREIGN KEY (fkUsuario) REFERENCES Usuario(idUsuario)
);

CREATE TABLE PartidasTime (
    idPartidasTime INT PRIMARY KEY AUTO_INCREMENT,
    data DATE,
    resultado TINYINT,
    tipo VARCHAR(75),
    duracao TIME,
    totalAbates INT,
    totalAssistencias INT,
    totalMortes INT,
    totalGold INT,
    totalBaron INT,
    totalDrag INT,
    totalTorres INT,
    totalDano INT,
    fkTime INT,
    CONSTRAINT fkTimePartidastime
        FOREIGN KEY (fkTime) REFERENCES Time(idTime)
);

CREATE TABLE PartidasIndividual (
    idPartidasIndividual INT PRIMARY KEY AUTO_INCREMENT,
    data DATE,
    resultado TINYINT,
    duracao TIME,
    abates INT,
    assistencias INT,
    mortes INT,
    cs INT,
    dano INT,
    cura INT,
    fkUsuario INT,
    CONSTRAINT fkPartidasindUsuario
        FOREIGN KEY (fkUsuario) REFERENCES Usuario(idUsuario)
);

CREATE TABLE Dashboard (
    idDashboard INT PRIMARY KEY AUTO_INCREMENT,
    duracao INT,
    totalBaron INT,
    totalDrag INT,
    totalTorres INT,
    totalAbates INT,
    totalMortes INT,
    totalAssistencias INT,
    totalGold INT,
    totalDano INT
);