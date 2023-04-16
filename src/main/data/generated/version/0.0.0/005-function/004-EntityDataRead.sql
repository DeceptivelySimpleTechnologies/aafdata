-- FUNCTION: EntityDataRead

-- DROP FUNCTION "EntityDataRead";

CREATE FUNCTION "EntityDataRead"
(
    entityName IN varchar(100),
    entityData OUT varchar
)
    RETURNS varchar
    LANGUAGE plpgsql
AS
$$
DECLARE
    sqlQuery varchar := 'SELECT json_agg("' || entityName || '") FROM "' || entityName || '"."' || entityName || '"';

BEGIN
    EXECUTE sqlQuery INTO entityData;

END;
$$
