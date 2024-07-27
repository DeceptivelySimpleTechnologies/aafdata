-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.
-- FUNCTION: public.GetEntityTypeDefinitionIdByLocalizedName(character varying)

-- DROP FUNCTION public."GetEntityTypeDefinitionIdByLocalizedName"(character varying);

CREATE FUNCTION public."GetEntityTypeDefinitionIdByLocalizedName"(
	entitytypename character varying)
	RETURNS bigint
	LANGUAGE 'plpgsql'

	SECURITY DEFINER
	SET search_path = pg_catalog,pg_temp

	COST 100
	VOLATILE

AS $BODY$
DECLARE
	entityData bigint;

	dataQuery varchar := 'SELECT "Id" FROM "EntityTypeDefinition"."EntityTypeDefinition" WHERE "LocalizedName" = ' || entitytypename || ';';
  
  BEGIN

	EXECUTE dataQuery INTO entityData;
	RETURN entityData;

  END;
$BODY$;

--ALTER FUNCTION "GetEntityTypeDefinitionIdByLocalizedName"(character varying)
--	OWNER TO AafCorePublisher;

REVOKE EXECUTE ON FUNCTION public."GetEntityTypeDefinitionIdByLocalizedName" FROM PUBLIC;

GRANT EXECUTE ON FUNCTION public."GetEntityTypeDefinitionIdByLocalizedName" TO "AafCoreModeler";
GRANT EXECUTE ON FUNCTION public."GetEntityTypeDefinitionIdByLocalizedName" TO "AafCoreReadWrite";
GRANT EXECUTE ON FUNCTION public."GetEntityTypeDefinitionIdByLocalizedName" TO "AafCoreReadOnly";

