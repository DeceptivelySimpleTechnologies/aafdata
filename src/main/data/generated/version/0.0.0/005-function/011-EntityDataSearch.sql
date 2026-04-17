-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- FUNCTION: public.EntityDataSearch(character varying)

-- DROP FUNCTION public."EntityDataSearch"(character varying);

CREATE OR REPLACE FUNCTION public."EntityDataSearch"(
	entitytypename character varying,
	selectClause character varying,
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
	dataQuery varchar;

  BEGIN

	dataQuery := 'WITH JsonData AS (' || selectClause ||' LIMIT '|| pageSize ||' OFFSET 0) SELECT JSON_AGG(JsonData.*) FROM JsonData;';

	EXECUTE dataQuery INTO entityData;

	IF (entityData IS NULL) THEN
		RETURN '{"EntityType":"' || entitytypename || '","TotalRows":0,"EntityData":[]}';
	ELSE
		entityCount := JSON_ARRAY_LENGTH(entityData::json);
		RETURN '{"EntityType":"' || entitytypename || '","TotalRows":' || entityCount || ',"EntityData":' || entityData || '}';
	END IF;

  END;
$BODY$;

REVOKE EXECUTE ON FUNCTION public."EntityDataSearch" FROM PUBLIC;

GRANT EXECUTE ON FUNCTION public."EntityDataSearch" TO "AafCoreModeler";
GRANT EXECUTE ON FUNCTION public."EntityDataSearch" TO "AafCoreReadWrite";
GRANT EXECUTE ON FUNCTION public."EntityDataSearch" TO "AafCoreReadOnly";
