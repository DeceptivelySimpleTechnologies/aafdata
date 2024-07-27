-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.
-- FUNCTION: GetMaximumDateTimeUtc

-- DROP FUNCTION "GetMaximumDateTimeUtc";

CREATE FUNCTION public."GetMaximumDateTimeUtc"()
    RETURNS timestamp without time zone
    LANGUAGE plpgsql

	SECURITY DEFINER
	SET search_path = pg_catalog,pg_temp

	COST 100
	VOLATILE
    
AS
$BODY$
DECLARE
    maximumDateTimeUtc timestamp without time zone;
BEGIN
    SELECT '9999-12-31T23:59:59.999Z'::timestamp INTO maximumDateTimeUtc;
    RETURN maximumDateTimeUtc;
END;
$BODY$;

--ALTER FUNCTION "GetMaximumDateTimeUtc"(character varying)
--	OWNER TO AafCorePublisher;

REVOKE EXECUTE ON FUNCTION public."GetMaximumDateTimeUtc" FROM PUBLIC;

GRANT EXECUTE ON FUNCTION public."GetMaximumDateTimeUtc" TO "AafCoreModeler";
GRANT EXECUTE ON FUNCTION public."GetMaximumDateTimeUtc" TO "AafCoreReadWrite";
GRANT EXECUTE ON FUNCTION public."GetMaximumDateTimeUtc" TO "AafCoreReadOnly";
