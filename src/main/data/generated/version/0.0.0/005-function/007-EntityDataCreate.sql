-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- FUNCTION: public.EntityDataCreate(character varying)

-- DROP FUNCTION public."EntityDataCreate"(character varying);

CREATE OR REPLACE FUNCTION public."EntityDataCreate"(
	entitytypename character varying,
	insertClause character varying,
	insertValues character varying,
	selectClause character varying,
	correlationUuid uuid,
	userId bigint)
	RETURNS character varying
	LANGUAGE 'plpgsql'

	SECURITY DEFINER
	SET search_path = pg_catalog,pg_temp

	COST 100
	VOLATILE
    
AS $BODY$
  DECLARE
    insertQuery varchar;
	newRecordIdQuery varchar;
	entityCount bigint;
	entityData varchar;
	countQuery varchar;
	dataQuery varchar;

	startTime timestamp without time zone;
	newRecordId bigint;
	newUuid uuid;
	deletedAt timestamp without time zone;
	newDigest varchar;

  BEGIN

	startTime := now() at time zone 'utc';
	newRecordIdQuery := 'SELECT MAX("Id") + 1 FROM "'|| entitytypename ||'"."'|| entitytypename ||'"';
	EXECUTE newRecordIdQuery INTO newRecordId;
	newUuid := public.uuid_generate_v4();
	deletedAt := public."GetMaximumDateTimeUtc"();
	newDigest := public.digest(''|| newRecordId ||', '''|| newUuid ||''', '|| insertValues  ||', '''|| correlationUuid ||''', '', '''|| startTime ||''', '|| userId ||', '''|| startTime ||''', '|| userId ||', '''|| deletedAt ||''', -1', 'sha256');
	newDigest := RIGHT(newDigest, LENGTH(newDigest) - 2);

--	insertQuery := 'INSERT INTO "'|| entitytypename ||'"."'|| entitytypename ||'" ("Id", "Uuid", "EntitySubtypeId", "TextKey", "LegalGivenName", "LegalSurname", "BornAtDateTimeUtc", "LegalCitizenOfCountryGeographicUnitId", "LocaleId", "ResourceName", "Ordinal", "IsActive", "CorrelationUuid", "Digest", "CreatedAtDateTimeUtc", "CreatedByInformationSystemUserId", "UpdatedAtDateTimeUtc", "UpdatedByInformationSystemUserId", "DeletedAtDateTimeUtc", "DeletedByInformationSystemUserId") VALUES (2, ''6856f897-df2c-4b81-b2fd-6d8fb8cc8f26'', -1, ''person-none-bill-baker'', ''Bill'', ''Baker'', ''2002-02-03 11:12:13.123'', 1, 1, '''', -1, true, ''f6dffe4d-7077-4394-ac22-cdbbca4a67c4'', ''4ffb52d8edaaa704436717de2e2f56fdc623bcc8c2fe714dc559e0d57cac4c38'', ''2024-08-23 11:08:13.543'', -100, ''2024-08-23 11:08:13.543'', -100, ''9999-12-31T23:59:59.999Z'', -1)';
--	insertQuery := 'INSERT INTO "'|| entitytypename ||'"."'|| entitytypename ||'" ("Id", "Uuid", "EntitySubtypeId", "TextKey", "LegalGivenName", "LegalSurname", "BornAtDateTimeUtc", "LegalCitizenOfCountryGeographicUnitId", "LocaleId", "ResourceName", "Ordinal", "IsActive", "CorrelationUuid", "Digest", "CreatedAtDateTimeUtc", "CreatedByInformationSystemUserId", "UpdatedAtDateTimeUtc", "UpdatedByInformationSystemUserId", "DeletedAtDateTimeUtc", "DeletedByInformationSystemUserId") VALUES ('|| newRecordId ||', ''6856f897-df2c-4b81-b2fd-6d8fb8cc8f26'', -1, ''person-none-bill-baker'', ''Bill'', ''Baker'', ''2002-02-03 11:12:13.123'', 1, 1, '''', -1, true, ''f6dffe4d-7077-4394-ac22-cdbbca4a67c4'', ''4ffb52d8edaaa704436717de2e2f56fdc623bcc8c2fe714dc559e0d57cac4c38'', ''2024-08-23 11:08:13.543'', -100, ''2024-08-23 11:08:13.543'', -100, ''9999-12-31T23:59:59.999Z'', -1)';
--	insertQuery := 'INSERT INTO "'|| entitytypename ||'"."'|| entitytypename ||'" ("Id", "Uuid", "EntitySubtypeId", "TextKey", "LegalGivenName", "LegalSurname", "BornAtDateTimeUtc", "LegalCitizenOfCountryGeographicUnitId", "LocaleId", "ResourceName", "Ordinal", "IsActive", "CorrelationUuid", "Digest", "CreatedAtDateTimeUtc", "CreatedByInformationSystemUserId", "UpdatedAtDateTimeUtc", "UpdatedByInformationSystemUserId", "DeletedAtDateTimeUtc", "DeletedByInformationSystemUserId") VALUES ('|| newRecordId ||', '''|| newUuid ||''', -1, ''person-none-bill-baker'', ''Bill'', ''Baker'', ''2002-02-03 11:12:13.123'', 1, 1, '''', -1, true, ''f6dffe4d-7077-4394-ac22-cdbbca4a67c4'', ''4ffb52d8edaaa704436717de2e2f56fdc623bcc8c2fe714dc559e0d57cac4c38'', ''2024-08-23 11:08:13.543'', -100, ''2024-08-23 11:08:13.543'', -100, ''9999-12-31T23:59:59.999Z'', -1)';
--	insertQuery := 'INSERT INTO "'|| entitytypename ||'"."'|| entitytypename ||'" ('|| insertClause ||') VALUES ('|| newRecordId ||', '''|| newUuid ||''', -1, ''person-none-bill-baker'', ''Bill'', ''Baker'', ''2002-02-03 11:12:13.123'', 1, 1, '''', -1, true, ''f6dffe4d-7077-4394-ac22-cdbbca4a67c4'', ''4ffb52d8edaaa704436717de2e2f56fdc623bcc8c2fe714dc559e0d57cac4c38'', ''2024-08-23 11:08:13.543'', -100, ''2024-08-23 11:08:13.543'', -100, ''9999-12-31T23:59:59.999Z'', -1)';
--	insertQuery := 'INSERT INTO "'|| entitytypename ||'"."'|| entitytypename ||'" ('|| insertClause ||') VALUES ('|| newRecordId ||', '''|| newUuid ||''', '|| insertValues ||', ''f6dffe4d-7077-4394-ac22-cdbbca4a67c4'', ''4ffb52d8edaaa704436717de2e2f56fdc623bcc8c2fe714dc559e0d57cac4c38'', ''2024-08-23 11:08:13.543'', -100, ''2024-08-23 11:08:13.543'', -100, ''9999-12-31T23:59:59.999Z'', -1)';
--	insertQuery := 'INSERT INTO "'|| entitytypename ||'"."'|| entitytypename ||'" ('|| insertClause ||') VALUES ('|| newRecordId ||', '''|| newUuid ||''', '|| insertValues ||', '''|| correlationUuid ||''', ''4ffb52d8edaaa704436717de2e2f56fdc623bcc8c2fe714dc559e0d57cac4c38'', ''2024-08-23 11:08:13.543'', -100, ''2024-08-23 11:08:13.543'', -100, ''9999-12-31T23:59:59.999Z'', -1)';
--	insertQuery := 'INSERT INTO "'|| entitytypename ||'"."'|| entitytypename ||'" ('|| insertClause ||') VALUES ('|| newRecordId ||', '''|| newUuid ||''', '|| insertValues ||', '''|| correlationUuid ||''', '''|| newDigest ||''', ''2024-08-23 11:08:13.543'', -100, ''2024-08-23 11:08:13.543'', -100, ''9999-12-31T23:59:59.999Z'', -1)';
--	insertQuery := 'INSERT INTO "'|| entitytypename ||'"."'|| entitytypename ||'" ('|| insertClause ||') VALUES ('|| newRecordId ||', '''|| newUuid ||''', '|| insertValues ||', '''|| correlationUuid ||''', '''|| newDigest ||''', '''|| startTime ||''', -100, '''|| startTime ||''', -100, ''9999-12-31T23:59:59.999Z'', -1)';
--	insertQuery := 'INSERT INTO "'|| entitytypename ||'"."'|| entitytypename ||'" ('|| insertClause ||') VALUES ('|| newRecordId ||', '''|| newUuid ||''', '|| insertValues ||', '''|| correlationUuid ||''', '''|| newDigest ||''', '''|| startTime ||''', -100, '''|| startTime ||''', -100, '''|| deletedAt ||''', -1)';
	insertQuery := 'INSERT INTO "'|| entitytypename ||'"."'|| entitytypename ||'" ('|| insertClause ||') VALUES ('|| newRecordId ||', '''|| newUuid ||''', '|| insertValues ||', '''|| correlationUuid ||''', '''|| newDigest ||''', '''|| startTime ||''', '|| userId ||', '''|| startTime ||''', '|| userId ||', '''|| deletedAt ||''', -1)';

	EXECUTE insertQuery;

	countQuery := 'SELECT COUNT("Id") FROM "' || entitytypename || '"."' || entitytypename || '" WHERE "IsActive" = true AND "DeletedAtDateTimeUtc" = ''9999-12-31T23:59:59.999'';';
--	dataQuery := 'WITH JsonData AS (SELECT * FROM "'|| entitytypename ||'"."'|| entitytypename ||'" WHERE "Id" = 2) SELECT JSON_AGG(JsonData.*) FROM JsonData;';
	dataQuery := 'WITH JsonData AS (SELECT '|| selectClause ||' FROM "'|| entitytypename ||'"."'|| entitytypename ||'" WHERE "Id" = '|| newRecordId ||') SELECT JSON_AGG(JsonData.*) FROM JsonData;';

	EXECUTE countQuery INTO entityCount;
	EXECUTE dataQuery INTO entityData;

--	RETURN '{"EntityType":"' || entitytypename || '","TotalRows":' || entityCount || ',"newRecordId":' || newRecordId || ',"EntityData":' || entityData || '}';
	RETURN '{"EntityType":"' || entitytypename || '","TotalRows":' || entityCount || ',"EntityData":' || entityData || '}';

  END;
$BODY$;

REVOKE EXECUTE ON FUNCTION public."EntityDataCreate" FROM PUBLIC;

GRANT EXECUTE ON FUNCTION public."EntityDataCreate" TO "AafCoreModeler";
GRANT EXECUTE ON FUNCTION public."EntityDataCreate" TO "AafCoreReadWrite";
GRANT EXECUTE ON FUNCTION public."EntityDataCreate" TO "AafCoreReadOnly";
