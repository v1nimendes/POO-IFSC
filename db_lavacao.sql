CREATE DATABASE IF NOT EXISTS db_lavacao;
USE db_lavacao;

CREATE TABLE IF NOT EXISTS cor(
   id int NOT NULL auto_increment,
   nome varchar(100),
   CONSTRAINT pk_cor
      PRIMARY KEY(id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS marca(
   id int NOT NULL auto_increment,
   nome  varchar(100) NOT NULL,
   CONSTRAINT pk_marca
      PRIMARY KEY(id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS modelo(
   id int NOT NULL auto_increment,
   descricao varchar(100),
   id_marca int NOT NULL ,
   categoria ENUM('PEQUENO', 'MEDIO', 'GRANDE', 'MOTO', 'PADRAO') NOT NULL DEFAULT 'PADRAO',
   CONSTRAINT pk_modelo
      PRIMARY KEY(id),
   CONSTRAINT fk_modelo_marca
      FOREIGN KEY(id_marca)
      REFERENCES marca(id)
) ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS motor(
   id_modelo INT NOT NULL REFERENCES modelo(id),
   potencia int NOT NULL DEFAULT 100,
   tipoCombustivel ENUM('GASOLINA', 'ETANOL', 'FLEX', 'GNV', 'OUTRO') NOT NULL DEFAULT 'GASOLINA',
	CONSTRAINT pk_motor PRIMARY KEY (id_modelo),
    CONSTRAINT fk_motor_modelo FOREIGN KEY (id_modelo) REFERENCES modelo(id) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS cliente(
   id int NOT NULL auto_increment,
   nome varchar(50) NOT NULL,
   celular varchar(50) NOT NULL,
   email varchar(100),
   endereco varchar(100),
   dataCadastro date,
   CONSTRAINT pk_cliente
      PRIMARY KEY(id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS pessoa_fisica(
	id_cliente INT NOT NULL REFERENCES cliente(id),
	cpf varchar(100) NOT NULL,
    dataNascimento date,
    CONSTRAINT pk_pessoaFisica PRIMARY KEY (id_cliente),
    CONSTRAINT fk_pessoaFisica_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id) 
		ON DELETE CASCADE
        ON UPDATE CASCADE
)ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS pessoa_juridica(
	id_cliente INT NOT NULL REFERENCES cliente(id),
	cnpj varchar(100) NOT NULL,
    inscricaoEstadual varchar(100),
    dataNascimento date,
    CONSTRAINT pk_pessoaJuridica PRIMARY KEY (id_cliente),
    CONSTRAINT fk_pessoaJuridica_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id) 
		ON DELETE CASCADE
        ON UPDATE CASCADE
)ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS veiculo(
   id int NOT NULL auto_increment,
   placa varchar(100) NOT NULL,
   observacoes varchar(100) NOT NULL,
   id_modelo int NOT NULL,
   id_cor int NOT NULL,
   id_cliente int NOT NULL,
   CONSTRAINT pk_veiculo
      PRIMARY KEY(id),
   CONSTRAINT fk_veiculo_modelo
      FOREIGN KEY(id_modelo)
      REFERENCES modelo(id),
   CONSTRAINT fk_veiculo_cor
      FOREIGN KEY(id_cor)
      REFERENCES cor(id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS servico(
	id INT NOT NULL auto_increment,
    descricao varchar(100),
    valor double,
    pontos INT NOT NULL,
    categoria ENUM('PEQUENO', 'MEDIO', 'GRANDE', 'MOTO', 'PADRAO') NOT NULL DEFAULT 'PADRAO',
    CONSTRAINT pk_servico PRIMARY KEY (id)
)ENGINE = InnoDB;

/*
  TABELAS PARA IMPLEMENTAÇÃO DO CONCEITO DE CLASSES ASSOCIATIVAS OU CLASSES INTERMEDIÁRIAS, QUE 
  IMPLEMENTAM A RELAÇÃO DE MULTIPLICIDADE MUITOS PARA MUITOS (M:N)
  TABELAS: ordemServico, itemOS, veiculo e servico (estas duas últimas, já implementada)
*/
CREATE TABLE IF NOT EXISTS ordem_servico(
	numero int NOT NULL auto_increment,
    total decimal(10,2) NOT NULL,
    agenda DATE NOT NULL,
    desconto double,
    id_veiculo int NOT NULL,
    status ENUM('ABERTA', 'FECHADA', 'CANCELADA') NOT NULL DEFAULT 'ABERTA',
    CONSTRAINT pk_ordemServico 
		PRIMARY KEY (numero),
    CONSTRAINT fk_ordemServico_veiculo 
		FOREIGN KEY(id_veiculo) 
        REFERENCES veiculo(id)
)ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS item_OS(
	id int NOT NULL auto_increment,
	valorServico decimal(10,2),
    observacoes varchar(100),
    id_servico INT NOT NULL,
    id_ordem_servico INT NOT NULL,
	CONSTRAINT pk_item_OS 
		PRIMARY KEY (id),
    CONSTRAINT fk_item_OS_servico 
		FOREIGN KEY (id_servico) 
		REFERENCES servico(id), 
	CONSTRAINT fk_item_OS_ordem_servico 
		FOREIGN KEY (id_ordem_servico) 
		REFERENCES ordem_servico(numero)
        ON DELETE CASCADE
)ENGINE = InnoDB;

