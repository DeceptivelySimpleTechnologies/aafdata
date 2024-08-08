-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: GeographicUnitHierarchy

-- DROP SCHEMA "GeographicUnitHierarchy" ;

CREATE SCHEMA "GeographicUnitHierarchy"
    AUTHORIZATION "AafCorePublisher";

GRANT USAGE ON SCHEMA "GeographicUnitHierarchy" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "GeographicUnitHierarchy" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "GeographicUnitHierarchy" TO "AafCoreReadOnly";
