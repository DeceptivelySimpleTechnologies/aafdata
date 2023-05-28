-- FUNCTION: GetMinimumDateTimeUtc

-- DROP FUNCTION "GetMinimumDateTimeUtc";

CREATE FUNCTION "GetMinimumDateTimeUtc"()
        RETURNS timestamp without time zone
        LANGUAGE plpgsql
    AS
$$
DECLARE
    minimumDateTimeUtc timestamp without time zone;
BEGIN
    SELECT '1900-01-01T00:00:00.000Z'::timestamp INTO minimumDateTimeUtc;
    RETURN minimumDateTimeUtc;
END;
$$
