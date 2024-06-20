-- FUNCTION: public.EntityDataRead(character varying)

-- DROP FUNCTION public."EntityDataRead"(character varying);

CREATE OR REPLACE FUNCTION public."EntityDataRead"(
	entitytypename character varying)
    RETURNS character varying
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
    
AS $BODY$
DECLARE
    entityData varchar;
--    sqlQuery varchar := 'SELECT json_agg("' || entitytypename || '" ORDER BY "Id" DESC) FROM "' || entitytypename || '"."' || entitytypename || '" WHERE "Id">0';
    sqlQuery varchar := 'WITH JsonData AS (SELECT * FROM "EntityType"."EntityType" WHERE "Id">0 ORDER BY "Id" DESC LIMIT 3 OFFSET 0) SELECT JSON_AGG(JsonData.*) FROM JsonData;';

  BEGIN

    EXECUTE sqlQuery INTO entityData;
    RETURN entityData;

  END;
$BODY$;

ALTER FUNCTION public."EntityDataRead"(character varying)
    OWNER TO postgres;
