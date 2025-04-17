-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: EntitySubtypeHierarchy

-- DROP SCHEMA "EntitySubtypeHierarchy" ;

CREATE SCHEMA "EntitySubtypeHierarchy"
    AUTHORIZATION "AafCoreModeler";

-- GRANT USAGE ON SCHEMA "EntitySubtypeHierarchy" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "EntitySubtypeHierarchy" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "EntitySubtypeHierarchy" TO "AafCoreReadOnly";
