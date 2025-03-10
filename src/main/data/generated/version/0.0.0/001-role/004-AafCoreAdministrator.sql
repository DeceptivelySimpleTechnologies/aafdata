-- NOTE: Run this script as the custom AafCoreOwner database role/account, which should be created by the default postgres role.

-- NOTE: This custom AafCoreAdministrator role will be used to back up, archive, restore, undelete (unmark for deletion), and repair business entity data in the AafCore database tables (entity models) that have been defined/scripted and created by the AafCoreModeler role.  These operations will most likely be performed interactively by database administrators with direct table access.

-- Role: "AafCoreAdministrator"

-- DROP ROLE "AafCoreAdministrator";

CREATE ROLE "AafCoreAdministrator" WITH
    LOGIN
    PASSWORD 'Adm1n15tat0rCl13nt!'
    NOSUPERUSER
    NOINHERIT
    NOCREATEDB
    NOCREATEROLE
    NOREPLICATION;