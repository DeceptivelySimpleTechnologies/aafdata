-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: LegalEntity

-- DROP SCHEMA "LegalEntity" ;

CREATE SCHEMA "LegalEntity"
    AUTHORIZATION "AafCorePublisher";

GRANT USAGE ON SCHEMA "LegalEntity" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "LegalEntity" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "LegalEntity" TO "AafCoreReadOnly";
