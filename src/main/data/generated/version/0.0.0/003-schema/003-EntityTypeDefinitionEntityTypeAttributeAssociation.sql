-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- SCHEMA: EntityTypeDefinitionEntityTypeAttributeAssociation

-- DROP SCHEMA "EntityTypeDefinitionEntityTypeAttributeAssociation" ;

CREATE SCHEMA "EntityTypeDefinitionEntityTypeAttributeAssociation"
    AUTHORIZATION "AafCoreModeler";

-- GRANT USAGE ON SCHEMA "EntityTypeDefinitionEntityTypeAttributeAssociation" TO "AafCoreModeler";
GRANT USAGE ON SCHEMA "EntityTypeDefinitionEntityTypeAttributeAssociation" TO "AafCoreReadWrite";
GRANT USAGE ON SCHEMA "EntityTypeDefinitionEntityTypeAttributeAssociation" TO "AafCoreReadOnly";
