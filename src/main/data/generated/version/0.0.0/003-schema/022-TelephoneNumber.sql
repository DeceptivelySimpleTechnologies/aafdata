-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: TelephoneNumber

-- DROP SCHEMA "TelephoneNumber" ;

CREATE SCHEMA "TelephoneNumber"
    AUTHORIZATION "AafCoreModeler";

-- GRANT USAGE ON SCHEMA "TelephoneNumber" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "TelephoneNumber" TO "AafCoreReadOnly";
GRANT USAGE ON SCHEMA "TelephoneNumber" TO "AafCoreReadWrite";
