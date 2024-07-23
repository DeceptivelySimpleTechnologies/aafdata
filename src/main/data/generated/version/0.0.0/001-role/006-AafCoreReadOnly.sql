-- NOTE: Run this script as the custom AafCoreOwner database role/account, which should be created by the default postgres role.

-- NOTE: This custom AafCoreReadWrite role will be used to read business entity data in the AafCore database tables (entity models) that have been defined/scripted by the AafCoreModeler role and created by the AafCorePublisher role.  These read-only operations will be interactively requested by API clients, e.g. web and mobile applications, utilities, etc, for reporting, analytics, and other prurposes without risking changes to this data and will be executed via custom Postgres functions rather than via direct table access

-- Role: "AafCoreReadOnly"

-- DROP ROLE "AafCoreReadOnly";

CREATE ROLE "AafCoreReadOnly" WITH
    LOGIN
    PASSWORD 'R3ad0nlyCl13nt!'
    NOSUPERUSER
    NOINHERIT
    NOCREATEDB
    NOCREATEROLE
    NOREPLICATION;