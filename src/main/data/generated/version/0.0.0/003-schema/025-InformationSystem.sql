-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: InformationSystem

-- DROP SCHEMA "InformationSystem" ;

CREATE SCHEMA "InformationSystem"
    AUTHORIZATION "AafCoreModeler";

--GRANT USAGE ON SCHEMA "InformationSystem" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "InformationSystem" TO "AafCoreReadOnly";
GRANT USAGE ON SCHEMA "InformationSystem" TO "AafCoreReadWrite";
