-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: InternetDomainLabel

-- DROP SCHEMA "InternetDomainLabel" ;

CREATE SCHEMA "InternetDomainLabel"
    AUTHORIZATION "AafCoreModeler";

-- GRANT USAGE ON SCHEMA "InternetDomainLabel" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "InternetDomainLabel" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "InternetDomainLabel" TO "AafCoreReadOnly";
