-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- FUNCTION: public.EntityDataDelete(character varying)

-- DROP FUNCTION public."EntityDataDelete"(character varying);

CREATE OR REPLACE FUNCTION public."EntityDataDelete"(
	entitytypename character varying,
	selectClause character varying,
	correlationUuid uuid,
	userId bigint,
	recordId bigint)
	RETURNS character varying
	LANGUAGE 'plpgsql'

	SECURITY DEFINER
	SET search_path = pg_catalog,pg_temp

	COST 100
	VOLATILE
    
AS $BODY$
  DECLARE
	deleteQuery varchar;
	entityCount bigint;
	entityData varchar;
	countQuery varchar;
	dataQuery varchar;

	startTime timestamp without time zone;
	newDigest varchar;

  BEGIN

	startTime := now() at time zone 'utc';
	newDigest := public.digest(''|| recordId ||', '''|| correlationUuid ||''', '', '''|| startTime ||''', '|| userId ||', '''|| startTime ||''', '|| userId ||', '''|| startTime ||''', -1', 'sha256');
	newDigest := RIGHT(newDigest, LENGTH(newDigest) - 2);

	deleteQuery := 'UPDATE "'|| entitytypename ||'"."'|| entitytypename ||'" SET "CorrelationUuid" = '''|| correlationUuid ||''', "Digest" = '''|| newDigest ||''', "DeletedAtDateTimeUtc" = '''|| startTime ||''', "DeletedByInformationSystemUserId" = '|| userId ||' WHERE "Id" = '|| recordId;

	EXECUTE deleteQuery;

	countQuery := 'SELECT COUNT("Id") FROM "' || entitytypename || '"."' || entitytypename || '" WHERE "IsActive" = true AND "DeletedAtDateTimeUtc" = ''9999-12-31T23:59:59.999'';';
	dataQuery := 'WITH JsonData AS (SELECT '|| selectClause ||' FROM "'|| entitytypename ||'"."'|| entitytypename ||'" WHERE "Id" = '|| recordId ||') SELECT JSON_AGG(JsonData.*) FROM JsonData;';

	EXECUTE countQuery INTO entityCount;
	EXECUTE dataQuery INTO entityData;

	RETURN '{"EntityType":"' || entitytypename || '","TotalRows":' || entityCount || ',"EntityData":' || entityData || '}';

  END;
$BODY$;

REVOKE EXECUTE ON FUNCTION public."EntityDataDelete" FROM PUBLIC;

GRANT EXECUTE ON FUNCTION public."EntityDataDelete" TO "AafCoreModeler";
GRANT EXECUTE ON FUNCTION public."EntityDataDelete" TO "AafCoreReadWrite";
GRANT EXECUTE ON FUNCTION public."EntityDataDelete" TO "AafCoreReadOnly";
