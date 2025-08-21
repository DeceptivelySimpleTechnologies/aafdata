-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: Keyword

-- DROP SCHEMA "Keyword" ;

CREATE SCHEMA "Keyword"
    AUTHORIZATION "AafCoreModeler";

-- GRANT USAGE ON SCHEMA "Keyword" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "Keyword" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "Keyword" TO "AafCoreReadOnly";
