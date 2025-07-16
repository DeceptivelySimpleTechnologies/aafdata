-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: UniformResourceIdentifier

-- DROP SCHEMA "UniformResourceIdentifier" ;

CREATE SCHEMA "UniformResourceIdentifier"
    AUTHORIZATION "AafCoreModeler";

-- GRANT USAGE ON SCHEMA "UniformResourceIdentifier" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "UniformResourceIdentifier" TO "AafCoreReadOnly";
GRANT USAGE ON SCHEMA "UniformResourceIdentifier" TO "AafCoreReadWrite";
