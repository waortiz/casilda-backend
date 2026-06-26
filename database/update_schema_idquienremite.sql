-- Alter table registrolinealma to change quienremite to idquienremite foreign key
ALTER TABLE registrolinealma DROP COLUMN IF EXISTS quienremite;
ALTER TABLE registrolinealma ADD COLUMN idquienremite integer;
ALTER TABLE registrolinealma ADD CONSTRAINT registrolinealma_idquienremite_fkey FOREIGN KEY (idquienremite) REFERENCES actorremitente(id) ON DELETE NO ACTION;
