-- FUNCTION: ValidateEntityName

-- DROP FUNCTION "ValidateEntityName";

CREATE FUNCTION "ValidateEntityName"
(
    entityName IN varchar(100)
)
    RETURNS boolean
    LANGUAGE plpgsql
AS
$$
DECLARE
    entityNames varchar;
    isValidEntityName boolean;

BEGIN
    SELECT
        array_agg("LocalizedName")
    FROM
        "EntityType"."EntityType"
    INTO
        entityNames;

    IF
        position(lower(entityName) in lower(entityNames)) > 0
    THEN
        isValidEntityName := true;
    ELSE
        isValidEntityName := false;
    END IF;

    RETURN isValidEntityName;

END;
$$
