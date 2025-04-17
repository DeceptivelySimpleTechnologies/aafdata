-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: Currency

-- DROP SCHEMA "Currency" ;

CREATE SCHEMA "Currency"
    AUTHORIZATION "AafCoreModeler";

-- GRANT USAGE ON SCHEMA "Currency" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "Currency" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "Currency" TO "AafCoreReadOnly";
