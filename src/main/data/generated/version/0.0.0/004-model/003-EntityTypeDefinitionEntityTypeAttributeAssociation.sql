    -- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- Table: EntityTypeDefinitionEntityTypeAttributeAssociation.EntityTypeDefinitionEntityTypeAttributeAssociation

-- DROP TABLE "EntityTypeDefinitionEntityTypeAttributeAssociation"."EntityTypeDefinitionEntityTypeAttributeAssociation";

CREATE TABLE "EntityTypeDefinitionEntityTypeAttributeAssociation"."EntityTypeDefinitionEntityTypeAttributeAssociation"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(200) COLLATE pg_catalog."default" NOT NULL,

    "EntityTypeDefinitionId" bigint NOT NULL,
    "EntityTypeAttributeId" bigint NOT NULL,
    "PublishedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "PublishedByInformationSystemUserId" bigint NOT NULL,

    "ResourceName" character varying(100) COLLATE pg_catalog."default" NOT NULL DEFAULT '',
    "Ordinal" bigint NOT NULL DEFAULT -1,
    "IsActive" boolean NOT NULL DEFAULT true,
    "CorrelationUuid" uuid NOT NULL,
    "Digest" character varying(500) COLLATE pg_catalog."default" NOT NULL,
    "CreatedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "CreatedByInformationSystemUserId" bigint NOT NULL,
    "UpdatedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "UpdatedByInformationSystemUserId" bigint NOT NULL,
    "DeletedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "DeletedByInformationSystemUserId" bigint NOT NULL,

    CONSTRAINT "EntTypDefEntTypAtrAssoc_PK" PRIMARY KEY ("Id"),

    CONSTRAINT "EntTypDefEntTypAtrAssoc_CHK_TextKey" CHECK ("TextKey" ~* '^[a-z0-9-]+$'),

    CONSTRAINT "EntTypDefEntTypAtrAssoc_UQ1_TextKey_DeletedAt" UNIQUE ("TextKey", "DeletedAtDateTimeUtc"),
    CONSTRAINT "EntTypDefEntTypAtrAssoc_UQ1_EntTypDefId_EntTypAtrId_DeletedAt" UNIQUE ("EntityTypeDefinitionId", "EntityTypeAttributeId", "DeletedAtDateTimeUtc"),

    CONSTRAINT "EntTypDefEntTypAtrAssoc_FK_EntityTypeDefinition" FOREIGN KEY ("EntityTypeDefinitionId") REFERENCES "EntityTypeDefinition"."EntityTypeDefinition"("Id"),
    CONSTRAINT "EntTypDefEntTypAtrAssoc_FK_EntityTypeAttribute" FOREIGN KEY ("EntityTypeAttributeId") REFERENCES "EntityTypeAttribute"."EntityTypeAttribute"("Id")
)

    TABLESPACE pg_default;

CREATE INDEX "EntTypDefEntTypAtrAssoc_IDX_EntityTypeDefinitionId" ON "EntityTypeDefinitionEntityTypeAttributeAssociation"."EntityTypeDefinitionEntityTypeAttributeAssociation" ("EntityTypeDefinitionId");
CREATE INDEX "EntTypDefEntTypAtrAssoc_IDX_EntityTypeAttributeId" ON "EntityTypeDefinitionEntityTypeAttributeAssociation"."EntityTypeDefinitionEntityTypeAttributeAssociation" ("EntityTypeAttributeId")
