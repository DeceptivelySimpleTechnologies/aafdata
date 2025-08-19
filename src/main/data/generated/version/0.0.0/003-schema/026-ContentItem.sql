-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: ContentItem

-- DROP SCHEMA "ContentItem" ;

CREATE SCHEMA "ContentItem"
    AUTHORIZATION "AafCoreModeler";

-- GRANT USAGE ON SCHEMA "ContentItem" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "ContentItem" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "ContentItem" TO "AafCoreReadOnly";
