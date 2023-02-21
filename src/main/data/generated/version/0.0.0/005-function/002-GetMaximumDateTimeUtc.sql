-- FUNCTION: GetMaximumDateTimeUtc

-- DROP FUNCTION GetMaximumDateTimeUtc ;

CREATE FUNCTION GetMaximumDateTimeUtc()
    RETURNS timestamp without time zone
    LANGUAGE plpgsql
AS
$$
DECLARE
    maximumDateTimeUtc timestamp without time zone;
BEGIN
    SELECT '9999-12-31T23:59:59.999Z'::timestamp INTO maximumDateTimeUtc;
    RETURN maximumDateTimeUtc;
END;
$$
