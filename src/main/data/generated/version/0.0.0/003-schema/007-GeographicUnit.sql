-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: GeographicUnit

-- DROP SCHEMA "GeographicUnit" ;

CREATE SCHEMA "GeographicUnit"
    AUTHORIZATION "AafCorePublisher";

GRANT USAGE ON SCHEMA "GeographicUnit" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "GeographicUnit" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "GeographicUnit" TO "AafCoreReadOnly";
