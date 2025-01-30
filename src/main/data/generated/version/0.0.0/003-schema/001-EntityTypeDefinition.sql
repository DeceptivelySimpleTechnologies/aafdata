-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: EntityTypeDefinition

-- DROP SCHEMA "EntityTypeDefinition" ;

CREATE SCHEMA "EntityTypeDefinition"
    AUTHORIZATION "AafCoreModeler";

-- GRANT USAGE ON SCHEMA "EntityTypeDefinition" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "EntityTypeDefinition" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "EntityTypeDefinition" TO "AafCoreReadOnly";
