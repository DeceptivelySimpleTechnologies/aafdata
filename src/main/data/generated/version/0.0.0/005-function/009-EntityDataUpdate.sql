-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.
-- FUNCTION: public.EntityDataUpdate(character varying)

-- DROP FUNCTION public."EntityDataUpdate"(character varying);

CREATE OR REPLACE FUNCTION public."EntityDataUpdate"(
	entitytypename character varying,
	updateClause character varying,
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
	updateQuery varchar;
	entityCount bigint;
	entityData varchar;
	countQuery varchar;
	dataQuery varchar;

	startTime timestamp without time zone;
	newDigest varchar;

  BEGIN

	startTime := now();
	newDigest := public.digest(''|| recordId ||', '|| updateClause  ||', '''|| correlationUuid ||''', '', '''|| startTime ||''', '|| userId ||', '''|| startTime ||''', '|| userId ||', '''|| startTime ||''', -1', 'sha256');
	newDigest := RIGHT(newDigest, LENGTH(newDigest) - 2);

--	updateQuery := 'UPDATE "'|| entitytypename ||'"."'|| entitytypename ||'" SET "LegalGivenName" = ''UpdatedLegalGivenName'', "BornAtDateTimeUtc" = ''2003-03-04 12:13:14.234'', "LegalCitizenOfCountryGeographicUnitId" = 0, "CorrelationUuid" = '''|| correlationUuid ||''', "Digest" = '''|| newDigest ||''', "UpdatedAtDateTimeUtc" = '''|| startTime ||''', "UpdatedByInformationSystemUserId" = '|| userId ||' WHERE "Id" = 2';
	updateQuery := 'UPDATE "'|| entitytypename ||'"."'|| entitytypename ||'" SET '|| updateClause ||', "CorrelationUuid" = '''|| correlationUuid ||''', "Digest" = '''|| newDigest ||''', "UpdatedAtDateTimeUtc" = '''|| startTime ||''', "UpdatedByInformationSystemUserId" = '|| userId ||' WHERE "Id" = '|| recordId;

	EXECUTE updateQuery;

	countQuery := 'SELECT COUNT("Id") FROM "' || entitytypename || '"."' || entitytypename || '" WHERE "IsActive" = true AND "DeletedAtDateTimeUtc" = ''9999-12-31T23:59:59.999'';';
	dataQuery := 'WITH JsonData AS (SELECT '|| selectClause ||' FROM "'|| entitytypename ||'"."'|| entitytypename ||'" WHERE "Id" = '|| recordId ||') SELECT JSON_AGG(JsonData.*) FROM JsonData;';

	EXECUTE countQuery INTO entityCount;
	EXECUTE dataQuery INTO entityData;

	RETURN '{"EntityType":"' || entitytypename || '","TotalRows":' || entityCount || ',"EntityData":' || entityData || '}';

  END;
$BODY$;

REVOKE EXECUTE ON FUNCTION public."EntityDataUpdate" FROM PUBLIC;

GRANT EXECUTE ON FUNCTION public."EntityDataUpdate" TO "AafCoreModeler";
GRANT EXECUTE ON FUNCTION public."EntityDataUpdate" TO "AafCoreReadWrite";
GRANT EXECUTE ON FUNCTION public."EntityDataUpdate" TO "AafCoreReadOnly";
