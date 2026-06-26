-- Add new independent columns to atencionaph
ALTER TABLE atencionaph ADD COLUMN IF NOT EXISTS notaaph character varying;
ALTER TABLE atencionaph ADD COLUMN IF NOT EXISTS motivonotriage character varying;

-- Migrate existing data based on whether triage was practiced
UPDATE atencionaph SET notaaph = notaomotivotriage WHERE practicotriage = true;
UPDATE atencionaph SET motivonotriage = notaomotivotriage WHERE practicotriage = false;

-- Drop the old dual column
ALTER TABLE atencionaph DROP COLUMN IF EXISTS notaomotivotriage;
