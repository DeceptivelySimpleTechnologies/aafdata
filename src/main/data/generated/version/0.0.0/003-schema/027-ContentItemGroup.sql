-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: ContentItemGroup

-- DROP SCHEMA "ContentItemGroup" ;

CREATE SCHEMA "ContentItemGroup"
    AUTHORIZATION "AafCoreModeler";

-- GRANT USAGE ON SCHEMA "ContentItemGroup" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "ContentItemGroup" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "ContentItemGroup" TO "AafCoreReadOnly";
