ALTER TABLE location
    ADD COLUMN owner INT references users(id) ON DELETE CASCADE;
ALTER TABLE location
    ADD COLUMN name VARCHAR(100)  NULL UNIQUE;
; esto va dsp
ALTER TABLE location
    ADD COLUMN name VARCHAR(100) NOT NULL ;
