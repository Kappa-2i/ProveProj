-- Creazione della tabella Persona
CREATE TABLE Persona (
  CodiceFiscale VARCHAR(16) PRIMARY KEY,
  Nome VARCHAR(255) NOT NULL,
  Cognome VARCHAR(255) NOT NULL ,
  DataNascita DATE NOT NULL,
  NumeroTelefono CHAR(10) UNIQUE,
  Città VARCHAR(255) NOT NULL,
  Via VARCHAR(255) NOT NULL,
  N_Civico VARCHAR(10) NOT NULL,
  Cap VARCHAR(5) NOT NULL,

  CHECK (DataNascita <= CURRENT_DATE - INTERVAL '18 years'),
  CHECK (length(numerotelefono)=10),
  CHECK (length(cap)=5),
  CHECK (length(codicefiscale)=16)
);


-- Creazione della tabella Account
CREATE TABLE Account (
  Email VARCHAR(255) PRIMARY KEY,
  NomeUtente VARCHAR(255) NOT NULL,
  Password VARCHAR(255) NOT NULL,
  Persona_CODICEFISCALE VARCHAR(16) REFERENCES Persona(CodiceFiscale) ON DELETE CASCADE,

  CHECK (LENGTH(email) - LENGTH(REPLACE(email, '@', ''))=1)
);


-- Creazione della tabella ContoCorrente
CREATE TABLE ContoCorrente (
  IBAN VARCHAR(27) PRIMARY KEY,
  DataApertura DATE NOT NULL,
  Saldo DECIMAL NOT NULL DEFAULT 0,
  Account_Email VARCHAR(255) REFERENCES Account(Email) ON DELETE CASCADE
);


-- Definire il tipo ENUM per TipoCarta
CREATE TYPE tipo_carta AS ENUM ('CartaDiCredito', 'CartaDiDebito');

-- Creare o modificare la tabella Carta per usare il tipo ENUM
CREATE TABLE Carta (
  PAN VARCHAR(16) PRIMARY KEY,
  PIN VARCHAR(5) NOT NULL UNIQUE,
  CVV VARCHAR(3) NOT NULL UNIQUE,
  TipoCarta tipo_carta NOT NULL,
  MaxInvio DECIMAL DEFAULT 3000.00,
  LimiteFondi DECIMAL DEFAULT 10000.00,
  Contocorrente_IBAN VARCHAR(27) REFERENCES ContoCorrente(IBAN) ON DELETE CASCADE
);


-- Creazione della tabella Salvadanaio
CREATE TABLE Salvadanaio (
  IdSalvadanaio SERIAL PRIMARY KEY ,
  NomeSalvadanaio VARCHAR(255) NOT NULL,
  Obiettivo DECIMAL NOT NULL,
  SaldoRisparmio DECIMAL DEFAULT 0 NOT NULL,
  SaldoRimanente DECIMAL NOT NULL,
  Descrizione VARCHAR(255) DEFAULT NULL,
  ContoCorrente_IBAN VARCHAR(27) REFERENCES ContoCorrente(IBAN) ON DELETE CASCADE,
  DataCreazione date not null,
  UNIQUE(NomeSalvadanaio, ContoCorrente_IBAN),
  
  CHECK (obiettivo > 0)
);


-- Prima, definiamo il tipo ENUM per TipologiaPagamento
CREATE TYPE tipologia_pagamento AS ENUM ('Bonifico', 'BonificoIstantaneo');
CREATE TYPE tipo_transazione AS ENUM ('Riceve da', 'Invia a');


-- Poi, utilizziamo il tipo ENUM nella definizione della tabella Transazione
CREATE TABLE Transazione (
  IdTransazione SERIAL PRIMARY KEY,
  Importo DECIMAL,
  Causale VARCHAR(255) NOT NULL,
  DataTransazione DATE,
  OrarioTransazione TIME,
  Iban2 VARCHAR(27),
  TipologiaPagamento tipologia_pagamento,
  TipoTransazione tipo_transazione,
  CategoriaEntrata VARCHAR(255),
  CategoriaUscita VARCHAR(255),
  Iban1 VARCHAR(27) REFERENCES ContoCorrente(IBAN) ON DELETE CASCADE
);


-- Creazione della tabella Notifica
CREATE TABLE Notifica (
  IdNotifica SERIAL PRIMARY KEY,
  DescrizioneNotifica VARCHAR(255) NOT NULL,
  Transazione_IdTransazione INT REFERENCES Transazione(IdTransazione),
  DataNotifica DATE,
  OraNotifica TIME
);


-- Creazione della tabella Raccolta
CREATE TABLE Raccolta (
  NomeRaccolta VARCHAR(255) PRIMARY KEY ,
  DataCreazione DATE NOT NULL,
  ParoleChiave VARCHAR(1000) DEFAULT NULL
);


-- Creazione della tabella Colleziona (tabella ponte tra Transazione e Raccolta)
CREATE TABLE Colleziona (
  NomeRaccolta VARCHAR REFERENCES Raccolta(Nomeraccolta) ON DELETE CASCADE,
  IdTransazione INT REFERENCES Transazione(IdTransazione) ON DELETE CASCADE,
  PRIMARY KEY (NomeRaccolta, IdTransazione)
);
