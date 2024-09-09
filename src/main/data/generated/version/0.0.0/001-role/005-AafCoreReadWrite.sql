-- NOTE: Run this script as the custom AafCoreOwner database role/account, which should be created by the default postgres role.

-- NOTE: This custom AafCoreReadWrite role will be used to create, read, update, and mark for deletion (CRUD) business entity data in the AafCore database tables (entity models) that have been defined/scripted by the AafCoreModeler role and created by the AafCorePublisher role.  These CRUD operations will be interactively requested by API clients, e.g. web and mobile applications, utilities, etc, and will be executed via custom Postgres functions rather than via direct table access.

-- Role: "AafCoreReadWrite"

-- DROP ROLE "AafCoreReadWrite";

CREATE ROLE "AafCoreReadWrite" WITH
    LOGIN
    PASSWORD 'R3adWr1t3Cl13nt!'
    NOSUPERUSER
    NOINHERIT
    NOCREATEDB
    NOCREATEROLE
    NOREPLICATION;