-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.
-- FUNCTION: public.EntityDataRead(character varying)

-- DROP FUNCTION public."EntityDataRead"(character varying);

CREATE OR REPLACE FUNCTION public."EntityDataRead"(
	entitytypename character varying,
	selectClause character varying,
	whereClause character varying,
	sortClause character varying,
	asOfDateTimeUtc timestamp,
	graphDepthLimit bigint,
	pageNumber bigint,
	pageSize bigint)
	RETURNS character varying
	LANGUAGE 'plpgsql'

	SECURITY DEFINER
	SET search_path = pg_catalog,pg_temp

	COST 100
	VOLATILE
    
AS $BODY$
DECLARE
	entityCount bigint;
	entityData varchar;

	countQuery varchar := 'SELECT COUNT("Id") FROM "' || entitytypename || '"."' || entitytypename || '" '|| whereClause ||';';
--  dataQuery varchar := 'SELECT json_agg("' || entitytypename || '" ORDER BY "Id" DESC) FROM "' || entitytypename || '"."' || entitytypename || '" WHERE "Id">0';
--	dataQuery varchar := 'WITH JsonData AS (SELECT * FROM "EntityType"."EntityType" WHERE "Id">0 ORDER BY "Id" DESC LIMIT 3 OFFSET 0) SELECT JSON_AGG(JsonData.*) FROM JsonData;';
--	dataQuery varchar := 'WITH JsonData AS (SELECT '|| selectClause ||' FROM "EntityType"."EntityType" WHERE "Id">0 ORDER BY "Id" DESC LIMIT 3 OFFSET 0) SELECT JSON_AGG(JsonData.*) FROM JsonData;';
	dataQuery varchar := 'WITH JsonData AS (SELECT '|| selectClause ||' FROM "'|| entitytypename ||'"."'|| entitytypename ||'" '|| whereClause ||' '|| sortClause ||' LIMIT '|| pageSize ||' OFFSET '|| pageNumber ||') SELECT JSON_AGG(JsonData.*) FROM JsonData;';

  BEGIN

	EXECUTE countQuery INTO entityCount;

	EXECUTE dataQuery INTO entityData;
	RETURN '{"EntityType":"' || entitytypename || '","TotalRows":' || entityCount || ',"EntityData":' || entityData || '}';

  END;
$BODY$;

REVOKE EXECUTE ON FUNCTION public."EntityDataRead" FROM PUBLIC;

GRANT EXECUTE ON FUNCTION public."EntityDataRead" TO "AafCoreModeler";
GRANT EXECUTE ON FUNCTION public."EntityDataRead" TO "AafCoreReadWrite";
GRANT EXECUTE ON FUNCTION public."EntityDataRead" TO "AafCoreReadOnly";
