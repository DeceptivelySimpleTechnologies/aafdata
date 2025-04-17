-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: Periodicity

-- DROP SCHEMA "Periodicity" ;

CREATE SCHEMA "Periodicity"
    AUTHORIZATION "AafCoreModeler";

-- GRANT USAGE ON SCHEMA "Periodicity" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "Periodicity" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "Periodicity" TO "AafCoreReadOnly";
