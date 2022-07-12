-- Role: "AafDatabaseOwner"
-- DROP ROLE "AafDatabaseOwner";

CREATE ROLE "AafCoreOwner" WITH
    LOGIN
    NOSUPERUSER
    NOINHERIT
    NOCREATEDB
    NOCREATEROLE
    NOREPLICATION;