-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: Locale

-- DROP SCHEMA "Locale" ;

CREATE SCHEMA "Locale"
    AUTHORIZATION "AafCoreModeler";

-- GRANT USAGE ON SCHEMA "Locale" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "Locale" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "Locale" TO "AafCoreReadOnly";
