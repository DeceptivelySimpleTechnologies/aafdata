-- FUNCTION: public.EntityDataRead(character varying)

-- DROP FUNCTION public."EntityDataRead"(character varying);

-- selectClause character varying
-- whereClause character varying
-- sortClause character varying
-- asOfDateTimeUtc
-- graphDepthLimit
-- pageNumber
-- pageSize

CREATE OR REPLACE FUNCTION public."EntityDataRead"(
	entitytypename character varying,
	selectClause character varying)
	RETURNS character varying
	LANGUAGE 'plpgsql'

	COST 100
	VOLATILE
    
AS $BODY$
DECLARE
	entityCount bigint;
	entityData varchar;

	countQuery varchar := 'SELECT COUNT("Id") FROM "EntityType"."EntityType" WHERE "Id">0;';
--  dataQuery varchar := 'SELECT json_agg("' || entitytypename || '" ORDER BY "Id" DESC) FROM "' || entitytypename || '"."' || entitytypename || '" WHERE "Id">0';
--	dataQuery varchar := 'WITH JsonData AS (SELECT * FROM "EntityType"."EntityType" WHERE "Id">0 ORDER BY "Id" DESC LIMIT 3 OFFSET 0) SELECT JSON_AGG(JsonData.*) FROM JsonData;';
	dataQuery varchar := 'WITH JsonData AS (SELECT '|| selectClause ||' FROM "EntityType"."EntityType" WHERE "Id">0 ORDER BY "Id" DESC LIMIT 3 OFFSET 0) SELECT JSON_AGG(JsonData.*) FROM JsonData;';

  BEGIN

	EXECUTE countQuery INTO entityCount;

	EXECUTE dataQuery INTO entityData;
	RETURN '{"TotalRows":' || entityCount || ',"EntityData":' || entityData || '}';

  END;
$BODY$;

-- ALTER FUNCTION public."EntityDataRead"(character varying)
--	OWNER TO postgres;

-- GRANT to ReadWrite, etc rather than to OWNER TO postgres
