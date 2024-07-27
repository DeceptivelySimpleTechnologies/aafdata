-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: EntitySubtype

-- DROP SCHEMA "EntitySubtype" ;

CREATE SCHEMA "EntitySubtype"
    AUTHORIZATION "AafCorePublisher";

GRANT USAGE ON SCHEMA "EntitySubtype" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "EntitySubtype" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "EntitySubtype" TO "AafCoreReadOnly";
