-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA:      

-- DROP SCHEMA "ContentItemGroupContentItemAssociation" ;

CREATE SCHEMA "ContentItemGroupContentItemAssociation"
    AUTHORIZATION "AafCoreModeler";

-- GRANT USAGE ON SCHEMA "ContentItemGroupContentItemAssociation" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "ContentItemGroupContentItemAssociation" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "ContentItemGroupContentItemAssociation" TO "AafCoreReadOnly";
