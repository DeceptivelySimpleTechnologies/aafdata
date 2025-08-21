-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA:      

-- DROP SCHEMA "KeywordGroupKeywordAssociation" ;

CREATE SCHEMA "KeywordGroupKeywordAssociation"
    AUTHORIZATION "AafCoreModeler";

-- GRANT USAGE ON SCHEMA "KeywordGroupKeywordAssociation" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "KeywordGroupKeywordAssociation" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "KeywordGroupKeywordAssociation" TO "AafCoreReadOnly";
