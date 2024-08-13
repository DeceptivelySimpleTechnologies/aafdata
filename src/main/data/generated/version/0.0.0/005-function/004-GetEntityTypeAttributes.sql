-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.
-- FUNCTION: public.GetEntityTypeAttributes()

-- DROP FUNCTION public."GetEntityTypeAttributes"();

CREATE FUNCTION public."GetEntityTypeAttributes"(
	OUT ref refcursor)
	LANGUAGE 'plpgsql'

	SECURITY DEFINER
	SET search_path = pg_catalog,pg_temp

	COST 100
	VOLATILE

AS $BODY$
  BEGIN
	OPEN ref FOR
		SELECT * FROM "EntityTypeAttribute"."EntityTypeAttribute" WHERE "IsActive" = true AND "DeletedAtDateTimeUtc" = '9999-12-31T23:59:59.999' ORDER BY "Ordinal" ASC;

  END;
$BODY$;

REVOKE EXECUTE ON FUNCTION public."GetEntityTypeAttributes" FROM PUBLIC;

GRANT EXECUTE ON FUNCTION public."GetEntityTypeAttributes" TO "AafCoreModeler";
GRANT EXECUTE ON FUNCTION public."GetEntityTypeAttributes" TO "AafCoreReadWrite";
GRANT EXECUTE ON FUNCTION public."GetEntityTypeAttributes" TO "AafCoreReadOnly";
