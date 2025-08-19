-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- Table: ContentItem.ContentItem

-- DROP TABLE "ContentItem"."ContentItem";

CREATE TABLE "ContentItem"."ContentItem"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(200) COLLATE pg_catalog."default" NOT NULL,

    "LocalizedName" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedDescription" character varying(2000) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedAbbreviation" character varying(15) COLLATE pg_catalog."default" NOT NULL,

    "LocalizedContent" character varying(1000000) COLLATE pg_catalog."default" NOT NULL,
    "AvailableAtDateTimeUtc" timestamp without time zone NOT NULL,
    "ExpiresAtDateTimeUtc" timestamp without time zone NOT NULL,

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

    CONSTRAINT "ContentItem_PK" PRIMARY KEY ("Id"),

    CONSTRAINT "ContentItem_CHK_TextKey" CHECK ("TextKey" ~* '^[a-z0-9-]+$'),

    CONSTRAINT "ContentItem_UQ1_TextKey_DeletedAtDateTimeUtc" UNIQUE ("TextKey", "DeletedAtDateTimeUtc"),
    CONSTRAINT "ContentItem_UQ1_LocalizedName_DeletedAtDateTimeUtc" UNIQUE ("LocalizedName", "DeletedAtDateTimeUtc"),

    CONSTRAINT "ContentItem_FK_EntitySubtypeId" FOREIGN KEY ("EntitySubtypeId") REFERENCES "EntitySubtype"."EntitySubtype"("Id")
)

    TABLESPACE pg_default;

CREATE INDEX "ContentItem_IDX_LocalizedName" ON "ContentItem"."ContentItem" ("LocalizedName")
