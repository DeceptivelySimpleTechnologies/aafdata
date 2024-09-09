-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: EntityType

-- DROP SCHEMA "EntityType" ;

CREATE SCHEMA "EntityType"
    AUTHORIZATION "AafCorePublisher";

GRANT USAGE ON SCHEMA "EntityType" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "EntityType" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "EntityType" TO "AafCoreReadOnly";
