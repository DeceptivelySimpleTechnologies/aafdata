-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.
-- Table: GeographicUnit.GeographicUnit

-- DROP TABLE "GeographicUnit"."GeographicUnit";

CREATE TABLE "GeographicUnit"."GeographicUnit"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(100) COLLATE pg_catalog."default" NOT NULL,

    "LocalizedName" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedDescription" character varying(2000) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedAbbreviation" character varying(15) COLLATE pg_catalog."default" NOT NULL,

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

    CONSTRAINT "GeographicUnit_PK" PRIMARY KEY ("Id")

    CONSTRAINT "GeographicUnit_UQ1_TextKey_DeletedAtDateTimeUtc" UNIQUE ("TextKey", "DeletedAtDateTimeUtc")
    CONSTRAINT "GeographicUnit_UQ1_LocalizedName_DeletedAtDateTimeUtc" UNIQUE ("LocalizedName", "DeletedAtDateTimeUtc")

    CONSTRAINT "GeographicUnit_FK_EntitySubtypeId" FOREIGN KEY ("Id") REFERENCES "EntitySubtype"("Id"))

    TABLESPACE pg_default;

ALTER TABLE "GeographicUnit"."GeographicUnit"
    OWNER to "AafCorePublisher";