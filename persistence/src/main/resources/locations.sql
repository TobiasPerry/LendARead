ALTER TABLE location
    ADD COLUMN owner INT references users(id) ON DELETE CASCADE;
