-- FUNCTION: EntityDataRead

-- DROP FUNCTION "EntityDataRead";

CREATE FUNCTION "EntityDataRead"
(
    entityName IN varchar(100)
)
    RETURNS varchar
    LANGUAGE plpgsql
AS
$$
DECLARE
    entityData varchar;
    sqlQuery varchar := 'SELECT json_agg("' || entityName || '") FROM "' || entityName || '"."' || entityName || '"';

BEGIN

    EXECUTE sqlQuery INTO entityData;
    RETURN entityData;

END;
$$
