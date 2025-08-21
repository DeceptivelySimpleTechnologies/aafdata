-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: KeywordGroup

-- DROP SCHEMA "KeywordGroup" ;

CREATE SCHEMA "KeywordGroup"
    AUTHORIZATION "AafCoreModeler";

-- GRANT USAGE ON SCHEMA "KeywordGroup" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "KeywordGroup" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "KeywordGroup" TO "AafCoreReadOnly";
