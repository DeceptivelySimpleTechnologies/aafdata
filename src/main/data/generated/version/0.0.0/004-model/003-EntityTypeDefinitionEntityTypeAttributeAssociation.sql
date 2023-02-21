-- Table: EntityTypeDefinitionEntityTypeAttributeAssociation.EntityTypeDefinitionEntityTypeAttributeAssociation

-- DROP TABLE "EntityTypeDefinitionEntityTypeAttributeAssociation"."EntityTypeDefinitionEntityTypeAttributeAssociation";

CREATE TABLE "EntityTypeDefinitionEntityTypeAttributeAssociation"."EntityTypeDefinitionEntityTypeAttributeAssociation"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(100) COLLATE pg_catalog."default" NOT NULL,

    "EntityTypeDefinitionId" bigint NOT NULL,
    "EntityTypeAttributeId" bigint NOT NULL,

    "ResourceName" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "Ordinal" bigint NOT NULL,
    "IsActive" boolean NOT NULL,
    "CorrelationUuid" uuid NOT NULL,
    "Digest" character varying(500) COLLATE pg_catalog."default" NOT NULL,
    "CreatedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "CreatedByInformationSystemUserId" bigint NOT NULL,
    "UpdatedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "UpdatedByInformationSystemUserId" bigint NOT NULL,
    "DeletedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "DeletedByInformationSystemUserId" bigint NOT NULL,

    CONSTRAINT "EntityTypeDefinitionEntityTypeAttributeAssociation_PK" PRIMARY KEY ("Id")
)

    TABLESPACE pg_default;

ALTER TABLE "EntityTypeDefinitionEntityTypeAttributeAssociation"."EntityTypeDefinitionEntityTypeAttributeAssociation"
    OWNER to "AafCoreOwner";