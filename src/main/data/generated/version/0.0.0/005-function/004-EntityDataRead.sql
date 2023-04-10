-- FUNCTION: EntityDataRead

-- DROP FUNCTION EntityDataRead ;

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

BEGIN
    SELECT
        json_agg("EntityType")
    FROM
        "EntityType"."EntityType"
    INTO
        entityData;

END;
$$
