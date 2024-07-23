-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.
-- FUNCTION: public.GetEntityTypeDefinitionIdByLocalizedName(character varying)

-- DROP FUNCTION public."GetEntityTypeDefinitionIdByLocalizedName"(character varying);

CREATE FUNCTION "GetEntityTypeDefinitionIdByLocalizedName"(
	entitytypename character varying)
	RETURNS bigint
	LANGUAGE 'plpgsql'
    
AS $BODY$
DECLARE
	entityData bigint;

	dataQuery varchar := 'SELECT "Id" FROM "EntityTypeDefinition"."EntityTypeDefinition" WHERE "LocalizedName" = ' || entitytypename || ';';
  
  BEGIN

	EXECUTE dataQuery INTO entityData;
	RETURN entityData;

  END;
$BODY$;

--ALTER FUNCTION public."GetEntityTypeDefinitionIdByLocalizedName"(bigint)
--	OWNER TO AafCorePublisher;

-- GRANT to ReadWrite, etc rather than to OWNER TO postgres
