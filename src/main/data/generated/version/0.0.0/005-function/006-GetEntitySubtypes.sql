-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- FUNCTION: public.GetEntitySubtypes()

-- DROP FUNCTION public."GetEntitySubtypes"();

CREATE FUNCTION public."GetEntitySubtypes"(
	OUT ref refcursor)
	LANGUAGE 'plpgsql'

	SECURITY DEFINER
	SET search_path = pg_catalog,pg_temp

	COST 100
	VOLATILE

AS $BODY$
  BEGIN
	OPEN ref FOR
		SELECT * FROM "EntitySubtype"."EntitySubtype" WHERE "IsActive" = true AND "DeletedAtDateTimeUtc" = '9999-12-31T23:59:59.999' ORDER BY "Ordinal" ASC;

  END;
$BODY$;

REVOKE EXECUTE ON FUNCTION public."GetEntitySubtypes" FROM PUBLIC;

GRANT EXECUTE ON FUNCTION public."GetEntitySubtypes" TO "AafCoreModeler";
GRANT EXECUTE ON FUNCTION public."GetEntitySubtypes" TO "AafCoreReadWrite";
GRANT EXECUTE ON FUNCTION public."GetEntitySubtypes" TO "AafCoreReadOnly";
