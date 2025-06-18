-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: PostalAddress

-- DROP SCHEMA "PostalAddress" ;

CREATE SCHEMA "PostalAddress"
    AUTHORIZATION "AafCoreModeler";

-- GRANT USAGE ON SCHEMA "PostalAddress" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "PostalAddress" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "PostalAddress" TO "AafCoreReadOnly";
