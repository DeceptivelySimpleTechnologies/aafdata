-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- Table: EntityTypeAttribute.EntityTypeAttribute

-- DROP TABLE "EntityTypeAttribute"."EntityTypeAttribute";

CREATE TABLE "EntityTypeAttribute"."EntityTypeAttribute"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(200) COLLATE pg_catalog."default" NOT NULL,

    "LocalizedName" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedDescription" character varying(2000) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedAbbreviation" character varying(15) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedInformation" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedPlaceholder" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "IsLocalizable" boolean NOT NULL,
    "GeneralizedDataTypeEntitySubtypeId" bigint NOT NULL,
    "DataSizeOrMaximumLengthInBytesOrCharacters" bigint NOT NULL,
    "DataPrecision" bigint NOT NULL,
    "DataScale" bigint NOT NULL,
    "KeyTypeEntitySubtypeId" bigint NOT NULL,
    "RelatedEntityTypeId" bigint NOT NULL,
    "RelatedEntityTypeAttributeId" bigint NOT NULL,
    "RelatedEntityTypeCardinalityEntitySubtypeId" bigint NOT NULL,
    "EntitySubtypeGroupKey" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "EntityTypeAttributeValueEntitySubtypeId" bigint NOT NULL,
    "DefaultValue" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "MinimumValue" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "MaximumValue" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "RegExValidationPattern" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "StepIncrementValue" numeric(6,2) NOT NULL,
    "RemoteValidationMethodAsAjaxUri" character varying(500) COLLATE pg_catalog."default" NOT NULL,
    "IndexEntitySubtypeId" bigint NOT NULL,
    "UniquenessEntitySubtypeId" bigint NOT NULL,
    "SensitivityEntitySubtypeId" bigint NOT NULL,
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

    CONSTRAINT "EntityTypeAttribute_PK" PRIMARY KEY ("Id"),

    CONSTRAINT "EntityTypeAttribute_CHK_TextKey" CHECK ("TextKey" ~* '^[a-z0-9-]+$'),
    CONSTRAINT "EntityTypeAttribute_CHK_LocalizedName" CHECK ("LocalizedName" ~* '^[A-Za-z]+$')
)

    TABLESPACE pg_default;

CREATE INDEX "EntityTypeAttribute_IDX_LocalizedName" ON "EntityTypeAttribute"."EntityTypeAttribute" ("LocalizedName")
