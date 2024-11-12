-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: Employee

-- DROP SCHEMA "Employee" ;

CREATE SCHEMA "Employee"
    AUTHORIZATION "AafCoreModeler";

-- GRANT USAGE ON SCHEMA "Employee" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "Employee" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "Employee" TO "AafCoreReadOnly";
