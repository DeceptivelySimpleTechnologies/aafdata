-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: OrganizationalUnit

-- DROP SCHEMA "OrganizationalUnit" ;

CREATE SCHEMA "OrganizationalUnit"
    AUTHORIZATION "AafCorePublisher";

GRANT USAGE ON SCHEMA "OrganizationalUnit" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "OrganizationalUnit" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "OrganizationalUnit" TO "AafCoreReadOnly";
