-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.
-- FUNCTION: GetMinimumDateTimeUtc

-- DROP FUNCTION "GetMinimumDateTimeUtc";

CREATE FUNCTION public."GetMinimumDateTimeUtc"()
    RETURNS timestamp without time zone
    LANGUAGE plpgsql

	SECURITY DEFINER
	SET search_path = pg_catalog,pg_temp

	COST 100
	VOLATILE
    
AS
$BODY$
DECLARE
    minimumDateTimeUtc timestamp without time zone;
BEGIN
    SELECT '1900-01-01T00:00:00.000Z'::timestamp INTO minimumDateTimeUtc;
    RETURN minimumDateTimeUtc;
END;
$BODY$;

--ALTER FUNCTION "GetMinimumDateTimeUtc"(character varying)
--	OWNER TO AafCorePublisher;

REVOKE EXECUTE ON FUNCTION public."GetMinimumDateTimeUtc" FROM PUBLIC;

GRANT EXECUTE ON FUNCTION public."GetMinimumDateTimeUtc" TO "AafCoreModeler";
GRANT EXECUTE ON FUNCTION public."GetMinimumDateTimeUtc" TO "AafCoreReadWrite";
GRANT EXECUTE ON FUNCTION public."GetMinimumDateTimeUtc" TO "AafCoreReadOnly";
