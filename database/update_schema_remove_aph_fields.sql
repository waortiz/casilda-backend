-- Alter table atencionaph to drop foreign key constraints and columns for idcanalaph, idconvenioaph, idambitoaph
ALTER TABLE atencionaph DROP CONSTRAINT IF EXISTS atencionaph_idcanalaph_fkey;
ALTER TABLE atencionaph DROP CONSTRAINT IF EXISTS atencionaph_idconvenioaph_fkey;
ALTER TABLE atencionaph DROP CONSTRAINT IF EXISTS atencionaph_idambitoaph_fkey;

ALTER TABLE atencionaph DROP COLUMN IF EXISTS idcanalaph;
ALTER TABLE atencionaph DROP COLUMN IF EXISTS idconvenioaph;
ALTER TABLE atencionaph DROP COLUMN IF EXISTS idambitoaph;

DROP TABLE IF EXISTS canalaph CASCADE;
DROP TABLE IF EXISTS convenioaph CASCADE;
DROP TABLE IF EXISTS ambitoaph CASCADE;
