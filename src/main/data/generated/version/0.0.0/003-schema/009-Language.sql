-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: Language

-- DROP SCHEMA "Language" ;

CREATE SCHEMA "Language"
    AUTHORIZATION "AafCoreModeler";

-- GRANT USAGE ON SCHEMA "Language" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "Language" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "Language" TO "AafCoreReadOnly";
