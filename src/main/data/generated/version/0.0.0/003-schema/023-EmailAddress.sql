-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: EmailAddress

-- DROP SCHEMA "EmailAddress" ;

CREATE SCHEMA "EmailAddress"
    AUTHORIZATION "AafCoreModeler";

-- GRANT USAGE ON SCHEMA "EmailAddress" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "EmailAddress" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "EmailAddress" TO "AafCoreReadOnly";
