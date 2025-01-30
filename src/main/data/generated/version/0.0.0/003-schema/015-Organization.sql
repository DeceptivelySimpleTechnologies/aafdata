-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: Organization

-- DROP SCHEMA "Organization" ;

CREATE SCHEMA "Organization"
    AUTHORIZATION "AafCoreModeler";

-- GRANT USAGE ON SCHEMA "Organization" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "Organization" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "Organization" TO "AafCoreReadOnly";
