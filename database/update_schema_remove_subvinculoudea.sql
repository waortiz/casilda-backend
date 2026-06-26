-- Alter table registrolinealma to drop idsubvinculoudea foreign key constraint and column
ALTER TABLE registrolinealma DROP CONSTRAINT IF EXISTS registrolinealma_idsubvinculoudea_fkey;
ALTER TABLE registrolinealma DROP COLUMN IF EXISTS idsubvinculoudea;
