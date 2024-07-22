-- NOTE: Run this script as the default/built-in postgres database role/account.

-- NOTE: The default postgres role should create this AafCoreOwner role, which should then create the other AAF-specific roles, the AafCore database, and the uuid-ossp extension.  This AafCoreOwner is to create necessary structure only, not data.

-- Role: "AafCoreOwner"

-- DROP ROLE "AafCoreOwner";

CREATE ROLE "AafCoreOwner" WITH
    LOGIN
    PASSWORD '0wn3rCl13nt!'
    NOSUPERUSER
    NOINHERIT
    NOCREATEDB
    NOCREATEROLE
    NOREPLICATION;