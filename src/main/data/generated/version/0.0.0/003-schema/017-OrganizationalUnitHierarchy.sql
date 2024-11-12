-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: OrganizationalUnitHierarchy

-- DROP SCHEMA "OrganizationalUnitHierarchy" ;

CREATE SCHEMA "OrganizationalUnitHierarchy"
    AUTHORIZATION "AafCoreModeler";

-- GRANT USAGE ON SCHEMA "OrganizationalUnitHierarchy" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "OrganizationalUnitHierarchy" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "OrganizationalUnitHierarchy" TO "AafCoreReadOnly";
