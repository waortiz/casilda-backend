-- Alter table registrolinealma to add observacionescorreo and observacionestelefono columns
ALTER TABLE registrolinealma ADD COLUMN IF NOT EXISTS observacionescorreo character varying(500);
ALTER TABLE registrolinealma ADD COLUMN IF NOT EXISTS observacionestelefono character varying(500);
