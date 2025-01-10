-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- FUNCTION: public.GetEntityTypeDefinitionEntityTypeAttributeAssociations()

-- DROP FUNCTION public."GetEntityTypeDefinitionEntityTypeAttributeAssociations"();

CREATE FUNCTION public."GetEntityTypeDefinitionEntityTypeAttributeAssociations"(
	OUT ref refcursor)
	LANGUAGE 'plpgsql'

	SECURITY DEFINER
	SET search_path = pg_catalog,pg_temp

	COST 100
	VOLATILE

AS $BODY$
  BEGIN
	OPEN ref FOR
		SELECT * FROM "EntityTypeDefinitionEntityTypeAttributeAssociation"."EntityTypeDefinitionEntityTypeAttributeAssociation" WHERE "IsActive" = true AND "DeletedAtDateTimeUtc" = '9999-12-31T23:59:59.999' ORDER BY "Id" ASC, "Ordinal" ASC;

  END;
$BODY$;

REVOKE EXECUTE ON FUNCTION public."GetEntityTypeDefinitionEntityTypeAttributeAssociations" FROM PUBLIC;

GRANT EXECUTE ON FUNCTION public."GetEntityTypeDefinitionEntityTypeAttributeAssociations" TO "AafCoreModeler";
GRANT EXECUTE ON FUNCTION public."GetEntityTypeDefinitionEntityTypeAttributeAssociations" TO "AafCoreReadWrite";
GRANT EXECUTE ON FUNCTION public."GetEntityTypeDefinitionEntityTypeAttributeAssociations" TO "AafCoreReadOnly";
