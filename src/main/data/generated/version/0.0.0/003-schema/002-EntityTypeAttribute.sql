-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: EntityTypeAttribute

-- DROP SCHEMA "EntityTypeAttribute" ;

CREATE SCHEMA "EntityTypeAttribute"
    AUTHORIZATION "AafCoreModeler";

-- GRANT USAGE ON SCHEMA "EntityTypeAttribute" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "EntityTypeAttribute" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "EntityTypeAttribute" TO "AafCoreReadOnly";
