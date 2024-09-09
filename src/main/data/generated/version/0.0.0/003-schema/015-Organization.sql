-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: Organization

-- DROP SCHEMA "Organization" ;

CREATE SCHEMA "Organization"
    AUTHORIZATION "AafCorePublisher";

GRANT USAGE ON SCHEMA "Organization" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "Organization" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "Organization" TO "AafCoreReadOnly";
