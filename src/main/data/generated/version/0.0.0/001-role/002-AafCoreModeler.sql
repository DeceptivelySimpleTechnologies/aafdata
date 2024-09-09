-- NOTE: Run this script as the custom AafCoreOwner database role/account, which should be created by the default postgres role.

-- NOTE: This custom AafCoreModeler role will be used to define/script the internal structure of the AafCore database, including its schemas, entity models (tables), functions, and system/lookup data, e.g. EntityTypeDefinition, EntityTypeAttribute, EntityType, EntitySubtype, etc.

-- Role: "AafCoreModeler"

-- DROP ROLE "AafCoreModeler";

CREATE ROLE "AafCoreModeler" WITH
    LOGIN
    PASSWORD 'M0d3l3rCl13nt!'
    NOSUPERUSER
    NOINHERIT
    NOCREATEDB
    NOCREATEROLE
    NOREPLICATION;