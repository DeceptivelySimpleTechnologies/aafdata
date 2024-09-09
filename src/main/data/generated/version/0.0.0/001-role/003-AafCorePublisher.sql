-- NOTE: Run this script as the custom AafCoreOwner database role/account, which should be created by the default postgres role.

-- NOTE: This custom AafCorePublisher role will be used to create the internal structure of the AafCore database, including its schemas, entity models (tables), functions, and system/lookup data, e.g. EntityTypeDefinition, EntityTypeAttribute, EntityType, EntitySubtype, etc, that has been defined/scripted by the AafCoreModeler role.

-- Role: "AafCorePublisher"

-- DROP ROLE "AafCorePublisher";

CREATE ROLE "AafCorePublisher" WITH
    LOGIN
    PASSWORD 'Publ15h3rCl13nt!'
    NOSUPERUSER
    NOINHERIT
    CREATEDB
    NOCREATEROLE
    NOREPLICATION;
