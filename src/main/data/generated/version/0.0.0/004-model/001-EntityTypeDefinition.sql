-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- Table: EntityTypeDefinition.EntityTypeDefinition

-- DROP TABLE "EntityTypeDefinition"."EntityTypeDefinition";

CREATE TABLE "EntityTypeDefinition"."EntityTypeDefinition"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(200) COLLATE pg_catalog."default" NOT NULL,

    "LocalizedName" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedDescription" character varying(2000) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedAbbreviation" character varying(15) COLLATE pg_catalog."default" NOT NULL,
    "VersionTag" character varying(11) COLLATE pg_catalog."default" NOT NULL,
    "DataLocationEntitySubtypeId" bigint NOT NULL,
    "DataStructureEntitySubtypeId" bigint NOT NULL,
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

    CONSTRAINT "EntityTypeDefinition_PK" PRIMARY KEY ("Id"),

    CONSTRAINT "EntityTypeDefinition_CHK_TextKey" CHECK ("TextKey" ~* '^[a-z0-9-]+$'),
    CONSTRAINT "EntityTypeDefinition_CHK_LocalizedName" CHECK ("LocalizedName" ~* '^[A-Za-z]+$'),

    CONSTRAINT "EntityTypeDefinition_UQ1_TextKey_DeletedAtDateTimeUtc" UNIQUE ("TextKey", "DeletedAtDateTimeUtc"),
    CONSTRAINT "EntityTypeDefinition_UQ1_LocalizedName_DeletedAtDateTimeUtc" UNIQUE ("LocalizedName", "DeletedAtDateTimeUtc")
)

    TABLESPACE pg_default;

CREATE INDEX "EntityTypeDefinition_IDX_LocalizedName" ON "EntityTypeDefinition"."EntityTypeDefinition" ("LocalizedName")
