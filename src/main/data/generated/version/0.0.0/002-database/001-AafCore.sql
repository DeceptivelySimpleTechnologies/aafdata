-- NOTE: Run this script as the custom AafCoreOwner database role/account, which should be created by the default postgres role.
-- Database: AafCore

-- DROP DATABASE "AafCore";

CREATE DATABASE "AafCore"
    WITH
    OWNER = "AafCoreOwner"
    ENCODING = 'UTF8'
    LC_COLLATE = 'C'
    LC_CTYPE = 'C'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

ALTER DATABASE AafCore
    SET "TIMEZONE" TO UTC;
