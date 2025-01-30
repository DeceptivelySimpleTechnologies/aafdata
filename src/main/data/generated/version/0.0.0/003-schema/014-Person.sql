-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: Person

-- DROP SCHEMA "Person" ;

CREATE SCHEMA "Person"
    AUTHORIZATION "AafCoreModeler";

-- GRANT USAGE ON SCHEMA "Person" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "Person" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "Person" TO "AafCoreReadOnly";
