-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: InternetDomainLabelHierarchy

-- DROP SCHEMA "InternetDomainLabelHierarchy" ;

CREATE SCHEMA "InternetDomainLabelHierarchy"
    AUTHORIZATION "AafCoreModeler";

-- GRANT USAGE ON SCHEMA "InternetDomainLabelHierarchy" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "InternetDomainLabelHierarchy" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "InternetDomainLabelHierarchy" TO "AafCoreReadOnly";
